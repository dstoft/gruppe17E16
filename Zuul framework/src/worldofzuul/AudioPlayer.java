/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Thilos
 * 
 * This class is an AudioPlayer, it's has a lot of methods to play sounds. 
 * The playMusic is a method for playing music the entire game
 * while the others is for specific events.
 */
public class AudioPlayer {

    public void playMusic() throws Exception {

        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("data/music/music.wav"));
        Clip clip = AudioSystem.getClip();
        clip.open(inputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        Thread.sleep(10000); // looping as long as this thread is alive

    }

    public void playFly() throws Exception {

        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("data/music/fly.wav"));
        Clip clip = AudioSystem.getClip();
        clip.open(inputStream);
        clip.start();
    }

    public void playWarp() throws Exception {

        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("data/music/Warp.wav"));
        Clip clip = AudioSystem.getClip();
        clip.open(inputStream);
        clip.start();
    }

    public void playThanks() throws Exception {

        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("data/music/Thanks.wav"));
        Clip clip = AudioSystem.getClip();
        clip.open(inputStream);
        clip.start();
    }
}
