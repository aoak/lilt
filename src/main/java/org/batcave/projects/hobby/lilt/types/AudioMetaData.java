package org.batcave.projects.hobby.lilt.types;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;

public class AudioMetaData {

    private static final String DURATION = "duration";
    private static final String AUTHOR = "author";
    private static final String TITLE = "title";
    
    private final AudioFormat audioFormat;
    private final long duration;
    private final String author;
    private final String title;
    
    public AudioMetaData(AudioFileFormat fFormat) {
        this.audioFormat = fFormat.getFormat();
        this.duration = fFormat.getProperty(DURATION) == null ? -1 : (long) fFormat.getProperty(DURATION);
        this.author = String.valueOf(fFormat.getProperty(AUTHOR));
        this.title = String.valueOf(fFormat.getProperty(TITLE));
    }

    public long getSongDuration() {
        return duration;
    }

    public String getSongAuthorFromAudio() {
        return author;
    }

    public String getSongTitleFromAudio() {
        return title;
    }
    
    public int getNumChannels() {
        return audioFormat.getChannels();
    }
    
    public Encoding getEncoding() {
        return audioFormat.getEncoding();
    }
    
    public float getFramRate() {
        return audioFormat.getFrameRate();
    }
    
    public float getSampleRate() {
        return audioFormat.getSampleRate();
    }
    
    public int getFrameSize() {
        return audioFormat.getFrameSize();
    }
    
    public int getSampleSize() {
        return audioFormat.getSampleSizeInBits();
    }
    
    public AudioFormat getAudioFormat() {
        return audioFormat;
    }
}
