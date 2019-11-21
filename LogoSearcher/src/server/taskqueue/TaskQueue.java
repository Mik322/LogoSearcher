package server.taskqueue;

import java.util.LinkedList;

import streamedobjects.Job;

public class TaskQueue {
	
	private LinkedList<Job> jobs = new LinkedList<>();

	public void push(Job job) {
		jobs.add(job);
	}
	
}
