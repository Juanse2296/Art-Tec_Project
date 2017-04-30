package front;

import org.jbox2d.common.Vec2;
import back.FormBack;
import processing.core.PApplet;
import processing.core.PVector;
import shiffman.box2d.Box2DProcessing;

public class Form extends FormBack {

	private float a;

	public Form() {
		super();
	}

	public void show(PApplet app, Box2DProcessing box2d) {
		switch (name) {
		case "obstaculo":
			pintarObstaculo(app, box2d);
			break;
		default:
		//	display(app, box2d);
			break;
		}

	}

	public void showViejo(PApplet app, Box2DProcessing box2d) {
		if (!name.equals("checkpoint")) {
			if (type > 0) {
				showCircle(app, box2d);
			} else {
				showPolygon(app, box2d);
			}
		} else {
			app.pushMatrix();
		//	app.translate(pos.x, pos.y);
			app.fill(255);
			app.rotate(a);
			app.shapeMode(PApplet.CENTER);
			app.shape(s, 0, 0);
			a += 0.01f;
			app.shapeMode(PApplet.CORNER);
			app.popMatrix();
		}
	}

	public void showCircle(PApplet app, Box2DProcessing box2d) {
		Vec2 pos = box2d.getBodyPixelCoord(body);
		// Get its angle of rotation
		float a = body.getAngle();
		app.ellipseMode(PApplet.CENTER);
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(a);
		app.fill(255);
		// app.ellipse(0, 0, r * 2, r * 2);
		// Let's add a line so we can see the rotation
		// app.line(0, 0, r, 0);
		app.shapeMode(PApplet.CENTER);
		app.shape(s);
		app.shapeMode(PApplet.CORNER);
		app.popMatrix();
	}

	public void showPolygon(PApplet app, Box2DProcessing box2d) {
		Vec2 pos = box2d.getBodyPixelCoord(body);
		float a = body.getAngle();
		// Fixture f = body.getFixtureList();
		// PolygonShape ps = (PolygonShape) f.getShape();
		app.fill(255);
		app.rectMode(PApplet.CENTER);
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(-a);
		app.beginShape();
		app.shapeMode(PApplet.CENTER);
		app.shape(s);
		app.shapeMode(PApplet.CORNER);
		app.endShape();
		app.popMatrix();
	}

	public void display(PApplet app, Box2DProcessing box2d) {
		// We look at each body and get its screen position
		Vec2 pos = box2d.getBodyPixelCoord(body);
		// Get its angle of rotation
		float a = body.getAngle();
		app.rectMode(PApplet.CENTER);
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(-a);
		app.stroke(255);
		app.rect(0, 0, size.x, size.y);
		app.popMatrix();
	}

	/// ------------------------------------------Elementos graficos HENDEL

	// obstaculo
	private int sa = 70;
	private int d = 10;
	private int num = 0;
	// checkpoint
	private int NUM = 24;

	public void pintarObstaculo(PApplet app, Box2DProcessing box2d) {
		PVector[] pts = new PVector[3];
		Vec2 pos = box2d.getBodyPixelCoord(body);	
		app.noStroke();
		// pintarSoportes(app, posX, posY, dir);
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		for (int i = 0; i < 3; i++) {
			pts[i] = new PVector(PApplet.sin(PApplet.radians(i * 120 + num)) * sa,
					PApplet.cos(PApplet.radians(i * 120 + num)) * sa);
		}
		for (int x = -sa; x <= sa; x += d) {
			for (int y = -sa; y <= sa; y += d) {
				if (crearTriangulo(new PVector(x, y), pts)) {
					app.fill(255);
					app.ellipse(x, y, d / 1, d / 1);
				} else {
					app.fill(0, 0);
					app.ellipse(x, y, d / 1.5f, d / 1.5f);
				}
			}
		}
		num += 1;
		app.popMatrix();

	}

	private void pintarSoportes(PApplet app, int posX, int posY, String dir) {
		if (dir.equals("arriba")) {
			app.rectMode(PApplet.CORNER);
			app.ellipseMode(PApplet.CENTER);
			app.fill(80);
			app.rect(posX - 13, posY - 3, 20, -(720));
			app.fill(150);
			app.rect(posX - 10, posY, 20, -(720));
			app.fill(200, 100, 100);
			app.ellipse(posX, posY, 25, 25);		
		}
		if (dir.equals("abajo")) {
			app.rectMode(PApplet.CORNER);
			app.ellipseMode(PApplet.CENTER);
			app.fill(80);
			app.rect(posX - 13, posY - 3, 20, (720));
			app.fill(150);
			app.rect(posX - 10, posY, 20, (720));
			app.fill(200, 100, 100);
			app.ellipse(posX, posY, 25, 25);
	
		}
		if (dir.equals("izquierda")) {
			app.rectMode(PApplet.CORNER);
			app.ellipseMode(PApplet.CENTER);
			app.fill(80);
			app.rect(posX - 3, posY - 13, -1280, 20);
			app.fill(150);
			app.rect(posX, posY - 10, -1280, 20);
			app.fill(200, 100, 100);
			app.ellipse(posX, posY, 25, 25);
		
		}
		if (dir.equals("derecha")) {
			app.rectMode(PApplet.CORNER);
			app.ellipseMode(PApplet.CENTER);
			app.fill(80);
			app.rect(posX - 3, posY - 13, 1280, (20));
			app.fill(150);
			app.rect(posX, posY - 10, 1280, (20));
			app.fill(200, 100, 100);
			app.ellipse(posX, posY, 25, 25);
	
		}
	}

	private boolean crearTriangulo(PVector l, PVector[] pts) {
		PVector p1 = pts[0];
		PVector p2 = pts[1];
		PVector p3 = pts[2];
		float d = ((p2.y - p3.y) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.y - p3.y));
		float a = ((p2.y - p3.y) * (l.x - p3.x) + (p3.x - p2.x) * (l.y - p3.y)) / d;
		float b = ((p3.y - p1.y) * (l.x - p3.x) + (p1.x - p3.x) * (l.y - p3.y)) / d;
		float c = 1 - a - b;
		return 0 <= a && a <= 1 && 0 <= b && b <= 1 && 0 <= c && c <= 1;
	}

	public void pintarCheckpoint(PApplet app, int posX, int posY) {
		app.pushMatrix();
		app.translate(posX, posY);
		for (int i = 0; i < NUM; i++) {
			float angle = i * PApplet.TWO_PI / NUM;
			float v = PApplet.pow(PApplet.abs(PApplet.sin(angle / 2 + app.frameCount * 0.03f)), 4);
			float r = PApplet.map(v, 0, 1, 3, 5);
			app.fill(app.lerpColor(app.color(20, 150, 100), app.color(20 + 15, 150 + 10, 100 + 10), v));
			app.ellipse((50 + r) * PApplet.cos(angle), (50 + r) * PApplet.sin(angle), r * 2, r * 2);
		}
		app.popMatrix();
	}

	// ----Fin de la clase

}
