package front;

import java.util.Random;
import java.util.logging.Level;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import back.DisplayBack;
import principal.CONFIG;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class Display extends DisplayBack {

	Firework[] fs = new Firework[10];
	boolean once;

	public Display(PApplet app, Box2DProcessing box2d, Reactivision react) {
		super(app, react, box2d);
		createFireworks();
	}

	private void createFireworks() {
		for (int i = 0; i < fs.length; i++) {
			fs[i] = new Firework(app);
		}
	}

	public void launchFireWork() {
		once = false;
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isHidden() && !once) {
				fs[i].launch();
				once = true;
			}
		}
	}

	public void show() {
		if (spin == null) {
			if (game) {
				showGame();
			} else if (v != null) {
				v.show(app);
			}
		} else {
			spin.show(app, winner);
			if (winner)			
				showFireworks();
		}
	}

	private void showFireworks() {
		for (int i = 0; i < fs.length; i++) {
			fs[i].pintar();
		}
		if (app.frameCount % 240 == 0)
			launchFireWork();
		for (int i = 0; i < 1000; i++)
			launchFireWork();

	}

	private void showGame() {
		app.background(0);
		app.shapeMode(app.CORNER);
		app.shape(background);
		app.shapeMode(app.CENTER);
		emo.show(app);
		showBridge();
		// showPlatforms();
		showPeople();
		showForms();
		wm.show(app);

		/// ------debe estar al final
		tryAgain();
	}

	public void tryAgain() {
		if (emo.getPos().y > app.height) {
			if (lvSelected > 3)
				restarEmotion(true);
			else
				restarEmotion(false);
		}
	}

	public void beginCon(Contact cp) {
		if (emo != null) {
			Fixture f1 = cp.getFixtureA();
			Fixture f2 = cp.getFixtureB();
			// Get both bodies
			Body b1 = f1.getBody();
			Body b2 = f2.getBody();
			// Get our objects that reference these bodies
			Object o1 = b1.getUserData();
			Object o2 = b2.getUserData();
			emo.soundPlayer();
		}
	}

	private void finished() {
		// if (app.dist(emo.getX(), emo.getY(), plats.get(plats.size() -
		// 1).getX(),
		// plats.get(plats.size() - 1).getY()) < emo.getW() + 4) {
		// System.out.println("interaccion terminada");
		// }
	}

	private void showPlatforms() {
		for (int i = 0; i < plats.size(); i++) {
			plats.get(i).show(app);
		}
	}

	private void showForms() {
		for (int i = 0; i < forms.size(); i++) {
			forms.get(i).show(app);
		}
	}

	public void clic() {
		if (!playingGame)
			playingGame = playing();
	}

	public boolean playing() {
		if (lvSelected < 4 && game) {
			lvSelected = lvSelected + 3;
			nextLevel(lvSelected);
			return true;
		} else {
			if (v != null) {
				v.stop();
				iniGame();
			}
		}
		return false;
	}

	public void key() {
		if (app.keyPressed && game) {
			wm.blockOut();
		}
	}

}
