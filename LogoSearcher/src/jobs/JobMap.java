package jobs;

import java.util.ArrayList;
import java.util.HashMap;

public class JobMap {
	
	private HashMap<String, JobQueue> map = new HashMap<>();
	
	public void add(Job job) {
		ArrayList<String> types = job.getTypes();
		for(String t: types) {
			map.get(t).push(job);
		}
	}
	
	public void addType(String type) {
		map.put(type, new JobQueue());
	}
	
}
