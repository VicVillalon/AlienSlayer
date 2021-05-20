package gameobjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Assets;
import graphics.Sound;
import math.Vector2D;
import states.GameState;
import window.MainWindow;

public class Ufo extends MovingObject {

	private static final int NODE_RADIUS = 160;
	private static final double UFO_MASS = 60;
	private static final long UFO_CADENCIA = 1000;
	private static final double UFO_ANGLE_RANGE = Math.PI / 2;
	private static final int UFO_SCORE = 40;
	private ArrayList<Vector2D> path;
	private Vector2D currentNode;
	private int index;
	private boolean following;
	private Cronometro cadencia;

	private Sound slimeSound;

	public Ufo(BufferedImage texture, Vector2D position, double maxVel, Vector2D velocity, GameState gameState,
			ArrayList<Vector2D> path) {
		super(texture, position, maxVel, velocity, gameState);
		this.path = path;
		index = 0;
		following = true;
		cadencia = new Cronometro();
		cadencia.run(UFO_CADENCIA);
		slimeSound = new Sound(Assets.slimeSound);
		slimeSound.changeVolume(-12.0f);
	}

	private Vector2D seekForce(Vector2D target) {
		Vector2D desiredVelocity = target.substract(getCenter());
		desiredVelocity = desiredVelocity.normalize().scale(maxVel);
		return desiredVelocity.substract(velocity);
	}

	private Vector2D pathFollowing() {
		currentNode = path.get(index);
		double distanceToNode = currentNode.substract(getCenter()).getMagnitude();

		if (distanceToNode < NODE_RADIUS) {
			index++;
			if (index >= path.size()) {
				following = false;
			}
		}

		return seekForce(currentNode);
	}

	@Override
	public void update() {
		Vector2D pathFollowing;

		if (following) {
			pathFollowing = pathFollowing();

		} else {
			pathFollowing = new Vector2D();
		}
		pathFollowing = pathFollowing.scale(1 / UFO_MASS);

		velocity = velocity.add(pathFollowing);
		velocity = velocity.limit(maxVel);
		position = position.add(velocity);

		if (position.getX() > MainWindow.WIDTH || position.getY() > MainWindow.HEIGHT || position.getX() < 0
				|| position.getY() < 0) {
			gameState.getMovingObjects().remove(this);
		}
		// disparo
		if (!cadencia.isRunning()) {
			Vector2D toPlayer = gameState.getPlayer().getCenter().substract(getCenter());
			toPlayer = toPlayer.normalize();
			double currentAngle = toPlayer.getAngle();
			currentAngle += Math.random() * UFO_ANGLE_RANGE - UFO_ANGLE_RANGE / 2;

			if (toPlayer.getX() < 0) {
				currentAngle = -currentAngle + Math.PI;
			}

			toPlayer = toPlayer.setDirection(currentAngle);

			Shoot shoot = new Shoot(Assets.disparo, getCenter().add(toPlayer.scale(width)), 5,
					currentAngle + Math.PI / 2, toPlayer, gameState);
			gameState.getMovingObjects().add(0, shoot);

			cadencia.run(UFO_CADENCIA);

			slimeSound.play();
		}

		if (slimeSound.getFramePosition() < 8500) {
			slimeSound.stop();
		}

		angle += 0.05;
		collidesWith();
		cadencia.update();

	}

	@Override
	public void destroy() {
		gameState.addScore(UFO_SCORE, position);
		slimeSound.play();
		super.destroy();
	}

	@Override
	public void draw(Graphics gp) {
		Graphics2D g2d = (Graphics2D) gp;

		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(angle, width / 2, height / 2);
		g2d.drawImage(texture, at, null);

	}

}
