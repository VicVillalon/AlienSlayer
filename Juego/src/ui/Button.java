package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Text;
import input.MouseInput;
import math.Vector2D;

public class Button {

	private BufferedImage mouseOutImg;

	private BufferedImage mouseInImg;

	private boolean mouseIn;

	private Rectangle boundingBox;

	private Action action;

	private String text;

	public Button(BufferedImage mouseOutImg, BufferedImage mouseInImg, int x, int y, boolean mouseIn, String text,
			Action action) {
		super();
		this.mouseOutImg = mouseOutImg;
		this.mouseInImg = mouseInImg;
		this.mouseIn = mouseIn;
		this.action = action;
		this.boundingBox = new Rectangle(x, y, mouseInImg.getWidth(), mouseInImg.getHeight());
		this.text = text;
	}

	public void update() {
		if (boundingBox.contains(MouseInput.x, MouseInput.y)) {
			mouseIn = true;
		} else {
			mouseIn = false;
		}

		if (mouseIn && MouseInput.MLB) {
			action.doAction();
		} else {

		}
	}

	public void draw(Graphics gp) {

		if (mouseIn) {
			gp.drawImage(mouseInImg, boundingBox.x, boundingBox.y, null);
		} else {
			gp.drawImage(mouseOutImg, boundingBox.x, boundingBox.y, null);
		}

		Text.drawText(gp, text, new Vector2D(boundingBox.getX() + boundingBox.getWidth() / 2,
				boundingBox.getY() + boundingBox.getHeight()), true, Color.BLACK, Assets.fontMid);
	}

}
