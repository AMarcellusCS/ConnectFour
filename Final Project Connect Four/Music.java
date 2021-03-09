/**
 * @author Adrian Marcellus 
 * @version 12/17/2019
 * Final Project Sounds
 * This is the class that plays sounds. 
 */

import java.io.File; 
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Music {
	AudioStream audio;
	String[] sounds = {"SOUNDS/PEW.wav", "SOUNDS/mickey.wav", "SOUNDS/gnome.wav"};
	public Music() {
	}
	public void playSound(String filePath) {
		InputStream music;
		try {
			music = new FileInputStream(new File(filePath));
			audio = new AudioStream(music);
			AudioPlayer.player.start(audio);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void playSound(int x) {
		InputStream music;
		try {
			music = new FileInputStream(new File(sounds[x]));
			audio = new AudioStream(music);
			AudioPlayer.player.start(audio);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void stopSound() {
		AudioPlayer.player.stop(audio);
	}
}
