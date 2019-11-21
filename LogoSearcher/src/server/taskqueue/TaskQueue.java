package server.taskqueue;

import java.util.LinkedList;

import streamedobjects.Task;

public class TaskQueue {
	
	private LinkedList<Task> tasks = new LinkedList<>();

	public synchronized void push(Task task) {
		tasks.add(task);
		notifyAll();
	}
	
	public synchronized Task pull() throws InterruptedException {
		while(tasks.size()==0) {
			wait();
		}
		Task task = tasks.poll();
		notifyAll();
		return task;
	}
	
}
