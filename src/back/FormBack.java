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

	protected Vec2 size;
	protected Body body;
	protected int type;
	protected float r;
	protected PShape s;
	protected Vec2 posCheck;

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


	public void makeRectBody(Box2DProcessing box2d, String data, Vec2 s, boolean lock) {
		this.size = s;
		type = 1;
		// Define a polygon (this is what we use for a rectangle)
		PolygonShape sd = new PolygonShape();
		float box2dW = box2d.scalarPixelsToWorld(s.x / 2);
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

		bd.position.set(box2d.coordPixelsToWorld(readPos(data)));
		body = box2d.createBody(bd);
		body.createFixture(fd);
	}

	private Vec2 readPos(String st) {
		String[] pos = st.split(",");
		Vec2 v = new Vec2(Integer.valueOf(pos[1]), Integer.valueOf(pos[2]));
		return v;
	}

	private float getRad(String st) {
		String[] pos = st.split(",");
		float r = Float.valueOf(pos[1]);
		return r;
	}

	private String getShape(String st) {
		String[] pos = st.split(",");
		String s = pos[0];
		return s;
	}
	public void makeCircleBody(PApplet app,Box2DProcessing box2d, String data) {
		 posCheck = readPos(data);
		//s=app.loadShape("data/shape/"+getShape(data)+".svg");
		type = 2;
		r=getRad(data);
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}
}
