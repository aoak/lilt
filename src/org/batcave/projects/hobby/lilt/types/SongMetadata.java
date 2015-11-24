package org.batcave.projects.hobby.lilt.types;

public class SongMetadata {

    private final String artist;
    private final String album;
    private final String name;
    
    public SongMetadata(String name, String album, String artist) {
        this.name = name;
        this.album = album;
        this.artist = artist;
    }
    
    public String getName() {
        return name;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }
}
