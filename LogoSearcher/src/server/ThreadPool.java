package server;

public class ThreadPool {

	private final int nThreads;
	private final PoolWorker[] threads;
	private final BlockingQueue<Runnable> queue;
	
	public ThreadPool(int nThreads) {
		this.nThreads = nThreads;
		queue = new BlockingQueue<Runnable>();
		threads = new PoolWorker[nThreads];
	
		for (int i = 0; i < nThreads; i++) {
        threads[i] = new PoolWorker();
        threads[i].start();
		}
	}	
		
	public void execute(Runnable handler){
    synchronized (queue) {
        try {
			queue.offer(handler);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        queue.notify();
    }
}
	
	 private class PoolWorker extends Thread {
	        public void run() {
	            Runnable handler;
	 
	            while (true) {
	                synchronized (queue) {
	                    while (queue.isEmpty()) {
	                        try {
	                            queue.wait();
	                        } catch (InterruptedException e) {
	                            System.out.println("An error occurred while queue is waiting: " + e.getMessage());
	                        }
	                    }
	                    handler = queue.poll();
	                }
	 
	                // If we don't catch RuntimeException,
	                // the pool could leak threads
	                try {
	                    handler.run();
	                } catch (RuntimeException e) {
	                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
	                }
	            }
	        }
	    }

	public void shutdown() {
		for (Thread t : threads) {
	        try {
	            t.join();
	        } catch (InterruptedException e) {}
	    }
		
	}
}
