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
package com.github.yt.test.jstorm.tick;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import com.github.yt.test.jstorm.grouping.shuffleGrouping.ShuffleGroupingSpouts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author binyang.dby on 2016/7/21.
 * <p>
 * This test is to check if the tick works correctly with the cycle which
 * is set by the user.
 */
public class TickTupleTest {

    private static Logger LOG = LoggerFactory.getLogger(TickTupleTest.class);

    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout1", new TickTupleTestSpout(), 1);

        //时间单位是秒。
        builder.setBolt("blots1", new TickTupleTestBolt(), 5)
                .addConfiguration(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 2);

        Config config = new Config();
        config.put(Config.TOPOLOGY_NAME, "TickTupleTest");

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("hello-topology1", config, builder.createTopology());
        System.out.println("启动成功!");
    }

}
