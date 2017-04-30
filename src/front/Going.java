package front;

import org.jbox2d.common.Vec2;

import back.GoingBack;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Going extends GoingBack {

	public Going(PApplet app, Box2DProcessing box2d, Vec2 pos, int numPoints) {
		super(app, box2d, pos, numPoints);
	}

	public void show(PApplet app) {
		showTie(app);
		createJoint();
		BridgePosition(app);
	}

	private void showTie(PApplet app) {
		app.pushMatrix();
		for (int i = 0; i < particlesLeft.size(); i++) {
			Point p = particlesLeft.get(i);
			p.display();
		}
		for (int i = 0; i < particlesRight.size(); i++) {
			Point p = particlesRight.get(i);
			p.display();
		}
		if(table!=null)
		table.display(app,box2d);
		app.popMatrix();
		
	}
	
	
	private void BridgePosition(PApplet app){	
		app.pushMatrix();
		app.noFill();
		app.stroke(255);
		app.rect(pos.x, pos.y+200, 200, 50);
		app.popMatrix();
		checkPosition();
	}
	
	private boolean checkPosition(){
		boolean temp=false;
		
		return temp;		
	}

}
