package front;



import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;


public class Main extends PApplet {
		
	Display dp;
	Box2DProcessing box2d;

	public static void main(String[] args) {
		PApplet.main("front.Main", args);
	}
	
	public void settings(){
		size(CONFIG.width,CONFIG.height);
	}
	
	public void setup(){				
		///---		
		box2d= new Box2DProcessing(this);
		box2d.createWorld();
		///---
		dp= new Display(this, box2d);
		smooth();
	}
	public void draw(){		
		dp.show();		
		box2d.step();
	}
	
	public void mouseDragged(){

	}


}
