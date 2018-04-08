package com.github.yt.test.jstorm.kafka;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.github.yt.test.jstorm.kafka.other.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author limiao
 */
public class KafkaSpouts extends BaseRichSpout {

    private static final long serialVersionUID = 468766578890885L;

    private SpoutOutputCollector collector;

    private transient LinkedBlockingDeque<String> sendingQueue;

    /**
     * spring kafka 容器
     */
    private transient KafkaMessageListenerContainer<String, String> container;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        sendingQueue = new LinkedBlockingDeque<>(20);
        KafkaConfig kafkaConfig = new KafkaConfig();
        kafkaConfig.init(map);
        Map<String, Object> props = kafkaConfig.getConsumerProps();
        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<String, String>(props);
        ContainerProperties containerProps = new ContainerProperties(kafkaConfig.getConseumeTopic());
        containerProps.setMessageListener(new MessageListener<String, String>() {
            @Override
            public void onMessage(ConsumerRecord<String, String> message) {
                try {
                    System.out.println("################## Thread:[" + Thread.currentThread() + "],message:[" + message.value() + "]" + System.currentTimeMillis());
                    sendingQueue.put(message.value());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        containerProps.setAckMode(AbstractMessageListenerContainer.AckMode.TIME);
        containerProps.setAckTime(Long.parseLong(String.valueOf(map.get("acktime"))));
        container = new KafkaMessageListenerContainer<String, String>(cf, containerProps);
        container.start();

    }

    @Override
    public void close() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void nextTuple() {
        try {
            String msg = sendingQueue.take();
            if (msg != null) {
                System.out.println("-------------------- Thread:[" + Thread.currentThread() + "],msg:[" + msg + "]" + System.currentTimeMillis());
                this.collector.emit(new Values(msg), msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ack(Object o) {

    }

    @Override
    public void fail(Object o) {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("line"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
