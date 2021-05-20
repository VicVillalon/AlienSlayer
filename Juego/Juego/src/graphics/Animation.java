package graphics;

import java.awt.image.BufferedImage;

import math.Vector2D;

public class Animation {
	private BufferedImage[] frames;
	private int change;
	private boolean running;
	private int index;
	private Vector2D position;
	private long time, lastTime;

	public Animation(BufferedImage[] frames, int change, Vector2D position) {
		this.frames = frames;
		this.change = change;
		this.position = position;
		index = 0;
		running = true;
		time = 0;
		lastTime = System.currentTimeMillis();
	}

	public void update() {
		time += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();

		if (time > change) {
			time = 0;
			index++;
			if (index >= frames.length) {
				running = false;
			}
		}
	}

	public boolean isRunning() {
		return running;
	}

	public Vector2D getPosition() {
		return position;
	}

	public BufferedImage getCurrentFrame() {
		return frames[index];
	}
}
