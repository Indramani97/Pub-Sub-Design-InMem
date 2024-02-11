package com.indra.pubsub.model;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
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
        log.info("Subscriber {} started consuming message [{}]", id, message.getMessage());
        Thread.sleep(sleepInterval);
        log.info("Subscriber {} done consuming message [{}]", id, message.getMessage());
    }
}
