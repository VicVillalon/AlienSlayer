package gameobjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Sound;
import input.KeyBoard;
import math.Vector2D;
import states.GameState;
import window.MainWindow;

// Clase jugador que implementa el gameobject
public class Player extends MovingObject {

	private Vector2D heading;
	private Vector2D acceleration;
	private final double ACC = 0.2;
	private final long FIRERATE = 300;

	private boolean accelerate = false;
	private boolean desaccelerate = false;
	private Cronometro cronometro;

	public static final long FLICKER_TIME = 200;
	public static final long SPAWNING_TIME = 3000;

	private boolean spawning, visible;
	private Cronometro spawnTime, flickerTime;

	private Sound shoot, hit;

	// Constructor
	public Player(BufferedImage texture, Vector2D position, double maxVel, Vector2D velocity, GameState gameState) {
		super(texture, position, maxVel, velocity, gameState);
		heading = new Vector2D(0, 1);
		acceleration = new Vector2D();
		cronometro = new Cronometro();
		spawnTime = new Cronometro();
		flickerTime = new Cronometro();
		shoot = new Sound(Assets.shoot);
		shoot.changeVolume(-12.0f);
		hit = new Sound(Assets.hit);
		hit.changeVolume(-12.0f);
	}

	// Actualización de la posicion del jugador a partir de los controles que
	// llegan
	// por teclado
	@Override
	public void update() {
		if (!spawnTime.isRunning()) {
			spawning = false;
			visible = true;
		}
		if (spawning) {
			if (!flickerTime.isRunning()) {
				flickerTime.run(FLICKER_TIME);
				if (visible == false) {
					visible = true;
				} else {
					visible = false;
				}
			}
		}

		if (KeyBoard.SHOOT && !cronometro.isRunning() && spawning == false) {
			gameState.getMovingObjects().add(0,
					new Shoot(Assets.disparo, getCenter().add(heading.scale(width / 2)), 5, angle, heading, gameState));
			cronometro.run(FIRERATE);
			shoot.play();
		}
		if (shoot.getFramePosition() > 8500) {
			shoot.stop();
		}

		if (KeyBoard.RIGHT) {
			angle += Math.PI / 40;
		}
		if (KeyBoard.LEFT) {
			angle -= Math.PI / 40;
		}

		if (KeyBoard.UP) {
			acceleration = heading.scale(ACC);
			accelerate = true;
			if (KeyBoard.DOWN) {
				accelerate = false;
				desaccelerate = false;
				acceleration = (velocity.scale(-1).normalize()).scale(ACC / 2);
			}
			// si hacemos que corra deberia ir aqui
		} else {
			if (velocity.getMagnitude() != 0) {
				acceleration = (velocity.scale(-1).normalize()).scale(ACC / 2);
			}
			accelerate = false;
		}
		if (KeyBoard.DOWN) {
			acceleration = heading.scale(-ACC);
			desaccelerate = true;
		} else {
			desaccelerate = false;
		}
		velocity = velocity.add(acceleration);

		velocity = velocity.limit(maxVel);

		heading = heading.setDirection(angle - Math.PI / 2);
		position = position.add(velocity);

		if (position.getX() > MainWindow.WIDTH) {
			position.setX(0);
		}
		if (position.getY() > MainWindow.HEIGHT) {
			position.setY(0);
		}
		if (position.getX() < 0) {
			position.setX(MainWindow.WIDTH);
		}
		if (position.getY() < 0) {
			position.setY(MainWindow.HEIGHT);
		}
		spawnTime.update();
		flickerTime.update();
		cronometro.update();
		collidesWith();
	}

	@Override
	public void destroy() {
		spawning = true;
		spawnTime.run(SPAWNING_TIME);
		hit.play();
		gameState.substractLife();
		// resetValues();
	}

	private void resetValues() {
		angle = 0;
		velocity = new Vector2D();
		position = new Vector2D(MainWindow.WIDTH / 2 - Assets.player.getWidth() / 2,
				MainWindow.HEIGHT / 2 - Assets.player.getHeight() / 2);
	}

	public boolean isSpawning() {
		return spawning;
	}

	// Se dibuja al jugador a partir de la posicion actualizada
	@Override
	public void draw(Graphics gp) {
		if (visible == false) {
			return;
		}

		Graphics2D g2d = (Graphics2D) gp;

		AffineTransform at1 = AffineTransform.getTranslateInstance(position.getX() + width / 2 + 5,
				position.getY() + height / 2 + 38);
		AffineTransform at2 = AffineTransform.getTranslateInstance(position.getX() + 15,
				position.getY() + height / 2 + 38);
		at1.rotate(angle, -5, -38);
		at2.rotate(angle, width / 2 - 15, -38);

		AffineTransform at3 = AffineTransform.getTranslateInstance(position.getX() + width / 2 + 5,
				position.getY() + height / 2 - 38);
		AffineTransform at4 = AffineTransform.getTranslateInstance(position.getX() + 15,
				position.getY() + height / 2 - 38);
		at3.rotate(angle, -5, 38);
		at4.rotate(angle, width / 2 - 15, 38);

		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(angle, width / 2, height / 2);
		g2d.drawImage(Assets.player, at, null);
		if (accelerate) {
			g2d.drawImage(Assets.speed, at1, null);
			g2d.drawImage(Assets.speed, at2, null);
		}
		if (desaccelerate) {
			g2d.drawImage(Assets.speedBack, at3, null);
			g2d.drawImage(Assets.speedBack, at4, null);
		}

	}
}
