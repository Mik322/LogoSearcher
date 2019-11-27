package server.objects.threadpool;

import server.objects.BlockingQueue;

public class ThreadPool {

	private final int nThreads;
	private final PoolWorker[] threads;
	private final BlockingQueue<Runnable> queue;

	public ThreadPool(int nThreads) {
		this.nThreads = nThreads;
		queue = new BlockingQueue<Runnable>();
		threads = new PoolWorker[nThreads];

		for (int i = 0; i < this.nThreads; i++) {
			threads[i] = new PoolWorker();
			threads[i].start();
		}
	}

	public void execute(Runnable handler) {
		queue.offer(handler);
	}

	private class PoolWorker extends Thread {
		public void run() {
			while (!interrupted()) {
				queue.poll().run();
			}
		}

	}

	public void shutdown() {
		for (Thread t : threads) {
			t.interrupt();
		}
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
			}
		}
	}
}
