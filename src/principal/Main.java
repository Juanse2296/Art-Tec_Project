package principal;

import org.jbox2d.dynamics.contacts.Contact;

import front.Display;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class Main extends PApplet {

	Display dp;
	Box2DProcessing box2d;

	public static void main(String[] args) {
		String[] appletArgs = new String[] { "principal.Main" };
		if (args != null) {
			PApplet.main(concat(appletArgs, args));
		} else {
			PApplet.main(appletArgs);
		}
	}

	public void settings() {
		size(CONFIG.width, CONFIG.height);
	}

	public void setup() {
		String[] args = { "VentanaReactivision" };
		Reactivision react = new Reactivision();
		PApplet.runSketch(args, react);
		/// ---
		box2d = new Box2DProcessing(this);
		box2d.createWorld();
		box2d.listenForCollisions();
		/// ---
		dp = new Display(this, box2d, react);
		smooth();
	}

	public void draw() {
		dp.show();
		box2d.step();
	}

	public void mousePressed() {
		dp.clic();
	}

	public void beginContact(Contact cp) {

		dp.beginCon(cp);

	}

	public void endContact(Contact cp) {
	}

}
