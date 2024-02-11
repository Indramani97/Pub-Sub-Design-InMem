package com.indra.pubsub.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Topic {

    private final String topicId;
    private final String topicName;
    private final List<Message> messages;

    private final List<SubscriberIface> subscribers;

    public Topic(String topicName, String topicId){
        this.topicId = topicId;
        this.topicName = topicName;
        this.messages = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }
    public String getTopicName() {
        return topicName;
    }

    public String getTopicId(){
        return this.topicId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message){
        this.messages.add(message);
    }

    public void addSubscriber(SubscriberIface subscriber){
        this.subscribers.add(subscriber);
    }

    public List<SubscriberIface> getSubscribers(){
        return this.subscribers;
    }
}
