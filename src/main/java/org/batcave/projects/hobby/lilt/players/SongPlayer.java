package org.batcave.projects.hobby.lilt.players;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

import org.batcave.projects.hobby.lilt.streams.Streamable;

/**
 * A player that can play a list of Streamables
 * @author aniket
 *
 */
public class SongPlayer {

    /* This probably is not required.
     * private static final String SPEAKERS = "Primary Sound Driver";
     */
    private static final int STREAM_SAMPLE_SIZE = 1024;

    private final List<Streamable> media;
    private final List<SourceDataLine> lines;
    private final Mixer mixer;
    
    public SongPlayer(List<Streamable> media) {
        this.media = media;
        
        Mixer tmp = AudioSystem.getMixer(null);
        /*
         * for (Mixer.Info info : AudioSystem.getMixerInfo()) {
            if (info.getName().equals(SPEAKERS)) {
                tmp = AudioSystem.getMixer(info);
                break;
            }
        }
        */
        mixer = tmp;
        
        lines = new ArrayList<>(media.size());
    }
    
    /**
     * Aquires as many lines from the mixer as the streamables and then plays
     * the stream.
     * @throws LineUnavailableException
     * @see AudioStream
     */
    public void play() throws LineUnavailableException {

        lines.clear();
        for (Streamable mediaStream : media) {
            lines.add(getLine(mediaStream.getAudioFormat()));
        }
        
        ExecutorService executor = Executors.newFixedThreadPool(lines.size());
        for (int i = 0; i < lines.size(); i++) {
            executor.submit(new StreamPlayer(media.get(i), lines.get(i)));
        }
        
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private SourceDataLine getLine(AudioFormat format) throws LineUnavailableException {
        DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine line = (SourceDataLine) mixer.getLine(lineInfo);
        line.open(format);
        return line;
    }
    
    private class StreamPlayer implements Callable<Void> {
        
        private final SourceDataLine line;
        private final Streamable stream;
        
        private StreamPlayer(Streamable stream, SourceDataLine line) {
            this.line = line;
            this.stream = stream;
        }

        @Override
        public Void call() throws Exception {
            line.start();
            while (true) {
                List<Float> samples = stream.stream(STREAM_SAMPLE_SIZE);
                if (samples.isEmpty()) {
                    break;
                }
                playSamples(samples, line);
            }
            line.drain();
            line.close();
            return null;
        }
        
        private void playSamples(List<Float> samples, SourceDataLine line) {
            byte[] buff = new byte[samples.size() * 4];
            short s;
            
            for (int i = 0; i < samples.size(); i++) {
                s = (short) (Short.MAX_VALUE * samples.get(i));
                buff[i*4] = (byte) s;
                buff[i*4 + 1] = (byte) (s >> 8);
                buff[i*4 + 2] = (byte) (s >> 16);
                buff[i*4 + 3] = (byte) (s >> 24);
            }
            line.write(buff, 0, buff.length);
        }
    }
}
