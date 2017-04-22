package front;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import back.DisplayBack;
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
		switch (state) {
		case 0:
			if (v != null)
				v.show(app);
			break;
		case 1:
			if (v != null)
			inst.show(app);
			break;
		case 2:
			showGame();
			break;
		case 3:
			spin.show(app, winner);
			if (winner)
				showFireworks();
			break;
		}
	}
	
	

	private void showFireworks() {
		for (int i = 0; i < fs.length; i++) {
			fs[i].pintar();
		}
		if (app.frameCount % 30 == 0)
		for (int i = 0; i < 100; i++)
			launchFireWork();
	}

	private void showGame() {
		app.background(0);
		app.shape(background);
		emo.show(app);
		showBridge();
		showPeople();
		showForms();
		go.show(app);
		/// ------debe estar al final
		tryAgain();
	}

	private void tryAgain() {
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




	private void showForms() {
		for (int i = 0; i < forms.size(); i++) {
			forms.get(i).show(app);
		}
	}

	public void clic() {
		Lclick();
		Rclick();
	}

	private void Lclick() {
		switch (state) {
		case 0:
			v.stop();	
			startIntruction();
			break;
		case 1:
		//	startGame();
			break;		
		case 2:
			if (lvSelected < 4) {
				lvSelected = lvSelected + 3;
				nextLevel(lvSelected);
			}
			break;
		}
	}

	private void Rclick() {
		if (app.mouseButton == app.RIGHT) {
			state = 3;
			winner = true;
			gameOver();
		}
	}

	public void key() {
		if (state == 2) {

		}
	}

}
