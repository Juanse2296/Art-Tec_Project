package front;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import back.SoundController;
import principal.CONFIG;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class Display {

	Emotion emo;
	Surface sf;
	PApplet app;
	Bridge bridge;
	SoundController sc;
	ArrayList<Platform> plats;

	public Display(PApplet app, Box2DProcessing box2d, Reactivision react) {
		this.app = app;
		sc = new SoundController(app);
		emo = new Emotion(box2d, sc.getPlayer(), 100, 100, 50, 50);
		// sf= new Surface(app, box2d,react);
		// Thread t= new Thread(sf);
		// t.start();
		bridge = new Bridge(app, box2d, react, 200, 20);
		plats = new ArrayList<Platform>();
		createPlatforms(box2d, CONFIG.obstacles);
	}

	public void show() {
		app.background(0);
		emo.show(app);
		// sf.show(app);
		bridge.display();
		showPlatforms();
		finished();
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
		finished();

	}

	public void finished() {
		if (app.dist(emo.getX(), emo.getY(), plats.get(plats.size() - 1).getX(),
				plats.get(plats.size() - 1).getY()) < emo.getW() + 4) {
			System.out.println("interaccion terminada");
		}
	}

	public void showPlatforms() {
		for (int i = 0; i < plats.size(); i++) {
			plats.get(i).show(app);
		}
	}

	public void createPlatforms(Box2DProcessing box2d, int num) {
		for (int i = 0; i < num; i++) {
			if (i == num - 1) {
				plats.add(new Platform(box2d, i, 1100, 600, 160, 20, true));
			} else {
				plats.add(new Platform(box2d, i, (int)app.random(200,1000), (int)app.random(200,600), (int)app.random(20,80), (int)app.random(20,80), false));
			}
		}
	}

}
