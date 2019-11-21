package server.taskqueue;

import java.util.HashMap;

import streamedobjects.Task;

public class TaskMap {

	private HashMap<String, TaskQueue> map = new HashMap<>();

	public void add(Task task) {
		String type = task.getType();
		map.get(type).push(task);
	}

	public void addType(String type) {
		map.put(type, new TaskQueue());
	}

}
