package gameobjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import math.Vector2D;
import states.GameState;
import window.MainWindow;

public class Meteor extends MovingObject {

	public static final double METEOR_VELOCITY = 2.0;
	private static final int METEOR_SCORE = 20;
	private Size size;

	public Meteor(BufferedImage texture, Vector2D position, double maxVel, Vector2D velocity, GameState gameState,
			Size size) {
		super(texture, position, maxVel, velocity, gameState);
		this.size = size;
		this.velocity = velocity.scale(maxVel);
	}

	@Override
	public void update() {
		position = position.add(velocity);
		if (position.getX() > MainWindow.WIDTH) {
			position.setX(-width);
		}
		if (position.getY() > MainWindow.HEIGHT) {
			position.setY(-height);
		}
		if (position.getX() < -width) {
			position.setX(MainWindow.WIDTH);
		}
		if (position.getY() < -height) {
			position.setY(MainWindow.HEIGHT);
		}

		angle += 0.05;

	}

	@Override
	public void destroy() {
		gameState.divideMeteor(this);
		gameState.addScore(METEOR_SCORE, position);

		super.destroy();
	}

	@Override
	public void draw(Graphics gp) {

		Graphics2D g2d = (Graphics2D) gp;

		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(angle, width / 2, height / 2);
		g2d.drawImage(texture, at, null);
	}

	public Size getSize() {
		return size;
	}

}
