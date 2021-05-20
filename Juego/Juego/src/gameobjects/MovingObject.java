package gameobjects;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Assets;
import graphics.Sound;
import math.Vector2D;
import states.GameState;

// Clase que extiende de gameObject referente a aquellos objetos que tienen un movimiento
public abstract class MovingObject extends GameObject {

	protected Vector2D velocity;
	protected AffineTransform at;
	protected double angle;
	protected double maxVel;
	protected int width;
	protected int height;
	protected GameState gameState;

	private Sound explosion;

	public MovingObject(BufferedImage texture, Vector2D position, double maxVel, Vector2D velocity,
			GameState gameState) {
		super(texture, position);
		this.velocity = velocity;
		this.maxVel = maxVel;
		this.gameState = gameState;
		width = texture.getWidth();
		height = texture.getHeight();
		angle = 0;
		explosion = new Sound(Assets.explosionSound);
		explosion.changeVolume(-11.0f);
	}

	protected void collidesWith() {
		ArrayList<MovingObject> movingObjects = gameState.getMovingObjects();

		for (int i = 0; i < movingObjects.size(); i++) {
			MovingObject m = movingObjects.get(i);
			if (m.equals(this)) {
				continue;
			}
			double distance = m.getCenter().substract(getCenter()).getMagnitude();

			if (distance < m.width / 2 + width / 2 && movingObjects.contains(this)) {
				objectCollision(m, this);
			}
		}

	}

	private void objectCollision(MovingObject a, MovingObject b) {
		if (a instanceof Player && ((Player) a).isSpawning() || b instanceof Player && ((Player) b).isSpawning()) {
			return;
		}

		if (!(a instanceof Meteor && b instanceof Meteor)) {
			if (!(a instanceof Player && b instanceof Shoot) && !(a instanceof Shoot && b instanceof Player)) {
				gameState.playExplosion(getCenter());
				a.destroy();
				b.destroy();
			}
		}
	}

	protected void destroy() {
		gameState.getMovingObjects().remove(this);
		if (!(this instanceof Shoot)) {
			explosion.play();
		}
	}

	public Vector2D getCenter() {
		return new Vector2D(position.getX() + width / 2, position.getY() + height / 2);
	}

}
