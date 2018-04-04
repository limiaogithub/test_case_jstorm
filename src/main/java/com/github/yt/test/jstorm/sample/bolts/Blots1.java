package com.github.yt.test.jstorm.sample.bolts;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Blots1 implements IRichBolt {

    private OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        String sentence = tuple.getString(0);
        String[] words = sentence.split(" ");
        for (String word : words) {
            word = word.trim();
            System.out.println("word:"+word);
            if (word.isEmpty()) {
                continue;
            }
            word = word.toLowerCase();
            List<Tuple> tupleList = new ArrayList<>();
            tupleList.add(tuple);
            collector.emit(tupleList, new Values(word));
        }
        collector.ack(tuple);
    }

    @Override
    public void cleanup() {
        System.out.println("Blots1 cleanup");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        System.out.println("Blots1 declareOutputFields");
        outputFieldsDeclarer.declare(new Fields("word"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        System.out.println("Blots1 getComponentConfiguration");
        return null;
    }
}
