package front;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import back.FormBack;
import processing.core.PApplet;
import processing.core.PShape;
import shiffman.box2d.Box2DProcessing;

public class Form extends FormBack {

	public Form(PApplet app, Box2DProcessing box2d, String lvlsDat, int lvl) {
		super(app, box2d, lvlsDat,lvl);
	}

	public void show(PApplet app) {
		if (type > 0) {
			showCircle(app);
		} else {
			showPolygon(app);
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
		app.endShape();
		app.popMatrix();
	}

}
