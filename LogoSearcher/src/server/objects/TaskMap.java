package server.objects;

import java.util.HashMap;

import streamedobjects.Task;

public class TaskMap {

	private HashMap<String, BlockingQueue<Task>> map = new HashMap<>();

	public synchronized void add(Task task) {
		String type = task.getType();
		map.get(type).offer(task);
	}

	public synchronized void addType(String type) {
		if (!map.containsKey(type)) {
			map.put(type, new BlockingQueue<Task>());
		}
	}

	public BlockingQueue<Task> getQueue(String type) {
		return map.get(type);
	}

}
