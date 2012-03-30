package m69.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.ThreadManager;

/**
 * Class used to execute some work in many threads.
 * @author Marcin Górzyñski
 */
public class Worker {
	
	private List<Task> tasks = new ArrayList<Worker.Task>();
	private Thread monitoringThread = null;
	private boolean working = false;
	
	/**
	 * Adds Task to List.
	 * @param r
	 * @throws Exception 
	 */
	public void addTask(Runnable r) throws WorkerException {
		if (!working) {
			tasks.add(new Task(r));	
		} else {
			throw new WorkerException();
		}
	}
	
	/**
	 * Removes Task from List.
	 * @param r
	 * @throws Exception 
	 */
	public void removeTask(Runnable r) throws WorkerException {
		if (!working) {
			tasks.remove(r);
		} else {
			throw new WorkerException();
		}
	}
	
	/**
	 * Executes Tasks
	 * @param blocking
	 */
	public void doWork(boolean blocking) {
		if (blocking) {
			working = true;
			for (int i = 0; i < tasks.size(); i++) {
				tasks.get(i).start();
			}
			monitor();
		} else {
			working = true;
			monitoringThread = ThreadManager.createThreadForCurrentRequest(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < tasks.size(); i++) {
						tasks.get(i).start();
					}
					monitor();
				}
				
			});
		}
	}
	
	/**
	 * Monitor all threads that are executing tasks.
	 */
	private void monitor() {
		while (working) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for (int i = 0; i < tasks.size(); i++) {
				if (tasks.get(i).finished())
					tasks.remove(i);
			}
				
			if (tasks.size() == 0)
				working = false;
		}
	}
	
	/**
	 * Class representing task.
	 * @author Marcin Górzyñski
	 */
	private class Task {
		private Runnable task; 
		private Thread thread;
		private int startedTime = 0;
		private int finishedTime = 0;
		
		public Task(Runnable t) {
			task = t;
			thread = ThreadManager.createThreadForCurrentRequest(t);
		}

		public Thread getThread() {
			return thread;
		}

		public void setThread(Thread thread) {
			this.thread = thread;
		}
		
		public void start() {
			thread.start();
		}
		
		public boolean finished() {
			if (thread.getState() == Thread.State.TERMINATED) {
				return true;
			} else {
				return false;
			}
		}
		
	}
}
