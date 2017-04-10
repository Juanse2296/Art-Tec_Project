package front;

import org.jbox2d.common.Vec2;

import back.FormBack;
import processing.core.PApplet;

public class Form extends FormBack {

	public Form(String data) {
		super(data);

	}

	public void show(PApplet app) {
		app.fill(255);
		app.beginShape();
		for (int i = 0; i < form.length; i++) {
			app.vertex(form[i].x, form[i].y);
		}
		app.endShape();
	}

}
