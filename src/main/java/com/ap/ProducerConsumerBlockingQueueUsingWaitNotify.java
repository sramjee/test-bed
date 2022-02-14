package com.ap;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerBlockingQueueUsingWaitNotify<E> {
	private Queue<E> queue;
	private int max = 16;

	private Object sharedQ = new Object();

	public ProducerConsumerBlockingQueueUsingWaitNotify(int size) {
		queue = new LinkedList<>();
		this.max = size;
	}

	public void put(E e) throws InterruptedException {
		synchronized (sharedQ) {
			if (queue.size() == max) {
				sharedQ.wait(); // Releases the key held by this thread and puts the thread in a WAIT state
			}
			queue.add(e);
			sharedQ.notifyAll();
		}
	}

	public E take() throws InterruptedException {
		E item;
		synchronized (sharedQ) {
			if (queue.size() == 0) {
				sharedQ.wait(); // Releases the key held by this thread and puts the thread in a WAIT state
			}
			item = queue.remove();
			sharedQ.notifyAll();
		}
		return item;
	}
}