package com.github.yt.test.jstorm.sample.spouts;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import shade.storm.org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class Spouts1 implements IRichSpout {

    private static final long serialVersionUID = 468766578890885L;

    private SpoutOutputCollector collector;

    private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        queue.add("hello jstorm");
    }

    @Override
    public void close() {
        System.out.println("Spouts1 close");
    }

    @Override
    public void activate() {
        System.out.println("Spouts1 activate");
    }

    @Override
    public void deactivate() {
        System.out.println("Spouts1 deactivate");
    }

    @Override
    public void nextTuple() {
        String word = queue.poll();
        if (StringUtils.isNotEmpty(word)) {
            this.collector.emit(new Values(word), word);
        }
    }

    @Override
    public void ack(Object o) {
        System.out.println("Spouts1 ack");
    }

    @Override
    public void fail(Object o) {
        System.out.println("Spouts1 fail");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("line"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        System.out.println("Spouts1 getComponentConfiguration");
        return null;
    }
}
