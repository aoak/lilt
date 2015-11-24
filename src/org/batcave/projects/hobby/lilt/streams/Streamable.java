package org.batcave.projects.hobby.lilt.streams;

import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioFormat;

/**
 * Interface for getting a stream of floating point samples.
 * Primary use of this is to get frames of samples on which we can try
 * some signal processing. This can also be used to play the song.
 * @author aniket
 */
public interface Streamable {
    // This returns list for now. If it shows to be performance bottleneck,
    // we will have to change this to array.
    /**
     * Reads the next batch of samples from the audio file.
     * Returned list of samples can be less than the input parameter in certain
     * cases like end of the audio input.
     * @param numSamples Desired number of samples to read
     * @return List<Float> of samples. Size of the list should indicate number of samples read.
     * @throws IOException if there is exception on read of underlying audio file.
     */
    List<Float> stream(int numSamples) throws IOException;
    
    /**
     * Returns audioFormat for the stream which can be used for various purposes.
     * @return
     */
    AudioFormat getAudioFormat();
}
