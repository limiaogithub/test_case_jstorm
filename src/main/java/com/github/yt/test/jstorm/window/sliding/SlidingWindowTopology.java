package com.github.yt.test.jstorm.window.sliding;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseWindowedBolt;

import java.util.HashMap;
import java.util.Map;

/**
 * @author binyang.dby on 2016/7/21.
 * <p>
 * basically the unit test of SlidingWindowTopology. I change the some of the spout to
 * check if the bolt do the correct processing. I calculate what the average value will
 * be in the spout and count them up. Then I count the actual average in the AvgBolt.
 * If the 2 values are the same, the test is passed.
 * @Test pass at 2016/07/21
 */
public class SlidingWindowTopology {

    public final static int SPOUT_LIMIT = 3000;
    public final static int SUM_BOLT_WINDOW_LENGTH = 30;
    public final static int SUM_BOLT_WINDOW_SLIDE = 10;

    private static Map<String, Object> conf = new HashMap<>();

    static {
        conf.put(Config.TOPOLOGY_NAME, "SlidingWindowTopology");
    }

    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new SlidingWindowSpout(SPOUT_LIMIT), 1);

        //滑动时间窗口，SUM_BOLT_WINDOW_LENGTH为长度30一组，SUM_BOLT_WINDOW_SLIDE为滑动10个
        builder.setBolt("sum", new SlidingWindowBolt().withWindow(
                new BaseWindowedBolt.Count(30), new BaseWindowedBolt.Count(10)), 1).shuffleGrouping("spout");

//        builder.setBolt("sum", new SlidingWindowBolt().withWindow(new BaseWindowedBolt.Duration(3, TimeUnit.SECONDS), new BaseWindowedBolt.Count(1)
//        ), 1).shuffleGrouping("spout");

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("topology1", conf, builder.createTopology());
        System.out.println("启动成功!");
    }


}
