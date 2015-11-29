package org.batcave.projects.hobby.lilt.streams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import org.tritonus.share.sampled.FloatSampleBuffer;

/**
 * Class that can supply stream of floating point samples from an
 * input stream
 * AudioStream currently lacks the ability to re-stream.
 * @author aniket
 */
public class AudioStream implements Streamable {
    // TODO: Add methods to restart the stream or close the stream
    
    private static final int BUFFER_LENGTH = 1024;
    private final AudioInputStream inputStream;
    private final AudioFormat format;
    private final FloatSampleBuffer buffer;
    private final int channelIndex;
    
    private int readIndex = 0;          // current index into the floating sample buffer
    private int lastIndex;              // index of the last available sample in the buffer array.
    private byte[] bytes = new byte[BUFFER_LENGTH];
    
    public enum Channel {
        Left, Right
    }
    
    /**
     * 
     * @param inputStream
     * @param channel AudioStream.Channel
     * @throws IOException
     */
    public AudioStream(AudioInputStream inputStream, Channel channel) throws IOException {
        this.inputStream = inputStream;
        this.format = inputStream.getFormat();
        this.buffer = new FloatSampleBuffer(format.getChannels(), BUFFER_LENGTH, format.getSampleRate());
        this.channelIndex = channel == Channel.Left ? 0 : 1;
        readStreamInBuffer();
    }
    
    @Override
    public AudioFormat getAudioFormat() {
        return format;
    }
    
    /**
     * Reads the bytes from the input stream into the floating sample buffer.
     * The buffer takes care of the conversion here. 
     * @throws IOException
     */
    private void readStreamInBuffer() throws IOException {
        int readBytes = inputStream.read(bytes, 0, bytes.length);
        if (readBytes == -1) {
            // If there was no data, set lastIndex to -1 so that the streaming stops.
            lastIndex = -1;
            return;
        }
        // compute number of samples available
        int frameCount = readBytes / format.getFrameSize();
        buffer.setSamplesFromBytes(bytes, 0, format, 0, frameCount);
        lastIndex = frameCount - 1;     // adjusting for array index
    }

    @Override
    public List<Float> stream(int numSamples) throws IOException {
        List<Float> list = new ArrayList<>(numSamples);
        for (int i = 0; i < numSamples; i++) {
            if (readIndex > lastIndex) {
                readStreamInBuffer();
                readIndex = 0;
            }
            if (readIndex <= lastIndex) {
                list.add(buffer.getChannel(channelIndex)[readIndex++]);
            }
        }
        return list;
    }
}
