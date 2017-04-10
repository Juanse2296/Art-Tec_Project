package back;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import shiffman.box2d.Box2DProcessing;

public class FormBack {

	protected Vec2 pos;
	protected int size;

	protected Body body;
	protected Box2DProcessing box2d;

	private String[] points;
	private float r = 0;

	public FormBack(Box2DProcessing box2d, String data) {
		this.box2d = box2d;
		if (getData(data) == 1 && r > 0)
			makeCircleBody(pos, r);
		else
			makeBody(pos);
	}

	public int getData(String data) {
		String[] d = data.split(",");
		int type = Integer.valueOf(d[4]);
		pos = new Vec2(Integer.valueOf(d[0]), Integer.valueOf(d[1]));
		if (type > 0) {
			r = Integer.valueOf(d[5]);
		} else {
			size = Integer.valueOf(d[2]);
			points = d[3].split("/");
		}
		return type;
	}

	private void makeBody(Vec2 center) {
		// Define a polygon (this is what we use for a rectangle)
		PolygonShape sd = new PolygonShape();
		Vec2[] vertices = new Vec2[points.length];
		for (int i = 0; i < points.length; i++) {
			String[] a = points[i].split(";");
			vertices[i] = box2d.vectorPixelsToWorld(new Vec2(Integer.valueOf(a[0]), Integer.valueOf(a[1])));
		}
		sd.set(vertices, vertices.length);
		// Define the body and make it from the shape
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.position.set(box2d.coordPixelsToWorld(center));
		body = box2d.createBody(bd);
		body.createFixture(sd, 1.0f);
		// Give it some initial random velocity
		// body.setLinearVelocity(new Vec2(random(-5, 5), random(2, 5)));
		// body.setAngularVelocity(random(-5, 5));
	}

	private void makeCircleBody(Vec2 center, float r) {
		// Define a body
		BodyDef bd = new BodyDef();
		// Set its position
		bd.position = box2d.coordPixelsToWorld(center.x, center.y);
		bd.type = BodyType.DYNAMIC;
		body = box2d.createBody(bd);

		// Make the body's shape a circle
		CircleShape cs = new CircleShape();
		cs.m_radius = box2d.scalarPixelsToWorld(r);

		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		// Parameters that affect physics
		fd.density = 1;
		fd.friction = 0.01f;
		fd.restitution = 0.3f;

		// Attach fixture to body
		body.createFixture(fd);

		// body.setAngularVelocity(random(-10, 10));
	}
}
