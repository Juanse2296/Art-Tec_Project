package front;

import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Display {

	Emotion emo;
	Surface sf;
	PApplet app;

	public Display(PApplet app, Box2DProcessing box2d) {
		this.app = app;
		emo = new Emotion(box2d,app.width / 2, app.height / 2, 50, 50);
		sf= new Surface(app, box2d);	
	}

	public void show() {
		app.background(0);		
		emo.show(app);
		sf.show(app);
	}

}
