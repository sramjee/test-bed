package com.ap;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BasicBlockingQueue {
	
	public static void main(String[] args) {
		
		BlockingQueue<Item> queue = new ArrayBlockingQueue<>(1);
		
		final Runnable producer = () -> {
			while(true) {
				try {
					queue.put(createItem());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(Thread.currentThread().isInterrupted()) {
					System.out.println("I'm interrupted... producer");
					return;
				}
			}
		};
		
		Thread producerThread = new Thread(producer);
		producerThread.start();
		

		final Runnable consumer = () -> {
			while(true) {
				try {
					process(queue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(Thread.currentThread().isInterrupted()) {
					System.out.println("I'm interrupted... consumer");
					return;
				}
			}
		};
		
		Thread consumerThread = new Thread(consumer);
		consumerThread.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		producerThread.interrupt();
		consumerThread.interrupt();
		System.out.println("Done!");
	}

	private static void process(Item i) {
		System.out.println("Consumed - "+i.getName());		
	}

	private static Item createItem() {
		Item i = new Item();
		i.setName(Double.toString(Math.random() + Math.random()));
		System.out.println("Produced - "+i.getName());
		return i;
	}
	
}