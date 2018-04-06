package com.github.yt.test.jstorm.grouping.shuffleGrouping;

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
public class ShuffleGroupingSpouts implements IRichSpout {

    private static final long serialVersionUID = 468766578890885L;

    private SpoutOutputCollector collector;

    private static final String[] strings = new String[]{"11","22","33"};

    boolean flag = true;

    @Override
    public void nextTuple() {
        if(!flag){
            return;
        }
        for(String str:strings){
            this.collector.emit(new Values(str), str);
        }
        flag=false;
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
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
    public void ack(Object o) {
        System.out.println("Spouts1 ack~~~~~~~~~~~~");
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
