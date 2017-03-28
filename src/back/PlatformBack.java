package back;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import shiffman.box2d.Box2DProcessing;

public class PlatformBack {

	protected int x, y, w, h, id;
	protected Box2DProcessing box2d;
	protected Body body;
	protected boolean last;
	public PlatformBack(Box2DProcessing box2d, int id, int x, int y, int w, int h, boolean last) {
		this.box2d = box2d;
		this.id = id;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.last=last;
		createBody();
	}

	public void createBody() {
		// ---- posicionamiento de objeto en coordenadas de box
		BodyDef bd = new BodyDef();
		bd.type = BodyType.STATIC;
		bd.position.set(box2d.coordPixelsToWorld(x, y));

		// Creacion del body
		body = box2d.createBody(bd);

		// --Creacion del shape
		PolygonShape sd = new PolygonShape();
		float box2dW = box2d.scalarPixelsToWorld(w / 2);
		float box2dH = box2d.scalarPixelsToWorld(h /2); 

		sd.setAsBox(box2dW, box2dH);

		// ----- Creacion de FIxture
		FixtureDef fd = new FixtureDef();
		fd.shape = sd;

		/// --caracteristicas
		fd.density = 0.1f;
		fd.friction = 0.3f;
		fd.restitution = 0.5f;

		// --Unir forma al cuerpo con sus caracteristicas
		body.createFixture(fd);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public int getId() {
		return id;
	}

	public boolean isLast() {
		return last;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setW(int w) {
		this.w = w;
	}

	public void setH(int h) {
		this.h = h;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

}
