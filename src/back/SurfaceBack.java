package back;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;

import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class SurfaceBack {

	protected ArrayList<Vec2> surface;
	

	public SurfaceBack(PApplet app,Box2DProcessing box2d) {
		surface = new ArrayList<Vec2>();
		ChainShape chain = new ChainShape();

		for (int i = 0, x = 100, y = 100; i < 5; i++) {
			x = 100 + (200 * i);
			y = (int) app.random(100, 700);
			surface.add(new Vec2(x, y));
		}

		/// -----

		Vec2[] vertices = new Vec2[surface.size()];

		for (int i = 0; i < vertices.length; i++) {
			Vec2 edge = box2d.coordPixelsToWorld(surface.get(i));
			vertices[i] = edge;
		}
		
		//---
		 chain.createChain(vertices,vertices.length);
		 
		 //---
		  BodyDef bd = new BodyDef();
		    bd.position.set(0.0f,0.0f);
		    Body body = box2d.createBody(bd);
		 
		    body.createFixture(chain,1);
	}

}
