package front;

import org.jbox2d.common.Vec2;

import back.GoingBack;
import principal.CONFIG;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Going extends GoingBack {

	public Going(PApplet app, Box2DProcessing box2d, Vec2 pos, int numPoints) {
		super(app, box2d, pos, numPoints);
	}

	public void show(PApplet app) {
		showTie(app);
		createJoint();
		BridgePosition(app);
	}

	private void showTie(PApplet app) {
		app.pushMatrix();
		app.fill(255);
		for (int i = 0; i < particlesLeft.size(); i++) {
			Point p = particlesLeft.get(i);
			p.display();
		}
		for (int i = 0; i < particlesRight.size(); i++) {
			Point p = particlesRight.get(i);
			p.display();
		}
		if (table != null)
			table.display(app, box2d);
		app.popMatrix();
	}

	private void BridgePosition(PApplet app) {
		app.pushMatrix();
		app.noFill();
		app.stroke(255);
		app.rectMode(PApplet.CORNER);
		app.rect(pos.x, pos.y + 200, CONFIG.sensibleAreaW, CONFIG.sensibleAreaH);
		app.popMatrix();
	}

	public boolean checkPosition(Vec2 a, Vec2 b, Vec2 c) {
		boolean temp = false;
		if ((a.x >= pos.x && a.x <= pos.x + CONFIG.sensibleAreaW && a.y >= pos.y && a.y <= pos.y + CONFIG.camHeight)
				&& (b.x >= pos.x && b.x <= pos.x + CONFIG.sensibleAreaW && b.y >= pos.y
						&& b.y <= pos.y + CONFIG.camHeight)
				&& (c.x >= pos.x && c.x <= pos.x + CONFIG.sensibleAreaW && c.y >= pos.y
						&& c.y <= pos.y + CONFIG.camHeight)) {
			return true;
		}
		return temp;
	}

}
