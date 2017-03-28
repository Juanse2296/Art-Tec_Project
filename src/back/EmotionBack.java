package back;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import ddf.minim.AudioSample;
import shiffman.box2d.Box2DProcessing;

public class EmotionBack {
	protected float x, y, w, h;
	protected Box2DProcessing box2d;
	protected Body body;
	protected AudioSample sound;
	public EmotionBack(Box2DProcessing box2d, AudioSample sound,float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.box2d = box2d;
		this.sound=sound;
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
	
	public void soundPlayer(){
		sound.trigger();
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getW() {
		return w;
	}

	public float getH() {
		return h;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setW(float w) {
		this.w = w;
	}

	public void setH(float h) {
		this.h = h;
	}


}
