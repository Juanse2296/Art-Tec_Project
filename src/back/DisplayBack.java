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
import front.Platform;
import front.Spinner;
import front.Windmill;
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
	protected ArrayList<Platform> plats;
	protected ArrayList<Form> forms;
	private ReaderTxt rt;
	protected PShape background;
	protected Emotion emo;
	protected Video v;
	protected boolean game;
	protected Windmill wm;
	protected int lvSelected, attempts = 4;
	protected Spinner spin;

	public DisplayBack(PApplet app, Reactivision react, Box2DProcessing box2d) {
		this.react = react;
		this.app = app;
		this.box2d = box2d;
		v = new Video(app, "tutorial");	
		spin= new Spinner();	
		spin.addObserver(this);
	}

	public boolean iniGame() {
		rt = new ReaderTxt(app);
		sc = new SoundController(app);
		plats = new ArrayList<Platform>();
		forms = new ArrayList<Form>();
		int[] n = { 1, 2, 3 };
		lvSelected = getRandom(n);
		startLevel(lvSelected);
		emo = new Emotion(box2d, sc.getPlayer(), new Vec2(200, 150), 50, 50);
		wm = new Windmill(app, box2d, 150, 100);
		return game = true;
	}

	protected void createBridge(int numPoints, int x, int y) {
		int lenght = numPoints * 10;
		bridge = new Bridge(app, box2d, react, lenght, numPoints, x, y);
	}

	protected void showBridge() {
		if (react.getTuioClient() != null) {
			if (react.getTuioClient().getTuioObjectList().size() > 2) {
				for (int i = 0; i < react.getTuioClient().getTuioObjectList().size(); i++) {
					TuioObject tobj = react.getTuioClient().getTuioObjectList().get(i);
					int a = 100;
					if (!created && tobj.getSymbolID() == 2) {
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

	protected void showPeople() {
		if (react.getTuioClient() != null) {
			ArrayList<TuioObject> tuioObjectList = react.getTuioClient().getTuioObjectList();
			for (int i = 0; i < tuioObjectList.size(); i++) {
				TuioObject tobj = tuioObjectList.get(i);
				if (tobj.getSymbolID() == 0) {
					int y = (int) app.map(tobj.getScreenY(app.height), CONFIG.maxDown, CONFIG.maxUp, 0, 720);
					app.ellipse(tobj.getScreenX(app.width), y + CONFIG.positionMap, 20, 20);
				}
				if (tobj.getSymbolID() == 1) {
					int y = (int) app.map(tobj.getScreenY(app.height), CONFIG.maxDown, CONFIG.maxUp, 0, 720);
					app.ellipse(tobj.getScreenX(app.width), y + CONFIG.positionMap, 20, 20);
				}
				if (tobj.getSymbolID() == 2) {
					int y = (int) app.map(tobj.getScreenY(app.height), CONFIG.maxDown, CONFIG.maxUp, 0, 720);
					app.ellipse(tobj.getScreenX(app.width), y + CONFIG.positionMap, 20, 20);
				}
			}
		}
	}

	protected void createPlatforms(Box2DProcessing box2d, int num) {
		for (int i = 0; i < num; i++) {
			if (i == num - 1) {
				plats.add(new Platform(box2d, i, 1100, 600, 160, 20, true));
			} else {
				plats.add(new Platform(box2d, i, (int) app.random(200, 1000), (int) app.random(200, 600),
						(int) app.random(20, 80), (int) app.random(20, 80), false));
			}
		}
	}

	protected void startLevel(int l) {
		rt.readTxt(l);
		forms.addAll(rt.getObjects(box2d, l));
		background = app.loadShape("data/shapes/" + l + "/fondo.svg");
	}

	protected void nextLevel(int l) {
		for (int i = 0; i < forms.size(); i++) {
			forms.get(i).killBody();
		}
		forms.clear();
		startLevel(l);
		restarEmotion(false);
	}

	public void restarEmotion(boolean statusGame) {
		emo.restartPosition(rt.getStart());
		if (statusGame) {
			attempts--;
			if (attempts < 1) {
				gameOver();
			}
		}
	}

	public int getRandom(int[] array) {
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}

	public void gameOver() {
		System.out.println("juego termnado");
		destroyGame();
		spin= new Spinner();
	}

	public void destroyGame() {
		game = false;
		rt = null;
		sc = null;
		plats.clear();
		forms.clear();
		emo = null;
		wm = null;
	}	

	public boolean validador(float xUno, float xDos, float xTres) {
		if (xUno < xDos && xUno < xTres && xDos > xUno && xDos < xTres && xTres > xUno && xTres > xDos) {
			return true;
		}
		return false;
	}

	@Override
	public void update(Observable obs, Object obj) {				
		spin=null;	
		v.loop();
	}

}
