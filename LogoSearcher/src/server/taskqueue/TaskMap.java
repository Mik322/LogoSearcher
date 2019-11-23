package server.taskqueue;

import java.util.HashMap;

import streamedobjects.Task;

public class TaskMap {

	private HashMap<String, TaskQueue> map = new HashMap<>();

	public synchronized void add(Task task) {
		String type = task.getType();
		map.get(type).push(task);
	}

	public synchronized void addType(String type) {
		if (!map.containsKey(type)) {
			map.put(type, new TaskQueue());
		}
	}

	public TaskQueue getQueue(String type) {
		return map.get(type);
	}

}
