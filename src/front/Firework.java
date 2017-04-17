package front;

import processing.core.PApplet;

public class Firework {
	private PApplet app;
	private float x, y, oldX, oldY, ySpeed, targetX, targetY, explodeTimer, flareWeight, flareAngle;
	private int flareAmount, duration;
	private boolean launched, exploded, hidden;
	private int flare;

	public Firework(PApplet app) {
		this.app = app;
		launched = false;
		exploded = false;
		hidden = true;

	}

	void pintar() {
		app.smooth();
		if ((launched) && (!exploded) && (!hidden)) {
			launchMaths();
			app.strokeWeight(3);
			app.stroke(255);
			app.line(x, y, oldX, oldY);
		}
		if ((!launched) && (exploded) && (!hidden)) {
			explodeMaths();
			app.noStroke();
			app.strokeWeight(flareWeight);
			app.stroke(flare);
			for (int i = 0; i < flareAmount + 1; i++) {
				app.pushMatrix();
				app.translate(x, y);
				app.point(app.sin(app.radians(i * flareAngle)) * explodeTimer,
						app.cos(app.radians(i * flareAngle)) * explodeTimer);
				app.popMatrix();
			}
		}
		if ((!launched) && (!exploded) && (hidden)) {
			// do nothing
		}
		app.noStroke();

	}

	public void launch() {

		// REPLACING THE MOUSE
		float posX = app.random(100, app.width/2);
		float posY = app.random(100, app.height/2);

		x = oldX = posX + ((app.random(5) * 10) - 25);
		y = oldY = app.height;
		targetX = posX;
		targetY = posY;
		ySpeed = app.random(3) + 2;
		flare = app.color(app.random(3) * 50 + 105, app.random(3) * 50 + 105, app.random(3) * 50 + 105);
		flareAmount = app.ceil(app.random(30)) + 20;
		flareWeight = app.ceil(app.random(3));
		duration = app.ceil(app.random(4)) * 20 + 30;
		flareAngle = 360 / flareAmount;
		launched = true;
		exploded = false;
		hidden = false;
	}

	public void launchMaths() {
		oldX = x;
		oldY = y;
		if (app.dist(x, y, targetX, targetY) > 6) {
			x += (targetX - x) / 2;
			y += -ySpeed;
		} else {
			explode();
		}
	}

	public void explode() {
		explodeTimer = 0;
		launched = false;
		exploded = true;
		hidden = false;
	}

	public void explodeMaths() {
		if (explodeTimer < duration) {
			explodeTimer += 0.4;
		} else {
			hide();
		}
	}

	public void hide() {
		launched = false;
		exploded = false;
		hidden = true;
	}

	public boolean isHidden() {
		return hidden;
	}

}
