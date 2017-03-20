package front;

import org.jbox2d.common.Vec2;

import back.EmotionBack;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Emotion extends EmotionBack {

	public Emotion(Box2DProcessing box2d, float x, float y, float w, float h) {
		super(box2d, x, y, w, h);
	}

	public void show(PApplet app) {
		Vec2 pos = box2d.getBodyPixelCoord(body);
		float a = body.getAngle();
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(-a);
		app.ellipse(0, 0, w, h);
		app.popMatrix();

		
	}

}
