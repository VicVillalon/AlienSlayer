package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameobjects.Message;
import gameobjects.Meteor;
import gameobjects.MovingObject;
import gameobjects.Player;
import gameobjects.Size;
import gameobjects.Ufo;
import graphics.Animation;
import graphics.Assets;
import graphics.Sound;
import math.Vector2D;
import window.MainWindow;

// Juego que guarda en todo momento el estado del juego, se encarga de actualizar, dibujar, etc.. Todos los objetos del juego
public class GameState extends State {

	private Player player;
	private ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>();
	private ArrayList<Animation> explosions = new ArrayList<Animation>();

	private ArrayList<Message> messages = new ArrayList<Message>();

	private int waves;
	private int meteors;
	private int score;
	private int lives;

	private Sound background;

	// Constructor que inicia los objetos del juego dandoles la posicion y los
	// graficos
	public GameState() {
		player = new Player(Assets.player, new Vector2D(MainWindow.WIDTH / 2 - Assets.player.getWidth() / 2,
				MainWindow.HEIGHT / 2 - Assets.player.getHeight() / 2), 2, new Vector2D(), this);
		movingObjects.add(player);
		waves = 1;
		meteors = 1;
		lives = 3;
		startWave();
		background = new Sound(Assets.backgroundMusic);
		background.loop();
		background.changeVolume(-10.0f);
	}

	private void startWave() {
		messages.add(new Message(new Vector2D(MainWindow.WIDTH / 2, MainWindow.HEIGHT / 2), false, "WAVE " + waves,
				Color.WHITE, true, Assets.fontBig, this));
		double x, y;
		for (int i = 0; i < meteors; i++) {
			x = i % 2 == 0 ? Math.random() * MainWindow.WIDTH : 0;
			y = i % 2 == 0 ? 0 : Math.random() * MainWindow.HEIGHT;

			BufferedImage texture = Assets.bigs[(int) (Math.random() * Assets.bigs.length)];
			movingObjects.add(new Meteor(texture, new Vector2D(x, y), Meteor.METEOR_VELOCITY * Math.random() + 1,
					new Vector2D(0, 1).setDirection(Math.random() * Math.PI * 2), this, Size.BIG));
		}
		meteors++;
		waves++;
		spawnUfo();
	}

	// Actualizacion de posicion de los objetos
	@Override
	public void update() {
		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).update();
		}
		for (int i = 0; i < explosions.size(); i++) {
			Animation anim = explosions.get(i);
			anim.update();
			if (!anim.isRunning()) {
				explosions.remove(i);
			}
		}
		for (int i = 0; i < movingObjects.size(); i++) {
			if (movingObjects.get(i) instanceof Meteor) {
				return;
			}
		}
		startWave();
	}

	public void divideMeteor(Meteor meteor) {
		Size size = meteor.getSize();
		BufferedImage[] textures = size.textures;

		Size newSize = null;

		switch (size) {
		case BIG:
			newSize = Size.MED;
			break;
		case MED:
			newSize = Size.SMALL;
			break;
		case SMALL:
			newSize = Size.TINY;
			break;
		default:
			return;
		}

		for (int i = 0; i < size.quantity; i++) {
			movingObjects.add(new Meteor(textures[(int) (Math.random() * textures.length)], meteor.getPosition(),
					Meteor.METEOR_VELOCITY * Math.random() + 1,
					new Vector2D(0, 1).setDirection(Math.random() * Math.PI * 2), this, newSize));
		}
	}

	public void playExplosion(Vector2D position) {
		explosions.add(new Animation(Assets.explosion, 50, position
				.substract(new Vector2D(Assets.explosion[0].getWidth() / 2, Assets.explosion[0].getHeight() / 2))));
	}

	private void spawnUfo() {
		int rand = (int) (Math.random() * 2);

		double x = rand == 0 ? (Math.random() * MainWindow.WIDTH) : 0;
		double y = rand == 0 ? 0 : (Math.random() * MainWindow.HEIGHT);

		ArrayList<Vector2D> path = new ArrayList<Vector2D>();

		double posX, posY;

		posX = Math.random() * MainWindow.WIDTH / 2;
		posY = Math.random() * MainWindow.HEIGHT / 2;
		path.add(new Vector2D(posX, posY));

		posX = Math.random() * (MainWindow.WIDTH / 2) + MainWindow.WIDTH / 2;
		posY = Math.random() * MainWindow.HEIGHT / 2;
		path.add(new Vector2D(posX, posY));

		posX = Math.random() * MainWindow.WIDTH / 2;
		posY = Math.random() * (MainWindow.HEIGHT / 2) + MainWindow.HEIGHT / 2;
		path.add(new Vector2D(posX, posY));

		posX = Math.random() * (MainWindow.WIDTH / 2) + MainWindow.WIDTH / 2;
		posY = Math.random() * (MainWindow.HEIGHT / 2) + MainWindow.HEIGHT / 2;
		path.add(new Vector2D(posX, posY));

		movingObjects.add(new Ufo(Assets.ufo, new Vector2D(x, y), 3, new Vector2D(), this, path));
	}

	// Dibuja en la ventana los objetos
	@Override
	public void draw(Graphics gp) {
		Graphics2D g2d = (Graphics2D) gp;

		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).draw(gp);
		}

		for (int i = 0; i < explosions.size(); i++) {
			Animation anim = explosions.get(i);
			g2d.drawImage(anim.getCurrentFrame(), (int) anim.getPosition().getX(), (int) anim.getPosition().getY(),
					null);
		}

		for (int i = 0; i < messages.size(); i++) {
			messages.get(i).draw(g2d);
		}

		drawScore(gp);
		drawLives(gp);
	}

	public void addScore(int value, Vector2D position) {
		score += value;
		messages.add(new Message(position, true, "+" + value + " score", Color.WHITE, false, Assets.fontMid, this));
	}

	private void drawScore(Graphics gp) {
		Vector2D pos = new Vector2D(960, 25);
		String scoreToString = Integer.toString(score);
		for (int i = 0; i < scoreToString.length(); i++) {
			gp.drawImage(Assets.numeros[Integer.parseInt(scoreToString.substring(i, i + 1))], (int) pos.getX(),
					(int) pos.getY(), null);
			pos.setX(pos.getX() + 20);
		}
	}

	private void drawLives(Graphics gp) {
		Vector2D posLife = new Vector2D(25, 25);
		gp.drawImage(Assets.life, (int) posLife.getX(), (int) posLife.getY(), null);
		gp.drawImage(Assets.numeros[10], (int) posLife.getX() + 40, (int) posLife.getY() + 5, null);

		String livesToString = Integer.toString(lives);
		Vector2D pos = new Vector2D(posLife.getX(), posLife.getY());

		for (int i = 0; i < livesToString.length(); i++) {
			int number = 0;
			number = Integer.parseInt(livesToString.substring(i, i + 1));
			if (number <= 0) {
				break;
			}
			gp.drawImage(Assets.numeros[number], (int) pos.getX() + 60, (int) pos.getY() + 5, null);
			pos.setX(pos.getX() + 20);
		}
	}

	public void substractLife() {
		lives--;
	}

	public ArrayList<MovingObject> getMovingObjects() {
		return movingObjects;
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

}
