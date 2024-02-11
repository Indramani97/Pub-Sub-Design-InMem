package com.indra.pubsub.handler;

import com.indra.pubsub.model.Message;
import com.indra.pubsub.model.SubscriberIface;
import com.indra.pubsub.model.Topic;

import java.util.concurrent.atomic.AtomicInteger;

public class TopicSubscriber implements Runnable {
    private final Topic topic;
    private final SubscriberIface subscriber;

    public TopicSubscriber(Topic topic, SubscriberIface subscriber){
        this.topic = topic;
        this.subscriber = subscriber;
    }


    @Override
    public void run() {
        synchronized (subscriber){
            do{
                AtomicInteger currentOffset = subscriber.getOffset();
                while(topic.getMessages().size()<=currentOffset.get()){
                    try {
                        subscriber.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                Message message = topic.getMessages().get(currentOffset.get());
                subscriber.getOffset().set(currentOffset.get()+1);
                try {
                    subscriber.consume(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while(true);
        }
    }

    public void notifySubscriber(){
        synchronized (subscriber){
            subscriber.notify();
        }
    }
}
