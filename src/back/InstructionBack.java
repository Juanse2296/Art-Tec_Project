package back;

import java.util.Observable;

import principal.CONFIG;
import processing.core.PApplet;
import processing.core.PImage;

public class InstructionBack extends Observable {

	protected String[] txt;
	protected int x, y;
	protected Time tim;
	protected PImage img;

	public InstructionBack(PApplet app, int x, int y, String[] txt) {
		this.x = x;
		this.y = y;
		this.txt = txt;
		tim = new Time();
		tim.Count(1);
		img = app.loadImage("data/context_ui.png");
	}

	protected boolean endTime() {
		if (tim.getSeconds() > CONFIG.instructionTime) {
			setChanged();
			notifyObservers();
			clearChanged();
			return true;
		}
		return false;
	}

	public void clear() {
		x = 0;
		y = 0;
		tim = null;
		txt = null;
	}
}
