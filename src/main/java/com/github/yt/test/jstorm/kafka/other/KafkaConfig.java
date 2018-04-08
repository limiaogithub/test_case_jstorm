package com.github.yt.test.jstorm.kafka.other;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 马驰
 * kafka
 */
public class KafkaConfig implements Serializable {


    /**
     * groupId String
     */
    private String groupId;
    /**
     * conseumeTopic String
     */
    private String conseumeTopic;
    /**
     * produceTopic String
     */
    private String produceTopic;

    private Map<String, Object> consumerProps;

    private Map<String, Object> procederProps;

    public KafkaConfig() {
    }

    /**
     * 初始化
     *
     * @param conf Map
     */
    public void init(Map conf) {
        groupId = String.valueOf(conf.get("group_id"));
        conseumeTopic = String.valueOf(conf.get("conseume_topic"));
        produceTopic = String.valueOf(conf.get("produce_topic"));
        consumerProps = consumerProps(conf);
        procederProps = getProcederProps(conf);
    }

    /**
     * 消费者配置
     *
     * @param conf Map
     * @return Map<String   ,       Object> 配置
     */
    private Map<String, Object> consumerProps(Map conf) {
        Map<String, Object> props = new HashMap<String, Object>(16);
        props.put("bootstrap.servers", conf.get("consumer_bootstrap.servers"));
        props.put("session.timeout.ms", conf.get("session.timeout.ms"));
        props.put("group.id", conf.get("group_id"));
        props.put("auto.offset.reset", conf.get("auto.offset.reset"));
        props.put("max.poll.records", conf.get("max.poll.records"));
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", conf.get("key.deserializer"));
        props.put("value.deserializer", conf.get("value.deserializer"));
        return props;
    }

    /**
     * 生产者配置
     *
     * @param conf Map
     * @return Properties 配置
     */
    private Map<String, Object> getProcederProps(Map conf) {
        Map<String, Object> props = new HashMap<String, Object>(16);
        props.put("bootstrap.servers", conf.get("producer_bootstrap.servers"));
        props.put("key.serializer", conf.get("key.serializer"));
        props.put("value.serializer", conf.get("value.serializer"));
        props.put("acks", conf.get("acks"));
        props.put("batch.size", conf.get("batch.size"));
        props.put("linger.ms", conf.get("linger.ms"));
        props.put("buffer.memory", conf.get("buffer.memory"));
        return props;
    }

    public Map<String, Object> getConsumerProps() {
        return consumerProps;
    }

    public void setConsumerProps(Map<String, Object> consumerProps) {
        this.consumerProps = consumerProps;
    }

    /**
     * 获取 groupId String
     *
     * @return String groupId
     */
    public String getGroupId() {
        return this.groupId;
    }

    /**
     * 设置 groupId String
     *
     * @param groupId String
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取 conseumeTopic String
     *
     * @return String conseumeTopic
     */
    public String getConseumeTopic() {
        return this.conseumeTopic;
    }

    /**
     * 设置 conseumeTopic String
     *
     * @param conseumeTopic String
     */
    public void setConseumeTopic(String conseumeTopic) {
        this.conseumeTopic = conseumeTopic;
    }

    /**
     * 获取 produceTopic String
     *
     * @return String produceTopic
     */
    public String getProduceTopic() {
        return this.produceTopic;
    }

    /**
     * 设置 produceTopic String
     *
     * @param produceTopic StringT
     */
    public void setProduceTopic(String produceTopic) {
        this.produceTopic = produceTopic;
    }

    public Map<String, Object> getProcederProps() {
        return procederProps;
    }

    public void setProcederProps(Map<String, Object> procederProps) {
        this.procederProps = procederProps;
    }
}
