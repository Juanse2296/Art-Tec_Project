package back;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import processing.core.PApplet;
import processing.core.PShape;
import shiffman.box2d.Box2DProcessing;

public class FormBack {

	protected Vec2 pos;
	protected Body body;
	protected Box2DProcessing box2d;
	protected float r = 0;
	protected int type;
	protected int stat;
	protected PShape s;
	PApplet app;

	public FormBack(PApplet app, Box2DProcessing box2d, String data,int lvl) {
		this.box2d = box2d;
		this.app = app;
		if (getData(data,lvl) == 1 && r > 0)
			makeCircleBody(pos, r);
		else
			makeBody(pos);
	}

	public void killBody() {
		box2d.destroyBody(body);
	}

	private int getData(String data, int lvl) {
		String[] d = data.split(",");
		type = Integer.valueOf(d[2]);
		stat = Integer.valueOf(d[5]);
		pos = new Vec2(Integer.valueOf(d[0]), Integer.valueOf(d[1]));
		s = app.loadShape("data/shapes/"+lvl+"/" + d[4] + ".svg");
		if (type > 0) {
			r = Integer.valueOf(d[3]);
		}
		return type;
	}

	private void makeBody(Vec2 center) {
		// Define a polygon (this is what we use for a rectangle)
		PolygonShape sd = new PolygonShape();
		float box2dW = box2d.scalarPixelsToWorld(s.getWidth() / 2);
		float box2dH = box2d.scalarPixelsToWorld(s.getHeight() / 2);
		sd.setAsBox(box2dW, box2dH);
		FixtureDef fd = new FixtureDef();
		fd.shape = sd;
		// Parameters that affect physics
		fd.density = 1;
		fd.friction = 0.3f;
		fd.restitution = 0.5f;
		// Define the body and make it from the shape
		BodyDef bd = new BodyDef();

		if (stat > 0)
			bd.type = BodyType.STATIC;
		else
			bd.type = BodyType.DYNAMIC;

		bd.position.set(box2d.coordPixelsToWorld(center));
		body = box2d.createBody(bd);
		body.createFixture(fd);
	}

	private void makeCircleBody(Vec2 center, float r) {
		BodyDef bd = new BodyDef();
		bd.position = box2d.coordPixelsToWorld(center.x, center.y);

		if (stat > 0)
			bd.type = BodyType.STATIC;
		else
			bd.type = BodyType.DYNAMIC;

		body = box2d.createBody(bd);
		CircleShape cs = new CircleShape();
		cs.m_radius = box2d.scalarPixelsToWorld(r);
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = 1;
		fd.friction = 0.01f;
		fd.restitution = 0.3f;
		body.createFixture(fd);
	}
}
