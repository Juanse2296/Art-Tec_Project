package back;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;

import TUIO.TuioObject;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class SurfaceBack implements Runnable {

	protected ArrayList<Vec2> surface;
	Reactivision react;
	PApplet app;
	

	public SurfaceBack(PApplet app, Box2DProcessing box2d, Reactivision react) {
		this.app = app;
		this.react = react;
		surface = new ArrayList<Vec2>();
		//oldBridge(box2d);
		
	}
	
	public void oldBridge(Box2DProcessing box2d){
		for (int i = 0, x = 100, y = 100; i < 6; i++) {
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
		// ---
		ChainShape chain = new ChainShape();
		chain.createChain(vertices, vertices.length);
		// ---
		BodyDef bd = new BodyDef();
		bd.position.set(0.0f, 0.0f);
		Body body = box2d.createBody(bd);
		// ---
		body.createFixture(chain, 1);
	}

	public void Points() {
		if (react.getTuioClient() != null) {
			ArrayList<TuioObject> tuioObjectList = react.getTuioClient().getTuioObjectList();
			for (int i = 0; i < tuioObjectList.size(); i++) {
				TuioObject tobj = tuioObjectList.get(i);			
			app.ellipse(tobj.getScreenX(app.width), tobj.getScreenY(app.height), 20, 20);
			}
		}
	}



	@Override
	public void run() {
		try {
			while (true) {			
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
