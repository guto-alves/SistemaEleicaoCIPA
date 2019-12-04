package sounds;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	public static void play() {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream oStream = AudioSystem.getAudioInputStream(new File("./src/sounds/barulho.wav"));
			clip.open(oStream);

			clip.loop(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
