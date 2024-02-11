package com.indra.pubsub.handler;

import com.indra.pubsub.model.Message;
import com.indra.pubsub.model.SubscriberIface;
import com.indra.pubsub.model.Topic;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
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
        log.info("Topic {} subscribed by subscriber {}",topic.getTopicName(), subscriber.getId());
    }

    public Topic createTopic(String topicName){
        Topic topic = new Topic(topicName, UUID.randomUUID().toString());
        TopicHandler handler = new TopicHandler(topic);
        this.topicProcessor.put(topic.getTopicId(), handler);
        log.info("Topic created with topic name {} and topic id {}", topicName, topic.getTopicId());
        return topic;
    }

    public void publish(Topic topic, Message message){
        topic.addMessage(message);
        new Thread(() -> {
            log.info("About to publish message [{}] to topic {}", message.getMessage(), topic.getTopicName());
            topicProcessor.get(topic.getTopicId()).publish();}).start();
        log.info("Message [{}] published to topic : {}", message.getMessage(), topic.getTopicName());
    }

    public void resetOffset(Topic topic , SubscriberIface subscriber , int newOffset){
        List<SubscriberIface> subs = topic.getSubscribers();
        if(subs.contains(subscriber)){
            subscriber.getOffset().set(newOffset);
            log.info("{} offset reset to {}", subscriber.getId(), newOffset);
            TopicHandler handler = topicProcessor.get(topic.getTopicId());
            handler.getTopicSubscriberMap().get(subscriber.getId()).notifySubscriber();
        }

    }
}
