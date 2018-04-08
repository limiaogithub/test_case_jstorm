package com.github.yt.test.jstorm.grouping.all;

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
public class AllGroupingSpouts implements IRichSpout {

    private static final long serialVersionUID = 468766578890885L;

    private SpoutOutputCollector collector;

    private String[] strings = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private boolean flag = true;

    @Override
    public void nextTuple() {
        if (!flag) {
            return;
        }
        for (String str : strings) {
            this.collector.emit(new Values(str), str);
        }
        flag = false;
    }

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
