package org.batcave.projects.hobby.lilt.readers;

import java.io.IOException;
import java.util.Arrays;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.batcave.projects.hobby.lilt.players.SongPlayer;
import org.batcave.projects.hobby.lilt.streams.AudioStream;
import org.batcave.projects.hobby.lilt.streams.AudioStream.Channel;
import org.batcave.projects.hobby.lilt.streams.Streamable;
import org.junit.Test;

public class Mp3ReaderTest {
    
    @Test
    public void playTest() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        String fileName = "data/Rocky_road_to_Dublin.mp3";
        SongReader reader = new Mp3Reader();
        AudioStream leftStream = new AudioStream(reader.read(fileName), Channel.Left);
        AudioStream rightStream = new AudioStream(reader.read(fileName), Channel.Right);
        SongPlayer player = new SongPlayer(Arrays.asList(new Streamable[] {leftStream, rightStream}));
        player.play();
    }
}
