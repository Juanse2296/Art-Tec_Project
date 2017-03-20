package front;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import TUIO.TuioObject;
import back.BridgeBack;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class Bridge extends BridgeBack {

	Bridge(PApplet app, Box2DProcessing box2d, Reactivision react, float l, int n) {
		super(app, box2d, react, l, n);
		// TODO Auto-generated constructor stub
	}

	// Draw the bridge
	public void display() {
		for (Point p : particles) {
			app.fill(255);
			p.display();

		}
		
		
		
		
		
		if ((app.mousePressed && app.mouseButton==app.LEFT)) {
			particles.get(0).move();
		}else if ((app.mousePressed && app.mouseButton==app.RIGHT)) {
			particles.get(particles.size()-1).move();
		}
		
		move();

	}
	
	public void move(){		
		if (react.getTuioClient() != null) {
			ArrayList<TuioObject> tuioObjectList = react.getTuioClient().getTuioObjectList();
			for (int i = 0; i < tuioObjectList.size(); i++) {
				TuioObject tobj = tuioObjectList.get(i);
				
				if(tobj.getSymbolID()==3){
					particles.get(0).move(tobj);
					app.ellipse(tobj.getScreenX(app.width), tobj.getScreenY(app.height), 20, 20);
				}else if(tobj.getSymbolID()==6){
					particles.get(particles.size()-1).move(tobj);
					app.ellipse(tobj.getScreenX(app.width), tobj.getScreenY(app.height), 20, 20);
				}
				
		
			}
		}
	}

}
