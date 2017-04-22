package back;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import org.jbox2d.common.Vec2;

import TUIO.TuioObject;
import front.Bridge;
import front.Emotion;
import front.Form;
import front.Going;
import front.Instruction;
import front.Spinner;
import principal.CONFIG;
import processing.core.PApplet;
import processing.core.PShape;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class DisplayBack implements Observer {
	protected Reactivision react;
	protected Bridge bridge;
	protected PApplet app;
	protected Box2DProcessing box2d;
	protected SoundController sc;
	private boolean created;
	protected ArrayList<Form> forms;
	private ReaderTxt rt;
	protected PShape background;
	protected Emotion emo;
	protected Video v;
	protected int lvSelected, attempts = 4;
	protected Spinner spin;
	protected boolean winner;
	protected int state;
	protected Instruction inst;
	protected Going go;

	public DisplayBack(PApplet app, Reactivision react, Box2DProcessing box2d) {
		this.react = react;
		this.app = app;
		this.box2d = box2d;
		v = new Video(app, "tutorial");
		v.loop();
	}

	protected void startIntruction() {
		app.clear();
		rt = new ReaderTxt(app);
		rt.readInstructions();
		String[] t = rt.getInstructions();
		inst = new Instruction(app.width / 2, app.height / t.length, t);
		inst.addObserver(this);
		state = 1;
	}

	protected void startGame() {
		app.clear();
		inst = null;
		sc = new SoundController(app);
		forms = new ArrayList<Form>();
		int[] n = { 1, 2, 3 };
		lvSelected = getRandom(n);
		startLevel(lvSelected);
	}

	protected void createBridge(int numPoints, int x, int y) {
		int lenght = numPoints * 10;
		bridge = new Bridge(app, box2d, react, lenght, numPoints, x, y);
	}

	protected void showBridge() {
		if (react.getTuioClient() != null && !created) {
			if (react.getTuioClient().getTuioObjectList().size() > 2) {
				int x[] = allowBridge();
				for (int i = 0; i < react.getTuioClient().getTuioObjectList().size(); i++) {
					TuioObject tobj = react.getTuioClient().getTuioObjectList().get(i);
					int a = 100;
					if (tobj.getSymbolID() == 1 && validador(x[0], x[1], x[2])) {
						createBridge(20, tobj.getScreenX(app.width), a);
						created = !created;
						break;
					}
				}
			}
		}
		if (bridge != null) {
			bridge.display();
		}
	}

	private int[] allowBridge() {
		int x[] = new int[3];
		for (int i = 0; i < react.getTuioClient().getTuioObjectList().size(); i++) {
			TuioObject tobj = react.getTuioClient().getTuioObjectList().get(i);
			switch (tobj.getSymbolID()) {
			case 0:
				x[0] = tobj.getScreenX(app.width);
				break;
			case 1:
				x[1] = tobj.getScreenX(app.width);
				break;
			case 2:
				x[2] = tobj.getScreenX(app.width);
				break;
			}
		}
		return x;

	}

	protected void showPeople() {
		if (react.getTuioClient() != null) {
			ArrayList<TuioObject> tuioObjectList = react.getTuioClient().getTuioObjectList();
			for (int i = 0; i < tuioObjectList.size(); i++) {
				TuioObject tobj = tuioObjectList.get(i);
				int y = 0;
				switch (tobj.getSymbolID()) {
				case 0:
					y = (int) app.map(tobj.getScreenY(app.height), CONFIG.maxDown, CONFIG.maxUp, 0, 720);
					app.fill(0, 0, 255);
					break;
				case 1:
					app.fill(0, 255, 0);
					y = (int) app.map(tobj.getScreenY(app.height), CONFIG.maxDown, CONFIG.maxUp, 0, 720);
					break;
				case 2:
					app.fill(255, 0, 0);
					y = (int) app.map(tobj.getScreenY(app.height), CONFIG.maxDown, CONFIG.maxUp, 0, 720);
					break;
				}
				app.ellipse(tobj.getScreenX(app.width), y + CONFIG.positionMap, 20, 20);
			}
		}
	}

	protected void startLevel(int l) {
		rt.readTxtLevels(l);
		forms.addAll(rt.getObjects(box2d, l));
		background = app.loadShape("data/shapes/" + l + "/fondo.svg");
		emo = new Emotion(box2d, sc.getPlayer(), rt.getStart(), 50, 50);
		go = new Going(app, box2d, rt.getStart(), 10);
		state = 2;
	}

	protected void nextLevel(int l) {
		for (int i = 0; i < forms.size(); i++) {
			forms.get(i).killBody();
		}
		forms.clear();
		startLevel(l);
		restarEmotion(false);
	}

	protected void restarEmotion(boolean statusGame) {
		emo.restartPosition(rt.getStart());

		if (statusGame) {
			attempts--;
			if (attempts < 1) {
				gameOver();
				winner = false;
			}
		}
	}

	protected int getRandom(int[] array) {
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}

	protected void gameOver() {
		System.out.println("juego termnado");
		destroyGame();
		spin = new Spinner();
		spin.addObserver(this);
	}

	protected void destroyGame() {
		rt = null;
		sc = null;
		emo = null;
		forms.clear();
		app.clear();
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

}
