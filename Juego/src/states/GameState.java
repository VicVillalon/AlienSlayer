package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import gameobjects.Message;
import gameobjects.MovingObject;
import gameobjects.Player;
import graphics.Assets;
import graphics.Text;
import math.Vector2D;

// Juego que guarda en todo momento el estado del juego, se encarga de actualizar, dibujar, etc.. Todos los objetos del juego
public class GameState extends State {

	private Player player;
	private ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>();
	// ArrayList de messages
	private ArrayList<Message> messages = new ArrayList<Message>();

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}

	// debajo de meteors
	private int waves = 1;

	// Constructor que inicia los objetos del juego dandoles la posicion y los
	// graficos
	public GameState() {
		player = new Player(Assets.player, new Vector2D(100, 500), 2, new Vector2D(), this);
		movingObjects.add(player);
	}

	// No terminado
	public void addScore(int value, Vector2D position) {
		score += value;
		message.add(new Message(this, new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2), "+" + value + " score",
				Color.WHITE, true, false, Assets.fontMid));
	}

	// Actualizacion de posicion de los objetos
	@Override
	public void update() {
		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).update();
		}
	}

	// Dibuja en la ventana los objetos
	@Override
	public void draw(Graphics gp) {
		Graphics2D g2d = (Graphics2D) gp;

		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).draw(gp);
		}

		for (int i = 0; i < messages.size(); i++) {
			messages.get(i).draw(g2d);
		}
		// drawScore(g);

		Text.drawText(g2d, "Wave " + waves, new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2), true, false,
				Color.WHITE, Assets.fontBig);

	}

	// Start wave
//	messages.add(new Message (this, new Vector2D(Constants.WIDTH/2, Constants.HEIGHT/2), "Wave " + waves, Color.WHITE, true, true, Assets.fontBig));
	public ArrayList<MovingObject> getMovingObjects() {
		return movingObjects;
	}

}
