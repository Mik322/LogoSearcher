package server.jobs;

import java.util.LinkedList;

public class JobQueue {
	
	private LinkedList<Job> jobs = new LinkedList<>();

	public void push(Job job) {
		jobs.add(job);
	}
	
}
