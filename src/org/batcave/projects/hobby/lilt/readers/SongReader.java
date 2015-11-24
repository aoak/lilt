package org.batcave.projects.hobby.lilt.readers;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Takes a file name and returns an audio stream that is ready to be read.
 * @author aniket
 *
 */
public interface SongReader {
    AudioInputStream read(String fileName) throws UnsupportedAudioFileException, IOException ;
}
