package com.lefu.async.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lefu.async.Flow;
import com.lefu.async.QueueContainer;
import com.lefu.async.core.SimpleQueueContainer;
import com.lefu.async.disruptor.DefaultDisruptorQueue;

import junit.framework.TestCase;

public class QueueTest extends TestCase {
	public static final String FIRST = "firstQueue";
	public static final String HELLO = "HelloQueue";
	public static final String TIP = "TipQueue";
	public static final String SECOND = "SecondQueue";
	public static final String EXCEPTION = "ExceptionQueue";
	
	private QueueContainer queueContainer;
	
	@Before
	public void setUp() {
		queueContainer = new SimpleQueueContainer();
		DefaultDisruptorQueue first = new DefaultDisruptorQueue(1024);
		first.setName(FIRST);
		first.setEventListener(new FisrtEventListener());
		first.setThreads(5);
		first.setExceptionQuene(EXCEPTION);
		DefaultDisruptorQueue queue = new DefaultDisruptorQueue(1024);
		queue.setName(TIP);
		queue.setLogUseTime(false);
		queue.setEventListener(new TipEventListener());
		queue.setThreads(3);
		DefaultDisruptorQueue queue2 = new DefaultDisruptorQueue(1024);
		queue2.setName(HELLO);
		queue2.setEventListener(new HelloEventListener());
		queue2.setThreads(3);
		DefaultDisruptorQueue queue3 = new DefaultDisruptorQueue(1024);
		queue3.setName(SECOND);
		queue3.setEventListener(new SecondEventListener());
		queue3.setThreads(3);
		List<String> depend = new ArrayList<String>();
		depend.add(HELLO);
		depend.add(TIP);
		queue3.setDependents(depend);
		queue3.setExceptionQuene(EXCEPTION);
		DefaultDisruptorQueue exceptionQueue = new DefaultDisruptorQueue(1024);
		exceptionQueue.setName(EXCEPTION);
		exceptionQueue.setEventListener(new ExceptionEventListener());
		exceptionQueue.setThreads(2);
		queueContainer.putQueue(first);
		queueContainer.putQueue(queue);
		queueContainer.putQueue(queue2);
		queueContainer.putQueue(queue3);
		queueContainer.putQueue(exceptionQueue);
		queueContainer.start();
	}
	
	@Test
	public void testQueue() throws InterruptedException {
//		Thread.sleep(15000);
		for (int i = 0; i < 50000; i++) {
			Flow flow = queueContainer.startFlow();
			String msg = UUID.randomUUID().toString();
			FirstEventData data = new FirstEventData(flow);
			data.setMessage(msg);
//			while (!queueContainer.hasAvailableCapacity(FIRST)) {
//				System.out.println(String.format("%s - [%s] sleep 1 ms", Thread.currentThread().getName(), FIRST));
//				Thread.sleep(1);
//			}
			queueContainer.publish(FIRST, data);
			Thread.sleep(new Random().nextInt(5));
		}
		System.out.println("Finish published");
		try {
			System.in.read();//Wait some time
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() {
		queueContainer.shutdown();
	}
	
}
