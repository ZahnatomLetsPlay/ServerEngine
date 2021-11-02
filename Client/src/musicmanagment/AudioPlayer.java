package musicmanagment;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioPlayer extends Thread {

	private Clip audio;

	private FloatControl volManager;

	private boolean muted;

	private int vol;


	public AudioPlayer(Clip clip) {
		this.audio = clip;
		this.volManager = (FloatControl) this.audio.getControl(FloatControl.Type.MASTER_GAIN);
	}

	public void Mute(boolean mute) {
		this.muted = mute;
		float volume = (float) (vol) / 100f;
		float range = volManager.getMaximum() - volManager.getMinimum();
		float gain = (range * volume) + volManager.getMinimum();
		this.volManager.setValue(mute ? this.volManager.getMinimum() : gain);
	}

	public void setVolume(int vol) {
		this.vol = vol;
		float volume = (float) (vol) / 100f;
		float range = volManager.getMaximum() - volManager.getMinimum();
		float gain = (range * volume) + volManager.getMinimum();
		volManager.setValue(!muted ? gain : volManager.getMinimum());
	}

	public void stopAndErase() {
		this.audio.stop();
		this.audio.drain();
	}

	@Override
	public void run() {
		this.audio.setFramePosition(0);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
			this.audio.loop(Clip.LOOP_CONTINUOUSLY);
			this.audio.start();
		
	}
}
