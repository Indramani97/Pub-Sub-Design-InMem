package com.indra.pubsub.handler;

import com.indra.pubsub.model.Message;
import com.indra.pubsub.model.SubscriberIface;
import com.indra.pubsub.model.Topic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MessageBroker {
    private static final MessageBroker instance  = new MessageBroker();

    public static MessageBroker getInstance(){
        return instance;
    }
    private MessageBroker(){
    }

    private final Map<String, TopicHandler> topicProcessor = new HashMap<>();

    public void subscribe(Topic topic, SubscriberIface subscriber){
        topic.addSubscriber(subscriber);
        System.out.println("Topic " + topic.getTopicName()+ " subscribed by subscriber " + subscriber.getId());
    }

    public Topic createTopic(String topicName){
        Topic topic = new Topic(topicName, UUID.randomUUID().toString());
        TopicHandler handler = new TopicHandler(topic);
        this.topicProcessor.put(topic.getTopicId(), handler);
        System.out.println("Topic created with topic name " + topicName + " and topic ID " + topic.getTopicId());
        return topic;
    }

    public void publish(Topic topic, Message message){
        topic.addMessage(message);
        new Thread(() -> topicProcessor.get(topic.getTopicId()).publish()).start();
        System.out.println(message.getMessage() + " published to topic: " + topic.getTopicName());
    }

    public void resetOffset(Topic topic , SubscriberIface subscriber , int newOffset){
        List<SubscriberIface> subs = topic.getSubscribers();
        if(subs.contains(subscriber)){
            subscriber.getOffset().set(newOffset);
            System.out.println(subscriber.getId() + " offset reset to: " + newOffset);
            TopicHandler handler = topicProcessor.get(topic.getTopicId());
            handler.getTopicSubscriberMap().get(subscriber.getId()).notifySubscriber();
        }

    }
}
