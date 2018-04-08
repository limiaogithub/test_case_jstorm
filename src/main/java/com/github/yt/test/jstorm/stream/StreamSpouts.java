package com.github.yt.test.jstorm.stream;

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
public class StreamSpouts implements IRichSpout {

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
            collector.emit("stream1", new Values(String.valueOf(i)));
        }
        for (int i = 0; i < 1000; i++) {
            collector.emit("stream2", new Values(String.valueOf(i + 90000)));
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
        outputFieldsDeclarer.declareStream("stream1", new Fields("A"));
        outputFieldsDeclarer.declareStream("stream2", new Fields("B"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
