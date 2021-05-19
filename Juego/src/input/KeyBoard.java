package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Clase que maneja toda la entrada por teclado, implementa keylistener para recoger la entrada de teclado
public class KeyBoard implements KeyListener {

	private boolean[] keys = new boolean[256];

	public static boolean UP, DOWN, LEFT, RIGHT, SHOOT;

	// Constructor que inicia todos los controles a falso para que una vez toques su
	// tecla correspondiente se ponga en true
	// y haga su acción
	public KeyBoard() {
		UP = false;
		DOWN = false;
		LEFT = false;
		RIGHT = false;
		SHOOT = false;
	}

	// Comprueba en todo momento si al teclado le entra cualquiera de estos
	// controles asignados a las teclas de las flechas
	public void update() {
		UP = keys[KeyEvent.VK_UP];
		DOWN = keys[KeyEvent.VK_DOWN];
		LEFT = keys[KeyEvent.VK_LEFT];
		RIGHT = keys[KeyEvent.VK_RIGHT];
		SHOOT = keys[KeyEvent.VK_SPACE];
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	// Metodo implementado por keylistener escucha en todo momento si alguna tecla
	// esta pulsada
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	// Metodo implementado por keylistener escucha en todo momento si alguna tecla
	// deja de ser pulsada
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;

	}

}
