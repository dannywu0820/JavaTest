package idv.ktw.syntax.thread;

import java.util.concurrent.ThreadLocalRandom;

public class SleepWaitNotifyPractice {
	public static void main(String[] args) throws InterruptedException {				
		try {
			SharedResource data = new SharedResource();
			Thread t1 = new Thread(new Sender(data));
			Thread t2 = new Thread(new Receiver(data));
			
			t1.start();
			t2.start();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	static void demoSleep() throws InterruptedException {
		System.out.println("Before Sleep");
		Thread.sleep(2 * 1000);
		System.out.println("Thread " + Thread.currentThread().getName() + " is woken");
	}
	
	static void demoWait() throws InterruptedException {
		Object LOCK = new Object();
		synchronized(LOCK) {
			System.out.println("Before Wait");
			LOCK.wait(2 * 1000);
			System.out.println("Object '" + LOCK + "' is woken");
		}
	}
}

class SharedResource {
	private String packet;
	private boolean transfer = true;
	
	public synchronized void send(String packet) {
		while(!transfer) {
			try {
				wait();
			}
			catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		transfer = false;
		
		this.packet = packet;
		notifyAll();
	}
	
	public synchronized String receive() {
		while(transfer) {
			try {
				wait();
			}
			catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		transfer = false;
		
		notifyAll();
		return packet;
	}
}

class Sender implements Runnable {
	private SharedResource data;

	Sender(SharedResource data) {
		this.data = data;
	}
	
	@Override
	public void run() {
		String[] packets = {
			"First packet",
			"Second packet",
			"Third packet",
			"Fourth packet",
			"End"	
		};
		
		for(String packet: packets) {
			data.send(packet);
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
			}
			catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}	
}

class Receiver implements Runnable {
    private SharedResource load;
 
    Receiver(SharedResource data) {
		this.load = data;
	}
    
    @Override
    public void run() {
        for(String receivedMessage = load.receive(); !"End".equals(receivedMessage); receivedMessage = load.receive()) {
            
            System.out.println(receivedMessage);

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  
            }
        }
    }
}
