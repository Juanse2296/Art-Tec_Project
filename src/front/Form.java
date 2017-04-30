package front;

import org.jbox2d.common.Vec2;
import back.FormBack;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Form extends FormBack {

	private float a;

	public Form(PApplet app, Box2DProcessing box2d, Vec2 pos) {
		super(app, box2d, pos);
	}

	public Form(Box2DProcessing box2d, Vec2 pos, Vec2 size, boolean lock) {
		super(box2d, pos, size, lock);
	}
	
	public Form(Box2DProcessing box2d, Vec2 pos,int r,boolean lock) {
		super( box2d,pos,r,lock);
	}

	public void show(PApplet app, Box2DProcessing box2d) {
		if (!name.equals("checkpoint")) {
			if (type > 0) {
				showCircle(app, box2d);
			} else {
				showPolygon(app, box2d);
			}
		} else {
			app.pushMatrix();
			app.translate(pos.x, pos.y);
			app.fill(255);
			app.rotate(a);
			app.shapeMode(PApplet.CENTER);
			app.shape(s, 0, 0);
			a += 0.01f;
			app.shapeMode(PApplet.CORNER);
			app.popMatrix();
		}
	}

	public void showCircle(PApplet app, Box2DProcessing box2d) {
		Vec2 pos = box2d.getBodyPixelCoord(body);
		// Get its angle of rotation
		float a = body.getAngle();
		app.ellipseMode(PApplet.CENTER);
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(a);
		app.fill(255);
		// app.ellipse(0, 0, r * 2, r * 2);
		// Let's add a line so we can see the rotation
		// app.line(0, 0, r, 0);
		app.shapeMode(PApplet.CENTER);
		app.shape(s);
		app.shapeMode(PApplet.CORNER);
		app.popMatrix();
	}

	public void showPolygon(PApplet app, Box2DProcessing box2d) {
		Vec2 pos = box2d.getBodyPixelCoord(body);
		float a = body.getAngle();
		// Fixture f = body.getFixtureList();
		// PolygonShape ps = (PolygonShape) f.getShape();
		app.fill(255);
		app.rectMode(PApplet.CENTER);
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(-a);
		app.beginShape();
		app.shapeMode(PApplet.CENTER);
		app.shape(s);
		app.shapeMode(PApplet.CORNER);
		app.endShape();
		app.popMatrix();
	}

	public void display(PApplet app, Box2DProcessing box2d) {
		// We look at each body and get its screen position
		Vec2 pos = box2d.getBodyPixelCoord(body);
		// Get its angle of rotation
		float a = body.getAngle();
		app.rectMode(PApplet.CENTER);
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(-a);
		app.fill(175);
		app.stroke(0);
		app.rect(0, 0, w, h);
		app.popMatrix();
	}
}
