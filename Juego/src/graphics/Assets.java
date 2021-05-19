package graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

// Clase que recoge todas las imagenes para dibujarlas en la ventana
public class Assets {

	public static BufferedImage player;

	public static BufferedImage speed;

	public static BufferedImage disparo, greenLaser, redLaser;

	public static BufferedImage[] bigs = new BufferedImage[4];
	public static BufferedImage[] meds = new BufferedImage[2];
	public static BufferedImage[] smalls = new BufferedImage[2];
	public static BufferedImage[] tinies = new BufferedImage[2];

	// Fonts
	public static Font fontBig, fontMid;
	// Ui

	public static BufferedImage blueBtn, greyBtn;

	// sounds

	public static Clip shoot, hit, slimeSound, backgroundMusic, explosion;
	public static BufferedImage escenario;

	// Metodo que inicia la carga de imagenes
	public static void init() {
		player = Loader.ImageLoader("/personajes/player.png");

		speed = Loader.ImageLoader("/efectos/fire.png");

		disparo = Loader.ImageLoader("/shoots/disparo.png");

		fontBig = Loader.loadFont("/fuentes/PressStart2P-Regular.tff", 42);

		fontMid = Loader.loadFont("/fuentes/PressStart2P-Regular.tff", 20);

		greenLaser = Loader.ImageLoader("/shoots/laserGreen.png");

		redLaser = Loader.ImageLoader("/shoots/laserRed.png");

		greyBtn = Loader.ImageLoader("/ui/grey_button.png");

		blueBtn = Loader.ImageLoader("/ui/blue_button.png");

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

		escenario = Loader.ImageLoader("/escenario/escenario.png");

		shoot = Loader.loadSound("/sonidos/shoot.wav");

		hit = Loader.loadSound("/sonidos/hit.wav");

		slimeSound = Loader.loadSound("/sonidos/slimeSound.wav");

		explosion = Loader.loadSound("/sonidos/explosion.wav");

		backgroundMusic = Loader.loadSound("/sonidos/backgroundMusic.wav");

	}
}
