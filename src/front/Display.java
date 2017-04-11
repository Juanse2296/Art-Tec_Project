package front;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import back.DisplayBack;
import back.SoundController;
import principal.CONFIG;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class Display extends DisplayBack {

	Emotion emo;

	public Display(PApplet app, Box2DProcessing box2d, Reactivision react) {
		super(app, react, box2d);
		emo = new Emotion(box2d, sc.getPlayer(), 100, 100, 50, 50);
		//createPlatforms(box2d, CONFIG.obstacles);
	}

	public void show() {
		app.background(0);
		app.shapeMode(app.CORNER);
		app.shape(background);
		app.shapeMode(app.CENTER);
		emo.show(app);
		showBridge();
		//showPlatforms();
		showPeople();
		showForms();
	}

	public void beginCon(Contact cp) {
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

	private void finished() {
		if (app.dist(emo.getX(), emo.getY(), plats.get(plats.size() - 1).getX(),
				plats.get(plats.size() - 1).getY()) < emo.getW() + 4) {
			System.out.println("interaccion terminada");
		}
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
		nextLevel(2);		
	}
}
