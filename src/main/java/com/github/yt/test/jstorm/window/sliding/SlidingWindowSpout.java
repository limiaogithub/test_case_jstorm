/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yt.test.jstorm.window.sliding;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.alibaba.jstorm.utils.JStormUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SlidingWindowSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private long msgId = 1;
    private int limit;

    private static List<Integer> numList = new ArrayList<>(3001);

    public SlidingWindowSpout(int limit) {
        this.limit = limit;
        for (int i = 0; i < 3001; i++) {
            numList.add(i);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("value", "ts", "msgid"));
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void nextTuple() {
        if (msgId > limit) {
            JStormUtils.sleepMs(1000);
            return;
        }
        JStormUtils.sleepMs(5);
        int randomInt = numList.get(Integer.parseInt(String.valueOf(msgId)));
        collector.emit(new Values(randomInt, System.currentTimeMillis() - (24 * 60 * 60 * 1000), msgId++), msgId);
    }

}
