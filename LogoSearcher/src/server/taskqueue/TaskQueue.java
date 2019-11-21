package server.taskqueue;

import java.util.LinkedList;

import streamedobjects.Task;

public class TaskQueue {
	
	private LinkedList<Task> tasks = new LinkedList<>();

	public void push(Task task) {
		tasks.add(task);
	}
	
}
