package tracking;

import gab.opencv.Contour;
import gab.opencv.OpenCV;
import principal.CONFIG;
import processing.core.PApplet;
import processing.core.PVector;
import processing.video.Capture;

public class Scan {

	Capture cam;
	OpenCV opencv;

	public Scan(PApplet app) {
		scanner(app);
		cam = new Capture(app, CONFIG.camWidth, CONFIG.camHeight);
		cam.start();
		scanner(app);
	}

	public void scanner(PApplet app) {
		opencv = new OpenCV(app, CONFIG.camWidth, CONFIG.camHeight);
		opencv.startBackgroundSubtraction(5, 3, 0.5);
	}

	public void show(PApplet app) {
		if (cam.available()) {
			cam.read();			
			cam.loadPixels();
		}
		opencv.loadImage(cam);	
		opencv.updateBackground();
		opencv.dilate();
		opencv.erode();
		
		app.stroke(255, 0, 0);
		app.strokeWeight(3);
		for (Contour contour : opencv.findContours()) {
			contour.draw();			
			 
		
		}
		
		
		
		app.image(cam, 0, 0);
	}

}
