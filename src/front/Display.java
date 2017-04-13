package front;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import back.DisplayBack;
import principal.CONFIG;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class Display extends DisplayBack {

	public Display(PApplet app, Box2DProcessing box2d, Reactivision react) {
		super(app, react, box2d);
	}

	public void show() {
		if (game) {
			showGame();
		} else if (v != null) {
		v.show(app);
		}
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
		if (emo.getPos().y > app.height) {
			restarEmotion();
		}
		wm.show(app);
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
		if (CONFIG.level < 3 && game) {
			CONFIG.level++;
			nextLevel(CONFIG.level);			
		} else {
			if(v!=null){
				v.stop();
				v = null;
				iniGame();
			}
			
		}
	}
	
	public void key(){
		if(app.keyPressed){
			wm.blockOut();
		}
	}

}
