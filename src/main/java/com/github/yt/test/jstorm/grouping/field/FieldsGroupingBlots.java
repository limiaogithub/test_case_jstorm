package com.github.yt.test.jstorm.grouping.field;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

import java.util.Map;

/**
 * @author limiao
 */
public class FieldsGroupingBlots implements IRichBolt {

    //private OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        //this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        String sentence = tuple.getString(0);
        System.out.println("-------------------- Thread:[" + Thread.currentThread() + "],str:[" + sentence + "]");
        //collector.ack(tuple);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
