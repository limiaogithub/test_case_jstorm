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


import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseWindowedBolt;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author binyang.dby on 2016/7/21.
 * <p>
 * basically the unit test of SlidingWindowTopology. I change the some of the spout to
 * check if the bolt do the correct processing. I calculate what the average value will
 * be in the spout and count them up. Then I count the actual average in the AvgBolt.
 * If the 2 values are the same, the test is passed.
 * @Test pass at 2016/07/21
 */
public class SlidingWindowTopologyTest {

    public final static int SPOUT_LIMIT = 3000;
    public final static int SUM_BOLT_WINDOW_LENGTH = 30;
    public final static int SUM_BOLT_WINDOW_SLIDE = 10;
    public final static int AVG_BOLT_WINDOW_LENGTH = 3;

    private static Map<String, Object> conf = new HashMap<>();

    static {
        //conf.put(Config.TOPOLOGY_DEBUG, true);
        conf.put(Config.TOPOLOGY_NAME, "SlidingWindowTopologyTest");
        //conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
    }

    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new SlidingWindowTestRandomSpout(SPOUT_LIMIT), 1);
        //the following bolt sums all the elements in the window. The window has length of 30 elements
        //and slide every 10 elements.
        //for example, if the spout generate 1, 2, 3, 4 ... then the SumBolt generate 55, 210, 465, 765 ...

        //滑动时间窗口，SUM_BOLT_WINDOW_LENGTH为长度30一组，SUM_BOLT_WINDOW_SLIDE为滑动10个
//        builder.setBolt("sum", new SlidingWindowTestSumBolt().withWindow(new BaseWindowedBolt.Count(SUM_BOLT_WINDOW_LENGTH),
//                new BaseWindowedBolt.Count(SUM_BOLT_WINDOW_SLIDE)), 1).shuffle("spout");

        builder.setBolt("sum", new SlidingWindowTestSumBolt().withWindow(new BaseWindowedBolt.Duration(3, TimeUnit.SECONDS), new BaseWindowedBolt.Count(1)
        ), 1).shuffleGrouping("spout");

        //the following bolt calculate the average value of elements in the window. The window has length
        //of 3. So it generates the average of 3 elements and then wait for another 3 elements.
//        builder.setBolt("avg", new SlidingWindowTestAvgBolt().withTumblingWindow(new BaseWindowedBolt.Count(AVG_BOLT_WINDOW_LENGTH)), 1)
//                .shuffle("sum");

//        Set<String> userDefineMetrics = new HashSet<String>();
//        userDefineMetrics.add("SlidingWindowTopologyTest.SpoutAvgSum");
//        userDefineMetrics.add("SlidingWindowTopologyTest.BoltAvgSum");

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("hello-topology1", conf, builder.createTopology());
        System.out.println("启动成功!");
    }
}
