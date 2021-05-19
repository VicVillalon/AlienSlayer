package gameobjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import game.MainWindow;
import graphics.Assets;
import input.KeyBoard;
import math.Vector2D;
import states.GameState;

// Clase jugador que implementa el gameobject
public class Player extends MovingObject {

	private Vector2D heading;
	private Vector2D acceleration;
	private final double ACC = 0.2;
	private final long FIRERATE = 300;

	private boolean accelerate = false;
	private Cronometro cronometro;

	// Constructor
	public Player(BufferedImage texture, Vector2D position, double maxVel, Vector2D velocity, GameState gameState) {
		super(texture, position, maxVel, velocity, gameState);
		heading = new Vector2D(0, 1);
		acceleration = new Vector2D();
		cronometro = new Cronometro();
	}

	// Actualización de la posicion del jugador a partir de los controles que llegan
	// por teclado
	@Override
	public void update() {
		if (KeyBoard.SHOOT && !cronometro.isRunning()) {
			gameState.getMovingObjects().add(0, new Shoot(Assets.disparo, getCenter().add(heading.scale(width / 2)),
					5, angle, heading, gameState));
			cronometro.run(FIRERATE);
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
		} else {
			if (velocity.getMagnitude() != 0) {
				acceleration = (velocity.scale(-1).normalize()).scale(ACC / 2);
			}
			accelerate = false;
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
		cronometro.update();
	}

	// Se dibuja al jugador a partir de la posicion actualizada
	@Override
	public void draw(Graphics gp) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D) gp;

		AffineTransform at1 = AffineTransform.getTranslateInstance(position.getX() + width / 2 + 5,
				position.getY() + height / 2 + 20);
		AffineTransform at2 = AffineTransform.getTranslateInstance(position.getX() + 22,
				position.getY() + height / 2 + 20);
		at1.rotate(angle, -5, -20);
		at2.rotate(angle, width / 2 - 22, -20);

		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(angle, width / 2, height / 2);
		g2d.drawImage(Assets.player, at, null);
		if (accelerate) {
			g2d.drawImage(Assets.speed, at1, null);
			g2d.drawImage(Assets.speed, at2, null);
		}

	}

	public Vector2D getCenter() {
		return new Vector2D(position.getX() + width / 2, position.getY() + height / 2);
	}

}
