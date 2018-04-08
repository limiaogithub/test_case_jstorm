package com.github.yt.test.jstorm.grouping.field;

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
public class FieldsGroupingSpouts implements IRichSpout {

    private static final long serialVersionUID = 468766578890885L;

    private SpoutOutputCollector collector;

    /**
     * 模拟一组存在重复数据的数组
     */
    private String[] strings = new String[]{"11", "22", "33", "11", "22", "33", "11", "22", "33", "11", "22", "33"};

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
