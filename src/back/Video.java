package back;

import processing.core.PApplet;
import processing.video.Movie;

public class Video {

	private Movie movie;

	public Video(PApplet app, String v) {
		movie = new Movie(app, "data/"+v+".mp4");
		movie.loop();
	}

	public void show(PApplet app) {
		app.background(0);
		movie.read();
		app.image(movie, 0, 0);
	}
	public void stop(){
		movie.stop();
		movie=null;
	}
}
