package back;

import ddf.minim.AudioPlayer;
import ddf.minim.AudioSample;
import ddf.minim.Minim;
import processing.core.PApplet;

public class SoundController {

	private Minim minim;
	private AudioSample player;
	private AudioPlayer soundBack;
	private AudioPlayer[] audioGame;
	
	public SoundController(PApplet app) {
		minim = new Minim(app);
		audioGame = new AudioPlayer[3];
		loadSounds();
	}

	public void play() {
		soundBack.loop();		
	}


	public void playAudioGame(int i) {
		audioGame[i].loop();
	}

	public void stopAudioGame(int i) {
		audioGame[i].pause();
	}

	private void clear() {
		player = null;
		soundBack = null;
		audioGame=null;
	}

	public void stop() {
		soundBack.pause();		
		clear();
	}

	private void loadSounds() {
		player = minim.loadSample("data/sounds/tabla.mp3");
		soundBack = minim.loadFile("data/sounds/soundback.mp3");
		soundBack.loop();
		//audioGame[0] = minim.loadFile("data/sounds/felicidad.mp3");
	//	audioGame[1] = minim.loadFile("data/sounds/tranquilidad.mp3");
		//audioGame[2] = minim.loadFile("data/sounds/tristeza.mp3");
	}


	public AudioSample getPlayer() {
		return player;
	}

	public void setPlayer(AudioSample player) {
		this.player = player;
	}

}
