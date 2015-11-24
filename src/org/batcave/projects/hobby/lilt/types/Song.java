package org.batcave.projects.hobby.lilt.types;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

public class Song {

    private final SongMetadata metadata;
    private final AudioMetaData audioMetadata;
    
    public Song(SongMetadata metadata, AudioFileFormat fileFormat) {
        this.metadata = metadata;
        this.audioMetadata = new AudioMetaData(fileFormat);
    }
    
    public String getSongName() {
        return metadata.getName();
    }
    
    public AudioFormat getAudioFormat() {
        return audioMetadata.getAudioFormat();
    }
}
