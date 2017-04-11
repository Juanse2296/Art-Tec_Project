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

	public ReaderTxt(PApplet app) {
		this.app = app;
	}

	public void readTxt(int l) {
		lvlsDat = app.loadStrings("data/levels/level" + l + ".txt");

	}

	public ArrayList<Form> getObjects(Box2DProcessing box2d) {
		//getShapes();
		
		ArrayList<Form> forms = new ArrayList<Form>();
		if (lvlsDat != null) {
			for (int i = 1; i < lvlsDat.length; i++) {
				forms.add(new Form(app,box2d, lvlsDat[i]));
			}
		}
		return forms;
	}



}
