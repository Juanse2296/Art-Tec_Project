package back;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import TUIO.TuioObject;
import front.Bridge;
import front.Form;
import front.Platform;
import principal.CONFIG;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class DisplayBack {
	protected Reactivision react;
	protected Bridge bridge;
	protected PApplet app;
	protected Box2DProcessing box2d;
	protected SoundController sc;
	boolean created;
	protected ArrayList<Platform> plats;
	protected ArrayList<Form> forms;
	private ReaderTxt rt;

	public DisplayBack(PApplet app, Reactivision react, Box2DProcessing box2d) {
		this.react = react;
		this.app = app;
		this.box2d = box2d;
		rt = new ReaderTxt(app);
		sc = new SoundController(app);
		plats = new ArrayList<Platform>();
		forms = new ArrayList<Form>();
		startLevel(rt);
	}

	public void createBridge(int numPoints, int x, int y) {
		int lenght = numPoints * 10;
		bridge = new Bridge(app, box2d, react, lenght, numPoints, x, y);
	}

	protected void showBridge() {
		if (react.getTuioClient() != null) {
			if (react.getTuioClient().getTuioObjectList().size() > 1) {
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

	public void showPeople() {
		if (react.getTuioClient() != null) {
			ArrayList<TuioObject> tuioObjectList = react.getTuioClient().getTuioObjectList();
			for (int i = 0; i < tuioObjectList.size(); i++) {
				TuioObject tobj = tuioObjectList.get(i);

				int b = 400;
				int c = 500;
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

	public void createPlatforms(Box2DProcessing box2d, int num) {
		for (int i = 0; i < num; i++) {
			if (i == num - 1) {
				plats.add(new Platform(box2d, i, 1100, 600, 160, 20, true));
			} else {
				plats.add(new Platform(box2d, i, (int) app.random(200, 1000), (int) app.random(200, 600),
						(int) app.random(20, 80), (int) app.random(20, 80), false));
			}
		}
	}

	public void startLevel(ReaderTxt rt) {
		rt.readTxt(1);
		forms.addAll(rt.getObjects());
	}

}
