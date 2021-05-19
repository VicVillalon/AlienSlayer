package gameobjects;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

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

	public MovingObject(BufferedImage texture, Vector2D position, double maxVel, Vector2D velocity,
			GameState gameState) {
		super(texture, position);
		this.velocity = velocity;
		this.maxVel = maxVel;
		this.gameState = gameState;
		width = texture.getWidth();
		height = texture.getHeight();
		angle = 0;
		// TODO Auto-generated constructor stub
	}
}
