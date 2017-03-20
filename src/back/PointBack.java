package back;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import TUIO.TuioObject;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class PointBack {

	protected Body body;
	protected float r;
	protected Box2DProcessing box2d;
	protected PApplet app;

	public PointBack(PApplet app, Box2DProcessing box2d, float x, float y, float r_, boolean fixed) {
		r = r_;
		this.box2d = box2d;
		this.app = app;
		// Define a body
		BodyDef bd = new BodyDef();
		if (fixed)
			bd.type = BodyType.STATIC;
		else
			bd.type = BodyType.DYNAMIC;

		// Set its position
		bd.position = box2d.coordPixelsToWorld(x, y);
		body = box2d.world.createBody(bd);

		// Make the body's shape a circle
		// Make the body's shape a circle
		CircleShape cs = new CircleShape();
		cs.m_radius = box2d.scalarPixelsToWorld(r);

		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		// Parameters that affect physics
		fd.density = 1;
		fd.friction = 0.3f;
		fd.restitution = 0.5f;

		body.createFixture(fd);

	}

	// This function removes the particle from the box2d world
	public void killBody() {
		box2d.destroyBody(body);
	}

	// Is the particle ready for deletion?
	public boolean done() {
		// Let's find the screen position of the particle
		Vec2 pos = box2d.getBodyPixelCoord(body);
		// Is it off the bottom of the screen?
		if (pos.y > app.height + r * 2) {
			killBody();
			return true;
		}
		return false;
	}

	public void move(TuioObject tobj) {
		body.setTransform(new Vec2(box2d.coordPixelsToWorld(tobj.getScreenX(app.width), tobj.getScreenY(app.height))), 30);
	}

	public void move() {
		body.setTransform(new Vec2(box2d.coordPixelsToWorld(app.mouseX, app.mouseY)), 30);
	}
}
