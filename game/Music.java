package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;

/**
 * 클래스 명 : Music class
 * 
 * 설명 : 음악을 재생하는 스레드 클래스이다.
 * 
 * 변수 : boolean loop - 음악을 반복 할지 여부
 *        boolean end - 음악이 끝났는지 여부
 *        String path - 음악 파일의 경로
 *        
 * 기능 : start():void - 음악 스레드를 시작한다.
 *        pause():void - 음악을 일시 중지한다.
 *        playBack():void - 일시 중지된 음악을 다시 재생한다.
 *        playStop():void - 음악 스레드를 종료한다.
 *        
 * 
 * @author sim-younghyun
 *
 */
public class Music  extends Thread{
	
	/**
	 * 음악 실행, 중지, 다시 시작하는 객체
	 */
	MusicPlayer musicPlayer;
	
	/**
	 * 음악 반복 여부
	 */
	boolean loop;
	
	/**
	 * 음악 종료 여부
	 */
	boolean end;
	
	/**
	 * 음악 파일 경로
	 */
	String path;
	
	public Music(String path,boolean loop) {
		this.loop = loop;
		this.path = path;
		this.end = false;
	}
	
	public void run() {
		do {
			try {
				FileInputStream input = new FileInputStream(path);
				musicPlayer = new MusicPlayer(input);
				musicPlayer.play();
				musicPlayer.getThread().join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}catch (JavaLayerException e1) {
				e1.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}while(loop);
	}
	
	public void playBack() {
		musicPlayer.resume();
	}
	
	public void playStop() {
		musicPlayer.stop();
		loop = false;
	}
	
	public void playPause() {
		musicPlayer.pause();
	}
}
