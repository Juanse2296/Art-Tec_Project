package front;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import back.FormBack;
import processing.core.PApplet;
import processing.core.PShape;
import shiffman.box2d.Box2DProcessing;

public class Form extends FormBack {

	PShape s;

	public Form(Box2DProcessing box2d, String data) {
		super(box2d, data);
	}

	public void show(PApplet app) {
		Vec2 pos = box2d.getBodyPixelCoord(body);
		float a = body.getAngle();		
		Fixture f = body.getFixtureList();
		PolygonShape ps = (PolygonShape) f.getShape();
		app.fill(255);
		app.pushMatrix();
		app.translate(pos.x, pos.y);
	    app.rotate(-a);
		app.beginShape();
		for (int i = 0; i < ps.getVertexCount(); i += 1) {
			Vec2 v = box2d.vectorWorldToPixels(ps.getVertex(i));
			app.vertex(v.x, v.y);
		}
		app.endShape();
		app.popMatrix();
	}

}
