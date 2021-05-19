package graphics;

import javax.sound.sampled.Clip;

public class Sound {

	private Clip clip;

	public Sound(Clip clip) {
		super();
		this.clip = clip;
	}

	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}

	public void stop() {
		clip.stop();
	}

}
