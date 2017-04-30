package back;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import processing.core.PShape;
import shiffman.box2d.Box2DProcessing;

public class FormBack {

	protected Vec2 size;
	protected Body body;
	protected int type;
	protected int stat;
	protected PShape s;
	protected String name="obstaculo";
	

	public FormBack() {
		
	}

	

	public void restartPosition(Box2DProcessing box2d, Vec2 start) {
		body.setTransform(new Vec2(box2d.coordPixelsToWorld(start.x, start.y - 100)), 0);
	}

	public void killBody(Box2DProcessing box2d) {
		if (body != null) {
			box2d.destroyBody(body);
		}
	}

	public void makeBody( Box2DProcessing box2d, Vec2 center) {
		type=0;
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
		//body.setLinearVelocity(new Vec2(app.random(-5, 5), app.random(2, 5)));
		//body.setAngularVelocity(app.random(-5, 5));
	}

	public void makeRectBody(Box2DProcessing box2d, Vec2 pos,Vec2 s,boolean lock ) {
		this.size=s;
		type=1;
		// Define a polygon (this is what we use for a rectangle)
		PolygonShape sd = new PolygonShape();
		float box2dW = box2d.scalarPixelsToWorld(s.x/ 2);
		float box2dH = box2d.scalarPixelsToWorld(s.y / 2);		
		sd.setAsBox(box2dW, box2dH);
		FixtureDef fd = new FixtureDef();
		fd.shape = sd;
		// Parameters that affect physics
		fd.density = 1;
		fd.friction = 0.3f;
		fd.restitution = 0.5f;
		// Define the body and make it from the shape
		BodyDef bd = new BodyDef();

		if (lock)
			bd.type = BodyType.STATIC;
		else
			bd.type = BodyType.DYNAMIC;

		bd.position.set(box2d.coordPixelsToWorld(pos));
		body = box2d.createBody(bd);
		body.createFixture(fd);
	}

	public void makeCircleBody(Box2DProcessing box2d, Vec2 center, float r, boolean lock) {
		type=2;
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



	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}
}
