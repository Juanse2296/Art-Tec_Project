package front;

import org.jbox2d.common.Vec2;

import back.GoingBack;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Going extends GoingBack {

	public Going(PApplet app, Box2DProcessing box2d, Vec2 pos, int numPoints) {
		super(app, box2d, pos, numPoints);
	}

	public void show(PApplet app) {
		showTie(app);
	}

	private void showTie(PApplet app) {
		app.pushMatrix();

		for (int i = 0; i < particlesLeft.size(); i++) {
			Point p = particlesLeft.get(i);
			p.display();
		}

		for (int i = 0; i < particlesRight.size(); i++) {
			Point p = particlesRight.get(i);
			p.display();
		}

		table.display(app);
		app.popMatrix();
		createJoint();
	}

}
