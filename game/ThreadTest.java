package game;

public class ThreadTest extends Thread{
	
	boolean i = true;
	int a = 10;
	
	public void run() {
		while(i == true && a >= 0) {
			System.out.println("1");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			a--;
		}
	}
	
	public synchronized void check() {
		if(i) {
			i = false;
		}
	}
}
