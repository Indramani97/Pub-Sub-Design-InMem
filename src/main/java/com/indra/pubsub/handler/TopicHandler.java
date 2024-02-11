package com.indra.pubsub.handler;

import com.indra.pubsub.model.SubscriberIface;
import com.indra.pubsub.model.Topic;

import java.util.HashMap;
import java.util.Map;

public class TopicHandler {
    private final Topic topic;
    private Map<String, TopicSubscriber> topicSubscriberMap;

    public TopicHandler(Topic topic){
        this.topic = topic;
        this.topicSubscriberMap = new HashMap<>();
    }

    public Map<String, TopicSubscriber> getTopicSubscriberMap(){
        return this.topicSubscriberMap;
    }

    public void publish(){
        for(SubscriberIface subs : topic.getSubscribers()){
            TopicSubscriber tSub = topicSubscriberMap.get(subs.getId());
            if(tSub != null){
                tSub.notifySubscriber();
            } else {
                TopicSubscriber topicSubscriber = new TopicSubscriber(topic, subs);
                topicSubscriberMap.put(subs.getId(), topicSubscriber);
                new Thread(topicSubscriber).start();
            }
        }
    }
}
