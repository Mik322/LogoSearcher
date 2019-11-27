package server;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
	
	private Queue<T> queue;
	int capacity = -1;
	
	public BlockingQueue() {
		this.queue = new LinkedList<T>();
	}
	
	public synchronized void offer(T t) throws InterruptedException {
		while(capacity != -1 && getSize() == capacity) {
			wait();
		}
		notifyAll();
		queue.add(t);
	}

	public synchronized T poll() {
		while(getSize()==0 && capacity != -1) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		notifyAll();
		return queue.poll();
	}

	public int getSize() {
		return queue.size();
	}

	public boolean isEmpty() {
		return queue.size() == 0;
	}
}
