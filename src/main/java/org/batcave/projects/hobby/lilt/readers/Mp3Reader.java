package org.batcave.projects.hobby.lilt.readers;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Class to read mp3 files.
 * @author aniket
 *
 */
public class Mp3Reader implements SongReader {

    public Mp3Reader() {
        
    }
    
    @Override
    public AudioInputStream read(String fileName) throws UnsupportedAudioFileException, IOException {
        File file = new File(fileName);
        // TODO: There should be a check on fileType etc.
        // AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);

        AudioInputStream in = AudioSystem.getAudioInputStream(file);
        AudioFormat format = in.getFormat();
        // TODO: Experiment with the parameters
        AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                format.getSampleRate(),
                16,
                format.getChannels(),
                format.getChannels() * 2,
                format.getSampleRate(),
                false);
        return AudioSystem.getAudioInputStream(decodedFormat, in);
    }
}
