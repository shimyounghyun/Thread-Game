package game;

import java.io.FileInputStream;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.Player;

public class MusicPlayer {
	
 	private final static int NOTSTARTED = 0;
    private final static int PLAYING = 1;
    private final static int PAUSED = 2;
    private final static int FINISHED = 3;
    // the player actually doing all the work
    private Player player;

    // locking object used to communicate with player thread
    private final Object playerLock = new Object();
    
    // status variable what player thread is doing/supposed to do
    private int playerStatus = NOTSTARTED;

    public MusicPlayer(final InputStream inputStream) throws JavaLayerException {
        this.player = new Player(inputStream);
    }

    Thread thread;
    
    public void play() throws JavaLayerException {
        synchronized (playerLock) {
            switch (playerStatus) {
                case NOTSTARTED:
                    Runnable r = new Runnable() {
                        public void run() {
                            playInternal();
                        }
                    };
                    final Thread player = new Thread(r);
                    thread = player;
                    player.setDaemon(true);
                    player.setPriority(Thread.MAX_PRIORITY);
                    playerStatus = PLAYING;
                    player.start();
                    break;
                case PAUSED:
                    resume();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Pauses playback. Returns true if new state is PAUSED.
     */
    public boolean pause() {
        synchronized (playerLock) {
            if (playerStatus == PLAYING) {
                playerStatus = PAUSED;
            }
            return playerStatus == PAUSED;
        }
    }

    /**
     * Resumes playback. Returns true if the new state is PLAYING.
     */
    public boolean resume() {
        synchronized (playerLock) {
            if (playerStatus == PAUSED) {
                playerStatus = PLAYING;
                playerLock.notifyAll();
            }
            return playerStatus == PLAYING;
        }
    }

    /**
     * Stops playback. If not playing, does nothing
     */
    public void stop() {
        synchronized (playerLock) {
            playerStatus = FINISHED;
            playerLock.notifyAll();
        }
    }
    private void playInternal() {
        while (playerStatus != FINISHED) {
    		try {
    			if (!player.play(1)) {
    				break;
    			}
    		} catch (final JavaLayerException e) {
    			break;
    		}
            // check if paused or terminated
            while (playerStatus == PAUSED) {
            	synchronized (playerLock) {
            		try {
            			playerLock.wait();
            		} catch (final InterruptedException e) {
            			// terminate player
            			break;
            		}
				}
            }
        }
        close();
    }

    /**
     * Closes the player, regardless of current state.
     */
    public void close() {
        synchronized (playerLock) {
            playerStatus = FINISHED;
        }
        try {
            player.close();
        } catch (final Exception e) {
            // ignore, we are terminating anyway
        }
    }

	public int getPlayerStatus() {
		return playerStatus;
	}

	public void setPlayerStatus(int playerStatus) {
		this.playerStatus = playerStatus;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}
    
    
}
