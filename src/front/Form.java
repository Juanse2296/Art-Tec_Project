package front;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import back.FormBack;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Form extends FormBack {

	private float a;

	public Form(PApplet app, Box2DProcessing box2d, String lvlsDat, int lvl) {
		super(app, box2d, lvlsDat, lvl);
	}

	public Form(PApplet app, Box2DProcessing box2d, int x, int y, int i, int j, boolean b) {
		super(app, box2d, x, y, i, j, b);
	}

	public void show(PApplet app) {
		if (!name.equals("checkpoint")) {
			if (type > 0) {
				showCircle(app);
			} else {
				showPolygon(app);
			}
		} else {
			app.pushMatrix();
			app.translate(pos.x, pos.y);
			app.fill(255);
			app.rotate(a);
			app.shapeMode(app.CENTER);
			app.shape(s, 0,0);
			a+=0.01f;
			app.shapeMode(app.CORNER);
			app.popMatrix();
		}
	}

	public void showCircle(PApplet app) {
		Vec2 pos = box2d.getBodyPixelCoord(body);
		// Get its angle of rotation
		float a = body.getAngle();
		app.ellipseMode(app.CENTER);
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(a);
		app.fill(255);
		// app.ellipse(0, 0, r * 2, r * 2);
		// Let's add a line so we can see the rotation
		// app.line(0, 0, r, 0);
		app.shapeMode(app.CENTER);
		app.shape(s);
		app.shapeMode(app.CORNER);
		app.popMatrix();
	}

	public void showPolygon(PApplet app) {
		Vec2 pos = box2d.getBodyPixelCoord(body);
		float a = body.getAngle();
		Fixture f = body.getFixtureList();
		PolygonShape ps = (PolygonShape) f.getShape();
		app.fill(255);
		app.rectMode(app.CENTER);
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(-a);
		app.beginShape();
		app.shapeMode(app.CENTER);
		app.shape(s);
		app.shapeMode(app.CORNER);
		app.endShape();
		app.popMatrix();
	}

	public void display(PApplet app) {
		// We look at each body and get its screen position
		Vec2 pos = box2d.getBodyPixelCoord(body);
		// Get its angle of rotation
		float a = body.getAngle();
		app.rectMode(app.CENTER);
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(-a);
		app.fill(175);
		app.stroke(0);
		app.rect(0, 0, w, h);
		app.popMatrix();
	}
}
