package back;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import shiffman.box2d.Box2DProcessing;

public class EmotionBack {
	protected float x, y, w, h;
	protected Box2DProcessing box2d;
	protected Body body;

	public EmotionBack(Box2DProcessing box2d, float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.box2d = box2d;
		createBody();
	}

	public void createBody() {
		// ---- posicionamiento de objeto en coordenadas de box
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.position.set(box2d.coordPixelsToWorld(x, y));

		// Creacion del body
		 body = box2d.createBody(bd);

		// --Creacion del shape
		CircleShape cir = new CircleShape();
		cir.m_radius= box2d.scalarPixelsToWorld(w/2);			

		// ----- Creacion de FIxture
		FixtureDef fd = new FixtureDef();
		fd.shape = cir;

		/// --caracteristicas
		fd.density = 0.1f;
		fd.friction = 0.3f;
		fd.restitution = 0.5f;

		// --Unir forma al cuerpo con sus caracteristicas
		body.createFixture(fd);
	}


}
