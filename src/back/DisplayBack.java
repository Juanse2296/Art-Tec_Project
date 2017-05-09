package back;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import org.jbox2d.common.Vec2;
import TUIO.TuioObject;
import background.DisplayBackground;
import front.Bridge;
import front.Emotion;
import front.Form;
import front.Going;
import front.Instruction;
import front.Particle;
import front.Spinner;
import principal.CONFIG;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class DisplayBack implements Observer {

	// ---Variables
	protected Reactivision react;
	protected Bridge bridge;
	protected PApplet app;
	protected Box2DProcessing box2d;
	protected SoundController sc;
	protected ArrayList<Form> forms;
	protected Emotion emo;
	protected Video v;
	protected Spinner spin;
	protected boolean winner;
	protected int attempts = CONFIG.attempts;
	protected int state;
	protected int timer;
	protected Instruction inst;
	protected Going go;
	protected boolean practicelevel = true;
	protected Vec2 startPostionTemp;
	protected boolean insideSensibleArea;
	protected boolean players;
	protected ArrayList<Particle> particles = new ArrayList<Particle>();
	protected DisplayBackground dispb;
	protected int point = 2;
	protected boolean arrow;
	private int globalControl;
	// --------------

	public DisplayBack(PApplet app, Reactivision react, Box2DProcessing box2d) {
		this.react = react;
		this.app = app;
		this.box2d = box2d;
		video();
	}

	private void video() {
		v = new Video(app, "tutorial");
		v.loop();
	}

	protected void startIntruction() {
		app.clear();
		spin = null;
		inst = new Instruction(app);
		inst.addObserver(this);
		state = CONFIG.state;
	}

	protected void startGame() {
		app.clear();
		inst = null;
		if (sc == null) {
			sc = new SoundController(app);
		}

		if (forms == null) {
			forms = new ArrayList<Form>();
		}
		startLevel(1);
		// sc.playAudioGame(0);
	}

	protected void moveParticle() {
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

	protected boolean createBridge(int numPoints, int x, int y) {
		int lenght = numPoints * 10;
		if (bridge == null) {
			bridge = new Bridge(app, box2d, react, lenght, numPoints, x, y);
			return true;
		}
		return false;
	}

	private boolean bridgeCreated(Vec2[] v) {
		if (bridge == null) {
			for (int i = 0; i < react.getTuioClient().getTuioObjectList().size(); i++) {
				TuioObject tobj = react.getTuioClient().getTuioObjectList().get(i);
				int a = 250;
				if (v[0] != null && v[1] != null && v[2] != null) {
					if ((tobj.getSymbolID() == 1) && (validador(v[0].x, v[1].x, v[2].x))
							&& (go.checkPosition(v[0], v[1], v[2]))) {
						return createBridge(20, tobj.getScreenX(app.width), a);
					}
				}
			}
		} else {
			return true;
		}
		return false;
	}

	protected void showArrow() {
		for (int i = 0; i < forms.size(); i++) {
			forms.get(i).setDisplay(arrow);
		}
		go.setDisplayText(!arrow);
	}

	protected Vec2[] showBridge() {
		Vec2 v[] = allowBridge();
		if (bridgeCreated(v)) {
			if (v[0] != null && v[1] != null && v[2] != null) {
				if (go.checkPosition(v[0], v[1], v[2]) && !insideSensibleArea) {
					go.actionJoint();
					arrow = true;
					insideSensibleArea = true;
				} else if (!go.checkPosition(v[0], v[1], v[2])) {
					insideSensibleArea = false;
				}
			}
			// ---
			bridge.display(v);
		}
		return v;
	}

	protected boolean interactionForms(Form f) {
		if ((f.catchChekpoin(emo.getPos()))) {
			point--;
			forms.remove(f);
			return true;
		}
		if (((f.finishLevel(box2d, emo.getPos()))) && point < 1 && practicelevel) {
			nextLevel(2);
			practicelevel = false;
		} else if (((f.finishLevel(box2d, emo.getPos()))) && !practicelevel) {
			state = 3;
			winner = true;
			gameOver();
			return true;
		}
		return false;
	}

	private float playersX = 0;

	/**
	 * pediente del mapeo para las posiciones porque puede varias
	 * dependiendo de la posicion de la camara y la distancia en la que esta la personas
	 * @return
	 */
	
	private Vec2[] allowBridge() {
		Vec2 v[] = new Vec2[3];
		if (react.getTuioClient() != null) {
			float y = 0;
			for (int i = 0; i < react.getTuioClient().getTuioObjectList().size(); i++) {
				TuioObject tobj = react.getTuioClient().getTuioObjectList().get(i);
				if (tobj != null) {
					if (tobj.getSymbolID() == 1) {
						playersX = tobj.getScreenX(app.width);
					}
					switch (tobj.getSymbolID()) {
					case 0:					
						y =PApplet.map(tobj.getScreenY(app.height), CONFIG.maxDown, CONFIG.maxUp,0,720);
						v[0] = new Vec2(playersX-150, y);
						break;
					case 1:
						y = tobj.getScreenY(app.width);
						v[1] = new Vec2(tobj.getScreenX(app.width), 360);
						break;
					case 2:
						y =PApplet.map(tobj.getScreenY(app.height), CONFIG.maxDown, CONFIG.maxUp,0,720);
						v[2] = new Vec2(playersX+150, y);
						break;
					}
				}
			}
		}
		return v;
	}

	protected void showPeople(Vec2[] v) {
		app.colorMode(PApplet.HSB, 255);
		app.noStroke();
		for (int i = 0; i < v.length; i++) {
			if (v[i] != null) {
				switch (i) {
				case 0:
					app.fill(100, 255, 255);
					app.ellipse(v[i].x, v[i].y, 20, 20);
					break;
				case 1:
					app.fill(160, 255, 255);
					app.ellipse(v[i].x, v[i].y, 20, 20);
					break;
				case 2:
					app.fill(0, 255, 255);
					app.ellipse(v[i].x, v[i].y, 20, 20);
					break;
				}

			}
		}
	}

	protected void startLevel(int l) {
		makeObjects(l);
		if (emo == null) {
			emo = new Emotion(app, box2d, sc.getPlayer(), startPostionTemp, 50, 50);
		}
		if (go == null) {
			go = new Going(app, box2d, startPostionTemp, 10);
		}
		if (dispb == null) {
			dispb = new DisplayBackground(app, emo);
			dispb.start();
		}
		state = 2;
	}

	public void makeObjects(int l) {
		String[] data = app.loadStrings("data/levels/level" + l + ".txt");
		String[] names = getName(data);
		for (int i = 1; i < names.length; i++) {
			Form f = new Form(names[i]);
			switch (names[i]) {
			case "checkpoint":
				f.makeCircleBody(app, box2d, data[i]);
				f.setP(app.loadImage("data/arrow.png"));
				break;
			case "obstacle":
				f.makeRectBody(box2d, data[i], new Vec2(100, 100), true);
				break;
			case "finish":
				f.makeRectBody(box2d, data[i], new Vec2(150, 50), true);
				break;
			case "start":
				String[] temp = data[i].split(",");
				startPostionTemp = new Vec2(Integer.valueOf(temp[1]), Integer.valueOf(temp[2]));
				break;
			}
			forms.add(f);
		}
	}

	private String[] getName(String[] data) {
		String[] a = new String[data.length];
		for (int i = 1; i < data.length; i++) {
			String[] temp = data[i].split(",");
			a[i] = temp[0];
		}
		return a;
	}

	protected void nextLevel(int l) {
		point = 3;
		for (int i = 0; i < forms.size(); i++) {
			forms.get(i).killBody(box2d);
		}
		forms.clear();
		startLevel(l);
		restarEmotion(false, new Vec2(startPostionTemp.x,startPostionTemp.y+100));
	}

	protected void restarEmotion(boolean statusGame, Vec2 pos) {
		emo.restartPosition(pos);
		if (statusGame) {
			attempts--;
			if (attempts < 1) {
				gameOver();
				winner = false;
			}
		}
		arrow = false;
	}

	protected int getRandom(int[] array) {
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}

	protected void gameOver() {
		destroyGame();
		if (spin == null) {
			spin = new Spinner();
			spin.addObserver(this);
		}
	}

	protected void destroyGame() {
		sc.stop();

		sc = null;
		emo = null;
		go = null;
		dispb.clear();
		dispb = null;
		if (forms != null)
			forms.clear();
		app.clear();
		state = 3;
	}

	private boolean validador(float xUno, float xDos, float xTres) {
		if (xUno < xDos && xUno < xTres && xDos > xUno && xDos < xTres && xTres > xUno && xTres > xDos) {
			return true;
		}
		return false;
	}

	private void startVideo() {
		state = 0;
		v.loop();
		spin = null;
	}

	@Override
	public void update(Observable obs, Object obj) {
		if (obs instanceof Spinner) {
			startVideo();
		}
		if (obs instanceof InstructionBack) {
			startGame();
		}
	}

	protected void ready() {
		if (playGame() && !players) {
			players = true;
			v.stop();
			startIntruction();
		}
	}

	protected boolean playGame() {
		Vec2[] v = allowBridge();
		if (v[0] != null && v[1] != null && v[2] != null) {
			return true;
		}
		return false;
	}

	// particulas
	public int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	protected int stateNumber;

	protected void changeStateBackgroundEffect(Emotion emo, int y) {
		if (emo.getPos().y < y / 3 && stateNumber != 0) {
			stateNumber = 0;
			for (int i = particles.size() - 1; i > -1; i--) {
				particles.get(i).setControl(stateNumber);
				;
			}
		}
		// --------
		if (emo.getPos().y > y / 3 && stateNumber != 1) {
			stateNumber = 1;
			for (int i = particles.size() - 1; i > -1; i--) {
				particles.get(i).setControl(stateNumber);
				;
			}
		}
		// --
		if (emo.getPos().y > (y / 3) * 2 && stateNumber != 2) {
			stateNumber = 2;
			for (int i = particles.size() - 1; i > -1; i--) {
				particles.get(i).setControl(stateNumber);
				;
			}
		}
	}

}
