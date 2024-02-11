package com.indra.pubsub.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Subscriber implements SubscriberIface{

    private final String id;

    private final AtomicInteger offset;

    private final long sleepInterval;

    public Subscriber(String id, long sleepInterval){
        this.id = id;
        this.sleepInterval = sleepInterval;
        this.offset = new AtomicInteger(0);
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public AtomicInteger getOffset() {
        return offset;
    }

    @Override
    public void consume(Message message) throws InterruptedException {
        System.out.println("Subscriber: " + id + " started consuming: " + message.getMessage());
        Thread.sleep(sleepInterval);
        System.out.println("Subscriber: " + id + " done consuming: " + message.getMessage());
    }
}
