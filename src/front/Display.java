package front;

import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class Display {

	Emotion emo;
	Surface sf;
	PApplet app;
	Bridge bridge;;

	public Display(PApplet app, Box2DProcessing box2d, Reactivision react) {
		this.app = app;
		emo = new Emotion(box2d,app.width / 2, 200, 50, 50);
//		sf= new Surface(app, box2d,react);	
//		Thread t= new Thread(sf);
//		t.start();
		bridge = new Bridge(app,box2d,react,150,20);
	}

	public void show() {
		app.background(0);		
		emo.show(app);
	//	sf.show(app);
		bridge.display();
	}

}
