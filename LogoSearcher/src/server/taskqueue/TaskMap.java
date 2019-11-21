package server.taskqueue;

import java.util.ArrayList;
import java.util.HashMap;

import streamedobjects.Job;

public class TaskMap {
	
	private HashMap<String, TaskQueue> map = new HashMap<>();
	
	public void add(Job job) {
		ArrayList<String> types = job.getTypes();
		for(String t: types) {
			map.get(t).push(job);
		}
	}
	
	public void addType(String type) {
		map.put(type, new TaskQueue());
	}
	
}
