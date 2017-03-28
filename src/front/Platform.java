package front;

import org.jbox2d.common.Vec2;

import back.PlatformBack;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Platform extends PlatformBack {

	public Platform(Box2DProcessing box2d, int id, int x, int y, int w, int h, boolean last) {
		super(box2d, id, x, y, w, h, last);

	}

	public void show(PApplet app) {
		Vec2 pos = box2d.getBodyPixelCoord(body);
		float a = body.getAngle();
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(-a);
		app.rectMode(app.CENTER);
		app.rect(0, 0, w, h);
		app.popMatrix();
		
		if(last){
			app.textSize(20);
			app.fill(255);
			app.textAlign(app.CENTER);
			app.text("Meta",pos.x, pos.y+50);
		}
	}
}
