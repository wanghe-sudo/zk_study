package org.study;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZKUtil {

    private static ZooKeeper zk;
    private static final String URL = "192.168.64.81:2181,192.168.64.82:2181,192.168.64.83:2181/testConf";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static ZooKeeper getZK() throws IOException, InterruptedException {
        if (null == zk) {
            zk = new ZooKeeper(URL, 1000, new DefaultWatch(countDownLatch));
            countDownLatch.await();
        }
        return zk;
    }
}
