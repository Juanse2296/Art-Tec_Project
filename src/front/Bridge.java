package front;

import back.BridgeBack;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class Bridge extends BridgeBack {

	public Bridge(PApplet app, Box2DProcessing box2d, Reactivision react, float l, int n, int x, int y) {
		super(app, box2d, react, l, n, x, y);
	}

	// Draw the bridge
	public void display() {
		for (Point p : particles) {
			app.fill(255);
			p.display();
		}
		move();
	}
}
