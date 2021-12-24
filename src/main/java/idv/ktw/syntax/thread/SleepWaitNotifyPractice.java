package idv.ktw.syntax.thread;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class SleepWaitNotifyPractice {
	public static void main(String[] args) throws InterruptedException {				
		try {
			SharedResource data = new SharedResource();
			Sender s = new Sender(data);
			Receiver r = new Receiver(data);
			
			s.start();
			r.start();
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
	// Follow Up: is it more thread-safe to use AtomicBoolean? 
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
		transfer = true;
		
		notifyAll();
		return packet;
	}
}

class Sender implements Runnable {
	private Thread worker;
	private SharedResource data;

	Sender(SharedResource data) {
		this.data = data;
		this.worker = new Thread(this);
		System.out.println("[" + this.getClass().getName() + "] Thread Created");
	}
	
	@Override
	public void run() {
		System.out.println("[" + this.getClass().getName() + "] Before Run");
		String[] packets = {
			"First packet",
			"Second packet",
			"Third packet",
			"Fourth packet",
			"End"	
		};
		AtomicInteger count = new AtomicInteger(0);
		
		while(count.get() < 5) {
			data.send(packets[count.get()]);
			try {
				count.getAndAdd(1);
				if(count.get() == 3) this.interrupt();
				Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
			}
			catch(InterruptedException e) {
				System.out.println("[" + this.getClass().getName() + "] InterruptedException catched");
				Thread.currentThread().interrupt();
			}
		}
		System.out.println("[" + this.getClass().getName() + "] After Run");
	}
	
	public void start() {
		System.out.println("[" + this.getClass().getName() + "] Before Start");
		this.worker.start();
		System.out.println("[" + this.getClass().getName() + "] After Start");
	}
	
	public void interrupt() {
		System.out.println("[" + this.getClass().getName() + "] this.interrupt() triggered");
		worker.interrupt();
	}
}

class Receiver implements Runnable {
	private Thread worker;
    private SharedResource load;
 
    Receiver(SharedResource data) {
		this.load = data;
		this.worker = new Thread(this);
		System.out.println("[" + this.getClass().getName() + "] Thread Created");
	}
    
    @Override
    public void run() {
    	System.out.println("[" + this.getClass().getName() + "] Before Run");
        for(String receivedMessage = load.receive(); !"End".equals(receivedMessage); receivedMessage = load.receive()) {
            
            System.out.println("[" + this.getClass().getName() + "] Receive: " + receivedMessage);

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  
            }
        }
        System.out.println("[" + this.getClass().getName() + "] After Run");
    }
    
    public void start() {
    	System.out.println("[" + this.getClass().getName() + "] Before Start");
		this.worker.start();
		System.out.println("[" + this.getClass().getName() + "] After Start");
    }
}
