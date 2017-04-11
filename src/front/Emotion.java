package front;

import org.jbox2d.common.Vec2;

import back.EmotionBack;
import ddf.minim.AudioSample;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Emotion extends EmotionBack {

	public Emotion(Box2DProcessing box2d, AudioSample sound, Vec2 pos, float w, float h) {
		super(box2d, sound, pos, w, h);
	}

	public void show(PApplet app) {
		pos = box2d.getBodyPixelCoord(body);
		float a = body.getAngle();
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(-a);
		app.ellipse(0, 0, w, h);
		app.popMatrix();		
	}
}
