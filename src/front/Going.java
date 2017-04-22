package front;

import back.GoingBack;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Going  extends GoingBack{

	public Going(PApplet app, Box2DProcessing box2d, int x, int y, int numPoints) {
		super(app, box2d, x, y, numPoints);	
	}
	
	public void show(PApplet app){
		showTie(app);		
	}
	
	private void showTie(PApplet app){		
		
		for (int i = 0; i < particlesLeft.size(); i++) {
			Point p = particlesLeft.get(i);
			p.display();
		}
		
		for (int i = 0; i < particlesRight.size(); i++) {
			Point p = particlesRight.get(i);
			p.display();
		}
		
		table.display(app);
	}

}
