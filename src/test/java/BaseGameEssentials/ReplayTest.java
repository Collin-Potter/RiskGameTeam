package BaseGameEssentials;

import com.amazonaws.services.s3.AmazonS3Client;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReplayTest {

    @Test
    public void initialize() {
        Replay.initialize();
    }

    @Test
    public void recordAction() {
        Replay.recordAction("Hi");
    }

    @Test
    public void stopReplay() {
        Replay.initialize();
        Replay.stopReplay();

    }

    @Test
    public void createFolder() {

    }
}