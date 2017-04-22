package back;

import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.AudioSample;
import ddf.minim.Minim;
import processing.core.PApplet;

public class SoundController {

	Minim minim;
	AudioSample player;
	AudioInput input;
	AudioPlayer soundBack;

	public SoundController(PApplet app) {
		minim = new Minim(app);	
		loadSounds();
	}

	public void loadSounds() {
		player = minim.loadSample("data/tabla.aif");
		soundBack= minim.loadFile("data/soundback.mp3");
	//	soundBack.play();
	}

	public AudioSample getPlayer() {
		return player;
	}

	public void setPlayer(AudioSample player) {
		this.player = player;
	}	
	
	
}
