package front;

import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Display {

	Emotion emo;
	PApplet app;

	public Display(PApplet app, Box2DProcessing box2d) {
		this.app = app;
		emo = new Emotion(box2d,app.width / 2, app.height / 2, 50, 50);
		app.noStroke();
	}

	public void show() {
		app.background(0);		
		emo.show(app);
	}

}
