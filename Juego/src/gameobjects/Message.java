package gameobjects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import graphics.Text;
import math.Vector2D;
import states.GameState;

public class Message {
	private GameState gameState;
	// transferencia
	private float alpha;
	private Vector2D position;
	private String text;
	private Color color;
	private boolean center;
	private boolean fade;
	private Font font;
	// canvi del alpha
	private final float deltaAlpha = 0.01f;

	public Message(GameState gameState, Vector2D position, String text, Color color, boolean center, boolean fade,
			Font font) {
		super();
		this.gameState = gameState;
		this.position = position;
		this.text = text;
		this.color = color;
		this.center = center;
		this.fade = fade;
		this.font = font;
		if (fade) {
			alpha = 1;
		} else {
			alpha = 0;
		}

	}

	public void draw(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		Text.drawText(g2d, text, position, center, color, font);

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

		position.setY(position.getY() - 1);

		if (fade) {
			alpha -= deltaAlpha;
		} else {
			alpha += deltaAlpha;
		}

		if (fade && alpha < 0 || fade && alpha > 1) {
			gameState.getMessages().remove(this);
		}
	}

}
