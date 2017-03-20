package front;

import org.jbox2d.common.Vec2;

import back.BridgeBack;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class Bridge extends BridgeBack {

	Bridge(PApplet app, Box2DProcessing box2d, Reactivision react, float l, int n) {
		super(app, box2d, react, l, n);
		// TODO Auto-generated constructor stub
	}

	// Draw the bridge
	public void display() {
		for (Point p : particles) {
			app.fill(255);
			p.display();

		}
		if (app.mousePressed && app.mouseButton==app.LEFT) {
			particles.get(0).move();
		}else if (app.mousePressed && app.mouseButton==app.RIGHT) {
			particles.get(particles.size()-1).move();
		}

	}

}
