package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import graphics.Assets;
import input.KeyBoard;
import input.MouseInput;
import states.MenuState;
import states.State;

// Clase principal, clase donde se muestra la ventana y esta todo el proceso de ejecución
public class MainWindow extends JFrame implements Runnable {

	public static final int WIDTH = 1080, HEIGHT = 720;
	private Canvas canvas;
	private Thread thread;
	private boolean running;

	private BufferStrategy bs;
	private Graphics gp;

	private final int FPS = 60;
	private double TARGETTIME = 1000000000 / FPS;
	private double delta = 0;
	private int AVERAGEFPS = FPS;

	private MouseInput mouseInput;
	private KeyBoard keyBoard;

	// Constructor. Crea la ventana, el canvas y la keyBoard, la ventana y el canvas
	// lo dimensiona.
	public MainWindow() {
		setTitle("SLIME SLAYER");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		keyBoard = new KeyBoard();
		mouseInput = new MouseInput();
		canvas = new Canvas();

		canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		canvas.setFocusable(true);

		add(canvas);
		canvas.addKeyListener(keyBoard);
		canvas.addMouseListener(mouseInput);
		canvas.addMouseMotionListener(mouseInput);
		setVisible(true);
	}

	// Main que ejecuta todo el programa
	public static void main(String[] args) {
		new MainWindow().start();

	}

	// Metodo que actualiza en todo momento la posicion y la entrada por teclado
	public void update() {
		keyBoard.update();
		State.getCurrentState().update();
	}

	// Metodo que dibuja a partir de la actualizacion los graficos y cada objeto en
	// su debida posicion
	public void draw() {
		bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}

		gp = bs.getDrawGraphics();

		// -----

		gp.fillRect(0, 0, WIDTH, HEIGHT);

		gp.drawString("" + AVERAGEFPS, 10, 100);

		State.getCurrentState().draw(gp);

		// ----
		gp.dispose();
		bs.show();
	}

	// Inicia la carga de activos y el estado del juego
	private void init() {
		Assets.init();
		State.changeState(new MenuState());
	}

	// Metodo implementado de la clase Runnable que esta en constante ejecucion,
	// limitado a 60 entradas por segundo por los fps,
	// en esas 60 entradas se encarga de actualizar las posiciones y dibujarlo en la
	// ventana.
	@Override
	public void run() {

		long now = 0;
		long lastTime = System.nanoTime();
		int frames = 0;
		long time = 0;

		init();

		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / TARGETTIME;
			time += (now - lastTime);
			lastTime = now;
			if (delta >= 1) {
				update();
				draw();
				delta--;
				frames++;
			}
			if (time >= 1000000000) {
				AVERAGEFPS = frames;
				frames = 0;
				time = 0;
			}
		}
		stop();

	}

	// metodo de inicialización que inicia el hilo y comienza la ejecución del run
	public void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	// metodo que finaliza la ejecución y termina los hilos
	public void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
