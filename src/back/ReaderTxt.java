package back;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import front.Form;
import processing.core.PApplet;
import processing.core.PShape;
import shiffman.box2d.Box2DProcessing;

public class ReaderTxt {

	private String[] lvlsDat;

	PApplet app;
	Vec2 start, end;

	public ReaderTxt(PApplet app) {
		this.app = app;
	}

	public void readTxt(int l) {
		lvlsDat = app.loadStrings("data/levels/level" + l + ".txt");
	}

	public ArrayList<Form> getObjects(Box2DProcessing box2d, int lvl) {
		// getShapes();

		ArrayList<Form> forms = new ArrayList<Form>();
		if (lvlsDat != null) {
			for (int i = 1; i < lvlsDat.length; i++) {
				forms.add(new Form(app, box2d, lvlsDat[i], lvl));
			}
		}

		for (int i = 0; i < forms.size(); i++) {
			Form f = forms.get(i);
			if (f.getName().equals("salida")) {
				start = f.getPos();	
			}
			if (f.getName().equals("llegada")) {
				end = f.getPos();		
			}
		}

		return forms;
	}

	public Vec2 getStart() {
		return start;
	}

	public void setStart(Vec2 start) {
		this.start = start;
	}

	public Vec2 getEnd() {
		return end;
	}

	public void setEnd(Vec2 end) {
		this.end = end;
	}

}
