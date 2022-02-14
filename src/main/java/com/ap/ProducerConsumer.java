package com.ap;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {

	public static void main(String[] args) throws InterruptedException {

		BlockingQueue<Item> queue = new ArrayBlockingQueue<>(1);
		//ProducerConsumerBlockingQueueUsingWaitNotify<Item> queue = new ProducerConsumerBlockingQueueUsingWaitNotify<>(1);
		//ProducerConsumerBlockingQueueUsingLockCondition<Item> queue = new ProducerConsumerBlockingQueueUsingLockCondition<>(1);
		
		final Runnable producer = () -> {
			int produced = 0;
			while (true) {
				if (Thread.currentThread().isInterrupted()) {
					System.out.println(Thread.currentThread().getName() + "I'm interrupted... producer");
					break;
				} else {
					try {
						Item createItem = createItem();
						System.out.println(Thread.currentThread().getName() + " : Producer : "+ createItem.getName() + " : before... ");
						queue.put(createItem);
						System.out.println(Thread.currentThread().getName() + " : Producer : "+ createItem.getName() + " : after... ");
						produced++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("Produced count - " + produced);
		};

		final Runnable consumer = () -> {
			int consumed = 0;
			while (true) {
				if (Thread.currentThread().isInterrupted()) {
					System.out.println(Thread.currentThread().getName() + "I'm interrupted... consumer");
					break;
				} else {
					try {
						process(queue.take());
						consumed++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("Consumed count - " + consumed);
		};
		
		Thread consumerThread = new Thread(consumer);
		consumerThread.start();
		
		Thread producerThread = new Thread(producer);
		producerThread.start();
		
		//consumerThread.join();
		//producerThread.join();

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		consumerThread.interrupt();

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		producerThread.interrupt();
		
		System.out.println("Done!");
	}

	private static void process(Item i) {
		System.out.println(Thread.currentThread().getName() + " Consumed - " + i.getName());
	}

	private static Item createItem() {
		Item i = new Item();
		i.setName(Double.toString(Math.random() + Math.random()));
		return i;
	}

}

class Item {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}