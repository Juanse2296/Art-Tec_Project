package background;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class DisplayBackground  {

	float gravityPull = 2;
	float airDrag = 0.2f;
	float elasticity = 0.1f;

	ArrayList<Rope> ropes;
	float collisionSize;
	boolean debugMode = false;

	float gravitySlider;
	float airDragSlider;
	float elasticitySlider;
	float collisionSizeSlider;



	public DisplayBackground(PApplet app) {
		ropes = new ArrayList<Rope>();
		int ropeCount = 130;
		// Create a series of ropes along the scene's width.
		for (int i = 0; i < ropeCount; i++) {
			// var x = map(i, 0, ropeCount, width/2-200, width/2+200);
			float x = 10+(10*i);
			float y = 0;
			Rope newRope = new Rope(x, y, 24);
			newRope.start();
			ropes.add(newRope);
		}
	}

	public void draw(PApplet app, PVector pos) {	
		collisionSize = 50;
		// React against collision object.
		for (int i = 0; i < ropes.size(); i++) {
			for (int j = 0; j < ropes.get(i).getPends().size(); j++) {
				Pendulum p = ropes.get(i).getPends().get(j);
				float d = PApplet.dist(pos.x, pos.y, p.getPos().x, p.getPos().y);
				if (d < collisionSize) {
					// Push ball away from collision object.
					PVector force = new PVector(p.getPos().x, p.getPos().y);
					force.sub(app.mouseX, app.mouseY);
					force.normalize();
					force.mult(2);
					p.getAcc().add(force);
				}
			}

			ropes.get(i).display(app);
		}		

	}
	
	public void clear(){
		ropes.clear();
		ropes=null;
	}

}
