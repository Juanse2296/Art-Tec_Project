package front;

import back.InstructionBack;
import processing.core.PApplet;

public class Instruction extends InstructionBack {
	public Instruction(int x, int y, String[] txt) {
		super(x, y, txt);
	}

	public void show(PApplet app) {		
		if (!endTime()) {
			app.background(0);
			int tam = 25;
			app.fill(255);
			app.textSize(tam);
			app.textAlign(app.CENTER, app.CENTER);
			for (int j = 0; j < txt.length; j++) {
				app.text(txt[j], x, y + (j * tam));
			}
			showTime(app);
		}
	}

	private void showTime(PApplet app) {
		app.fill(255);
		app.textSize(25);
		app.text(tim.getSeconds(), app.width - 150, app.height - 150);
	}

}
