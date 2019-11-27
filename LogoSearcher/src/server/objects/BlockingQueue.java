package server.objects;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
	
	private Queue<T> queue;
	
	public BlockingQueue() {
		this.queue = new LinkedList<T>();
	}
	
	public synchronized void offer(T t) {
		queue.add(t);
		notifyAll();
	}

	public synchronized T poll() {
		while(getSize()==0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		T t = queue.poll();
		notifyAll();
		return t;
	}

	public int getSize() {
		return queue.size();
	}

	public boolean isEmpty() {
		return queue.size() == 0;
	}
}
