package com.github.yt.test.jstorm.sample;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.Map;

/**
 * @author limiao
 */
public class Spouts1 implements IRichSpout {

    private static final long serialVersionUID = 468766578890885L;

    private SpoutOutputCollector collector;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
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
        for (int i = 0; i < 1000; i++) {
            this.collector.emit(new Values(String.valueOf(i)), String.valueOf(i));
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
