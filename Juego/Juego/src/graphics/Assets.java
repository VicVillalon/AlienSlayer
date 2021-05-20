package graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

// Clase que recoge todas las imagenes para dibujarlas en la ventana
public class Assets {

	public static BufferedImage player;

	public static BufferedImage speed;
	public static BufferedImage speedBack;

	public static BufferedImage disparo, greenLaser, redLaser;

	public static BufferedImage[] bigs = new BufferedImage[4];
	public static BufferedImage[] meds = new BufferedImage[2];
	public static BufferedImage[] smalls = new BufferedImage[2];
	public static BufferedImage[] tinies = new BufferedImage[2];

	public static BufferedImage[] explosion = new BufferedImage[9];

	public static BufferedImage[] numeros = new BufferedImage[11];

	public static BufferedImage escenario;

	public static BufferedImage ufo;

	public static BufferedImage life;

	// Fonts
	public static Font fontBig, fontMid;
	// Ui

	public static BufferedImage blueBtn, greyBtn;

	// sounds
	public static Clip shoot, hit, slimeSound, backgroundMusic, explosionSound;

	// Metodo que inicia la carga de imagenes
	public static void init() {
		player = Loader.ImageLoader("/personajes/player.png");

		speed = Loader.ImageLoader("/efectos/pisadas.png");

		speedBack = Loader.ImageLoader("/efectos/pisadasBack.png");

		disparo = Loader.ImageLoader("/shoots/disparo.png");

		greenLaser = Loader.ImageLoader("/shoots/laserGreen.png");

		redLaser = Loader.ImageLoader("/shoots/laserRed.png");

		greyBtn = Loader.ImageLoader("/ui/grey_button.png");

		blueBtn = Loader.ImageLoader("/ui/blue_button.png");

		fontBig = Loader.loadFont("/fuentes/PressStart2P-Regular.ttf", 42);

		fontMid = Loader.loadFont("/fuentes/PressStart2P-Regular.ttf", 20);

		for (int i = 0; i < bigs.length; i++) {
			bigs[i] = Loader.ImageLoader("/meteors/big" + (i + 1) + ".png");
		}
		for (int i = 0; i < meds.length; i++) {
			meds[i] = Loader.ImageLoader("/meteors/med" + (i + 1) + ".png");
		}
		for (int i = 0; i < smalls.length; i++) {
			smalls[i] = Loader.ImageLoader("/meteors/small" + (i + 1) + ".png");
		}
		for (int i = 0; i < tinies.length; i++) {
			tinies[i] = Loader.ImageLoader("/meteors/tiny" + (i + 1) + ".png");
		}
		for (int i = 0; i < explosion.length; i++) {
			explosion[i] = Loader.ImageLoader("/explosion/" + i + ".png");
		}

		escenario = Loader.ImageLoader("/escenario/escenario.png");

		ufo = Loader.ImageLoader("/ufo/slimeFast.png");

		life = Loader.ImageLoader("/heart/heart.png");

		for (int i = 0; i < numeros.length; i++) {
			numeros[i] = Loader.ImageLoader("/numeros/" + i + ".png");
		}

		shoot = Loader.loadSound("/sonidos/shoot.wav");

		hit = Loader.loadSound("/sonidos/slime.wav");

		slimeSound = Loader.loadSound("/sonidos/slime.wav");

		explosionSound = Loader.loadSound("/sonidos/explosion.wav");

		backgroundMusic = Loader.loadSound("/sonidos/backgroundMusic.wav");

	}
}
