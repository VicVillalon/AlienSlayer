package gameobjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import math.Vector2D;
import states.GameState;
import window.MainWindow;

public class Shoot extends MovingObject {

	public Shoot(BufferedImage texture, Vector2D position, double maxVel, double angle, Vector2D velocity,
			GameState gameState) {
		super(texture, position, maxVel, velocity, gameState);
		this.angle = angle;
		this.velocity = velocity.scale(maxVel);
	}

	@Override
	public void update() {
		position = position.add(velocity);
		if (position.getX() < 0 || position.getX() > MainWindow.WIDTH || position.getY() < 0
				|| position.getY() > MainWindow.HEIGHT) {
			gameState.getMovingObjects().remove(this);

		}
		collidesWith();
	}

	@Override
	public void draw(Graphics gp) {
		Graphics2D g2d = (Graphics2D) gp;
		at = AffineTransform.getTranslateInstance(position.getX() - width / 2 + 13, position.getY() - 20);

		at.rotate(angle, width / 2 - 13, 20);

		g2d.drawImage(texture, at, null);

	}

	@Override
	public Vector2D getCenter() {
		return new Vector2D(position.getX() + width / 2, position.getY() + width / 2);
	}

}
