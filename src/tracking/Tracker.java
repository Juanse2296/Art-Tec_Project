package tracking;

import principal.CONFIG;
import processing.core.PApplet;

public class Tracker extends PApplet {

	Scan sc;

	public void settings() {
		size(CONFIG.width, CONFIG.height);
	}

	public void setup() {
		sc = new Scan(this);
	}

	public void draw() {		
		sc.show(this);
	}

}
