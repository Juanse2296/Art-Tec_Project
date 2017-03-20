package front;

import org.jbox2d.common.Vec2;

import back.SurfaceBack;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Surface extends SurfaceBack {

	public Surface(PApplet app, Box2DProcessing box2d) {
		super(app, box2d);
		app.noFill();
	}

	public void show(PApplet app) {		
		app.pushMatrix();
		app.strokeWeight(2);
		app.stroke(255);		
		app.beginShape();
		for (Vec2 v : surface) {
			app.vertex(v.x, v.y);
		}
		app.endShape();
		app.popMatrix();	
	}

}
