package back;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import shiffman.box2d.Box2DProcessing;

public class FormBack {

	protected Vec2 pos;
	protected int size;

	protected Body body;
	protected Box2DProcessing box2d;

	private String[] points;

	public FormBack(Box2DProcessing box2d, String data) {
		this.box2d=box2d;
		getData(data);
		makeBody(pos);
	}

	public void getData(String data) {
		String[] d = data.split(",");
		pos = new Vec2(Integer.valueOf(d[0]), Integer.valueOf(d[1]));
		size = Integer.valueOf(d[2]);
		points = d[3].split("/");
	

		System.out.println("pos: " + pos);
		System.out.println("size: " + size);
		//System.out.println("points form: " + form.length);
		System.out.println("--------------------------------------");
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

}
