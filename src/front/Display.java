package front;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import back.DisplayBack;
import principal.CONFIG;
import processing.core.PApplet;
import processing.core.PVector;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class Display extends DisplayBack {

	private Firework[] fs = new Firework[CONFIG.fireworks];
	private boolean once;
	private int point;
	private int globalControl;

	public Display(PApplet app, Box2DProcessing box2d, Reactivision react) {
		super(app, react, box2d);
		app.colorMode(PApplet.HSB);
		createFireworks();
	}

	private void createFireworks() {
		for (int i = 0; i < fs.length; i++) {
			fs[i] = new Firework();
		}
	}

	public void launchFireWork(PApplet app) {
		once = false;
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isHidden() && !once) {
				fs[i].launch(app);
				once = true;
			}
		}
	}

	public void show(PApplet app) {
		switch (state) {
		case 0:
			if (v != null)
				v.show(app);
			ready();
			break;
		case 1:
			if (v != null)
				inst.show(app);
			break;
		case 2:
			showGame(app);
			break;
		case 3:
			spin.show(app, winner);
			if (winner)
				showFireworks(app);
			break;
		}
	}

	private void showFireworks(PApplet app) {
		for (int i = 0; i < fs.length; i++) {
			fs[i].pintar(app);
		}
		if (app.frameCount % 30 == 0)
			for (int i = 0; i < 100; i++)
				launchFireWork(app);
	}

	private void showGame(PApplet app) {
		app.background(0);
		 changeHue();
		showBackground(app);
		 dispb.draw(app,new PVector(emo.getPos().x,emo.getPos().y));
		 emo.show(app);
		 showPeople(showBridge());
		 showForms(app);
		 if (!practicelevel)
		 showAttemps(app);
		go.show(app);
		 catchEmotion(app);
		//// ------debe estar al final
		 if (!winner)
		 tryAgain(app);
	}

	private void tryAgain(PApplet app) {
		if (emo.getPos().y > app.height) {
			if (!practicelevel)
				restarEmotion(true, startPostionTemp);
			else
				restarEmotion(false, startPostionTemp);
		}
	}

	public void beginCon(Contact cp) {
		if (emo != null) {
			emo.soundPlayer();
		}
	}

	private void showForms(PApplet app) {
		for (int i = 0; i < forms.size(); i++) {
			Form f = forms.get(i);
			f.show(app, box2d);
		}
		for (int i = 0; i < forms.size(); i++) {
			Form f = forms.get(i);
			if ((f.catchChekpoin(emo.getPos()))) {
				point++;
				forms.remove(f);
				break;
			}
			if (((f.finishLevel(box2d, emo.getPos()))) && point > 3 && practicelevel) {
				nextLevel(2);
				practicelevel = false;
			} else if (((f.finishLevel(box2d, emo.getPos()))) && !practicelevel) {
				state = 3;
				winner = true;
				gameOver();
				break;
			}
		}
	}

	public void clic(PApplet app) {
		Lclick(app);
		Rclick(app);
	}

	private void Lclick(PApplet app) {
		switch (state) {
		case 0:
			v.stop();
			startIntruction();
			break;
		case 1:
			// startGame();
			break;
		case 2:
			// nextLevel(2);
			break;
		}
	}

	private void Rclick(PApplet app) {
		if (app.mouseButton == PApplet.RIGHT) {
			state = 3;
			winner = true;
			gameOver();
		}
	}

	public void key(PApplet app) {
		if (state == 2) {
			go.actionJoint();
		}
	}

	// ----------------------------------

	private void showBackground(PApplet app) {
		app.pushMatrix();
		createParticle(app);
		app.popMatrix();
	}

	private void changeHue() {
		float col = PApplet.map(emo.getPos().y, 0, 850, 0, 255);
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).setHue(col);
		}
		for (int i = 0; i < forms.size(); i++) {
			forms.get(i).setHue(col);
		}
	}

	// ----------------------Metodo que crea administra el funcionamiento del
	// background
	public void createParticle(PApplet app) {
		for (int i = particles.size() - 1; i > -1; i--) {
			particles.get(i).pintar(app);
		}
		float posXran = 0, posYran = 0;
		if (app.millis() - timer >= 3000) {
			timer = app.millis();
			posXran = randomWithRange(10, 1270);
			posYran = randomWithRange(10, 710);
		}
		particles.add(new Particle(posXran, posYran, app));
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).setControl(globalControl);
			if (particles.get(i).getLife() <= 0) {
				particles.remove(i);
			}
		}
	}

	// ---------------Metodo contador que gestiona el tiempo de aparicion de las
	// particulas
	public int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	private void catchEmotion(PApplet app) {
		if (state > 1 && emo != null) {
			if (PApplet.dist(emo.getPos().x, emo.getPos().y, app.mouseX, app.mouseY) < 100 && app.mousePressed) {
				emo.setPos(new Vec2(app.mouseX, app.mouseY));
			}
		}
	}

	private void showAttemps(PApplet app) {
		for (int i = 0; i < attempts; i++) {
			app.ellipse(100 + (25 * i), 50, 20, 20);
		}
	}

	/// ------------------------
}
