package com.indra.pubsub.model;

import java.util.concurrent.atomic.AtomicInteger;

public interface SubscriberIface {
    String getId();

    AtomicInteger getOffset();
    void consume(Message message) throws InterruptedException;

}
