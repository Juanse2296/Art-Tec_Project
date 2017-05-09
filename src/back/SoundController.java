package back;

import ddf.minim.AudioPlayer;
import ddf.minim.AudioSample;
import ddf.minim.Minim;
import processing.core.PApplet;

public class SoundController {

	private Minim minim;
	private AudioSample player;
	private AudioPlayer soundBack;

	
	public SoundController(PApplet app) {
		minim = new Minim(app);	
		loadSounds();
	}

	public void play() {
		soundBack.loop();		
	}

	private void clear() {
		player = null;
		soundBack = null;
	}

	public void stop() {
		soundBack.pause();		
		clear();
	}

	private void loadSounds() {
		player = minim.loadSample("data/sounds/tabla.mp3");
		soundBack = minim.loadFile("data/sounds/soundback.mp3");
		soundBack.loop();	
	}


	public AudioSample getPlayer() {
		return player;
	}

	public void setPlayer(AudioSample player) {
		this.player = player;
	}

}
