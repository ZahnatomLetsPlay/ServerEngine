package musicmanagment;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicManager {
	public Clip mainMenu;
	public Clip queuePhase;

	public MusicManager() {
		try {
			mainMenu = AudioSystem.getClip();
			queuePhase = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			System.out.println(e);
		}
	}

	/**
	 * i.g. MusicManager mm = new MusicManager(); mm.loadAudio(mm.mainMenu,
	 * "filepath");
	 * 
	 * @param clip
	 * @param filename
	 */
	public void loadAudio(Clip clip, String filename) {
		try {
			clip.open(AudioSystem.getAudioInputStream(new File("Sounds//" + filename).toURI().toURL()));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

}
