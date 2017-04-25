package front;

import processing.core.PApplet;
import processing.core.PVector;

public class Particle {

	private float life, lifeRate, angle, maxScale, hue, rotateRate, maxOffset;
	private PVector pos;
	private int control;

	public Particle(PApplet app, float x, float y) {
		life = 1.0f;
		lifeRate = app.random(0.005f, 0.02f);
		angle = app.map(app.cos(app.radians(app.frameCount * 5)), -1, 1, -180, 180);
		maxScale = app.max((float) 0.25, app.abs((float) (app.sin(app.radians(app.frameCount * 5)) * 1.5)));

		// --
		rotateRate = app.random(-200, 200);
		maxOffset = app.random(50, 300);
		// ----
		pos = new PVector(x, y);
		int control = 0;
		hue = 0;
	}

	void pintar(PApplet app) {
		app.colorMode(app.HSB, 255);
		float offset = app.map(life, 1, 0, 0, maxOffset); // Pushes out along x
														// axis.

		// Scales from particle's origin pivot.
		float s;
		switch (control) {
		case 0:
			s = app.map(life, 1, 0, 0, maxScale);
			break;

		case 1:
			s = app.map(life, 1, 0, maxScale, 0);
			break;

		case 2:
			s = app.noise(app.map(life, 1, 0, 10, 0));
			break;

		default:
			s = app.map(life, 1, 0, 0, maxScale);
			break;
		}

		float t = app.map(life, 1, 0, 0, 1); // Represents the time of the
											// particle's life.

		float opacity = app.map(life, 1, 0, 255, 0);

		app.strokeWeight(5);
		app.stroke(app.color(hue, 255, 200, opacity * 0.5f)); // Show stroke slightly
														// darker.
		app.fill(app.color(hue, 255, 255, opacity * 0.8f));

		app.pushMatrix();

		// Creates a spiral motion.
		app.translate(pos.x, pos.y);
		app.rotate(app.radians(angle + t * rotateRate));
		app.scale(s);

		app.ellipse(offset, 0, 20, 20);

		app.popMatrix();

		life -= lifeRate;
		app.colorMode(app.RGB, 255);
		app.noStroke();
	}

	public float getLife() {
		return life;
	}

	// public void setInverse(boolean inver) {
	// inverse = inver;
	// }
	public void setControl(int cont) {
		control = cont;
	}

	public void setHue(float h) {
		hue = h;
	}

	// ---
}
