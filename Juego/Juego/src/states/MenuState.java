package states;

import java.awt.Graphics;
import java.util.ArrayList;

import graphics.Assets;
import ui.Action;
import ui.Button;
import window.MainWindow;

public class MenuState extends State {

	private ArrayList<Button> buttons;

	public MenuState() {
		buttons = new ArrayList<Button>();

		buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, MainWindow.WIDTH / 2 - Assets.greyBtn.getWidth() / 2,
				MainWindow.HEIGHT / 2 - Assets.greyBtn.getHeight(), "PLAY", new Action() {
					@Override
					public void doAction() {
						State.changeState(new GameState());
					}
				}));

		buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, MainWindow.WIDTH / 2 - Assets.greyBtn.getWidth() / 2,
				MainWindow.HEIGHT / 2 + Assets.greyBtn.getHeight() / 2, "EXIT", new Action() {
					@Override
					public void doAction() {
						System.exit(0);
					}
				}));

	}

	@Override
	public void update() {
		for (Button b : buttons) {
			b.update();
		}
	}

	@Override
	public void draw(Graphics g) {
		for (Button b : buttons) {
			b.draw(g);
		}
	}

}