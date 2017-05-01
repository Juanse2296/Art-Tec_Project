package front;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import back.DisplayBack;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class Display extends DisplayBack {

	Firework[] fs = new Firework[10];
	boolean once;
	private int point;

	public Display(PApplet app, Box2DProcessing box2d, Reactivision react) {
		super(app, react, box2d);
		app.colorMode(PApplet.HSB);
		createFireworks();
	}

	private void createFireworks() {
		for (int i = 0; i < fs.length; i++) {
			fs[i] = new Firework(app);
		}
	}

	public void launchFireWork() {
		once = false;
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isHidden() && !once) {
				fs[i].launch();
				once = true;
			}
		}
	}

	public void show() {
		switch (state) {
		case 0:
			if (v != null)
				v.show(app);
			break;
		case 1:
			if (v != null)
				inst.show(app);
			break;
		case 2:
			showGame();
			break;
		case 3:
			spin.show(app, winner);
			if (winner)
				showFireworks();
			break;
		}
	}

	private void showFireworks() {
		for (int i = 0; i < fs.length; i++) {
			fs[i].pintar();
		}
		if (app.frameCount % 30 == 0)
			for (int i = 0; i < 100; i++)
				launchFireWork();
	}

	private void showGame() {
		app.background(0);
		showBackground();
		emo.show(app);
		showBridge();
		showPeople();
		showForms();
		go.show(app);
		/// ------debe estar al final
		if(!winner)tryAgain();
	}

	private void tryAgain() {
		if (emo.getPos().y > app.height) {
			if (lvSelected > 3)
				restarEmotion(true);
			else
				restarEmotion(false);
		}
	}

	public void beginCon(Contact cp) {
		if (emo != null) {
			emo.soundPlayer();
		}
	}

	private void showForms() {
		for (int i = 0; i < forms.size(); i++) {
			Form f = forms.get(i);
			f.show(app, box2d);
		}
		for (int i = 0; i < forms.size(); i++) {
			Form f = forms.get(i);
			// if(f.catchChekpoin(box2d,emo.getPos()))point++;
			// System.out.println(point);
			if (f.catchChekpoin(new Vec2(app.mouseX, app.mouseY))) {
				point++;
				if(point>3){
					practicelevel=false;
				}
				forms.remove(f);
				break;
			}
			if(f.finishPracticeLeve(box2d, new Vec2(app.mouseX, app.mouseY)) && practicelevel){
				nextLevel(2);
			}else if(f.finishPracticeLeve(box2d, new Vec2(app.mouseX, app.mouseY)) && !practicelevel){
				state = 3;
				winner = true;
				gameOver();
				//break;
			}
		}
	}

	public void clic() {
		Lclick();
		Rclick();
	}

	private void Lclick() {
		switch (state) {
		case 0:
			v.stop();
			startIntruction();
			break;
		case 1:
			// startGame();
			break;
		case 2:
			nextLevel(2);
			break;
		}
	}

	private void Rclick() {
		if (app.mouseButton == PApplet.RIGHT) {
			state = 3;
			winner = true;
			gameOver();
		}
	}

	public void key() {
		if (state == 2) {
			go.actionJoint();
		}

		/// -----

		if (app.key == 'w') {
			globalControl = 0;
			globalHue = 0;
		}
		// --------Activador de TRANQUILIDAD
		if (app.key == 's') {

			globalControl = 1;
			globalHue = 105;
		}
		// --------Activador de TRISTEZA
		if (app.key == 'x') {

			globalControl = 2;
			globalHue = 180;
		}
	}

	// ----------------------------------

	private void showBackground() {
		app.pushMatrix();
		createParticle();
		app.popMatrix();
	}

	public void manageColorBack() {

		// ----------------------------Controla las tranciones de color de cada
		// emocion
		switch (colorManage) {
		case 0:
			globalHue += 0.1;
			break;
		case 1:
			globalHue -= 0.1;
			break;

		default:
			globalHue -= 0.1;
			break;
		}
		// ---------------------------Controla los niveles de Hue para mostrar
		// el color indicado en cada emocion
		switch (globalControl) {
		case 0:
			if (globalHue <= 0) {
				colorManage = 0;
			}
			if (globalHue >= 60) {
				colorManage = 1;
			}

			break;
		case 1:
			if (globalHue <= 105) {
				colorManage = 0;
			}
			if (globalHue >= 140) {
				colorManage = 1;
			}

			break;
		case 2:
			if (globalHue <= 170) {
				colorManage = 0;
			}
			if (globalHue >= 220) {
				colorManage = 1;
			}
			break;
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).setHue(globalHue);
		}
	}

	// ----------------------Metodo que crea administra el funcionamiento del
	// background
	public void createParticle() {
		for (int i = particles.size() - 1; i > -1; i--) {
			particles.get(i).pintar(app);

			if (particles.get(i).getLife() < 0) {
				// particles = splice(i, 1);
			}
		}

		if (app.millis() - timer >= 1000) {
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
		// manageColorBack();
	}

	// ---------------Metodo contador que gestiona el tiempo de aparicion de las
	// particulas
	public int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	/// ------------------------
}
