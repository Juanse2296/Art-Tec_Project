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
	protected int type;
	protected int stat;
	protected PShape s;
	protected String name;
	protected int w, h;

	public FormBack(PApplet app, Box2DProcessing box2d, Vec2 pos) {
		makeBody(app, box2d, pos);
	}

	public FormBack(Box2DProcessing box2d, Vec2 pos, Vec2 size, boolean lock) {
		makeRectBody(box2d, pos);
	}
	
	public FormBack(Box2DProcessing box2d, Vec2 pos,int r,boolean lock) {		
		makeCircleBody(box2d, pos, r, lock);
	}

	public void restartPosition(Box2DProcessing box2d, Vec2 start) {
		body.setTransform(new Vec2(box2d.coordPixelsToWorld(start.x, start.y - 100)), 0);
	}

	public void killBody(Box2DProcessing box2d) {
		if (body != null) {
			box2d.destroyBody(body);
		}
	}

	private void makeBody(PApplet app, Box2DProcessing box2d, Vec2 center) {
		// Define a polygon (this is what we use for a rectangle)
		PolygonShape sd = new PolygonShape();
		Vec2[] vertices = new Vec2[4];
		vertices[0] = box2d.vectorPixelsToWorld(new Vec2(-15, 25));
		vertices[1] = box2d.vectorPixelsToWorld(new Vec2(15, 0));
		vertices[2] = box2d.vectorPixelsToWorld(new Vec2(20, -15));
		vertices[3] = box2d.vectorPixelsToWorld(new Vec2(-10, -10));
		sd.set(vertices, vertices.length);
		// Define the body and make it from the shape
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.position.set(box2d.coordPixelsToWorld(center));
		body = box2d.createBody(bd);
		body.createFixture(sd, 1.0f);
		// Give it some initial random velocity
		body.setLinearVelocity(new Vec2(app.random(-5, 5), app.random(2, 5)));
		body.setAngularVelocity(app.random(-5, 5));
	}

	private void makeRectBody(Box2DProcessing box2d, Vec2 center) {
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

	private void makeCircleBody(Box2DProcessing box2d, Vec2 center, float r, boolean lock) {
		BodyDef bd = new BodyDef();
		bd.position = box2d.coordPixelsToWorld(center.x, center.y);
		if (lock)
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vec2 getPos() {
		return pos;
	}

	public void setPos(Vec2 pos) {
		this.pos = pos;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}
}
