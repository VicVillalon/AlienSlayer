package gameobjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import math.Vector2D;

public class Escenario extends GameObject {

	public Escenario(BufferedImage texture, Vector2D position) {
		super(texture, position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics gp) {
		Graphics2D g2d = (Graphics2D) gp;
		AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
		g2d.drawImage(Assets.escenario, at, null);

	}

}
