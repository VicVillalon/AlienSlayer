package gameobjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import math.Vector2D;

// Clase abstracta que debe implementar cualquier objeto del juego, implementa las texturas del objeto y la posicion de este
public abstract class GameObject {
	protected BufferedImage texture;
	protected Vector2D position;

	// Constructor
	public GameObject(BufferedImage texture, Vector2D position) {
		super();
		this.texture = texture;
		this.position = position;
	}

	// Clase de actualización del objeto (poscion, textura)
	public abstract void update();

	// Clase que dibuja el objeto a partir de su actualización
	public abstract void draw(Graphics gp);

	// Getter y Setter de la posicion para poder modificar la posicion del objeto
	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

}
