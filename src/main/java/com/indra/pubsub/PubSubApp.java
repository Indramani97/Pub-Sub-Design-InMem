package com.indra.pubsub;

import com.indra.pubsub.handler.MessageBroker;
import com.indra.pubsub.model.Message;
import com.indra.pubsub.model.Subscriber;
import com.indra.pubsub.model.SubscriberIface;
import com.indra.pubsub.model.Topic;

public class PubSubApp {
    public static void main(String[] args) throws InterruptedException {
        MessageBroker broker = MessageBroker.getInstance();
        Topic topic = broker.createTopic("Topic 1");
        SubscriberIface sub1 = new Subscriber("Sub1", 5000);
        SubscriberIface sub2 = new Subscriber("Sub2", 5000);
        broker.subscribe(topic, sub1);
        broker.subscribe(topic, sub2);
        broker.publish(topic, new Message("message1"));
        broker.publish(topic,new Message("Message 2"));
        Thread.sleep(30000);
        broker.publish(topic,new Message("Message 3"));
        Thread.sleep(30000);
        broker.resetOffset(topic, sub2,0);
    }
}
