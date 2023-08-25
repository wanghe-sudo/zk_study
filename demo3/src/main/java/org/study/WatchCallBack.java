package org.study;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class WatchCallBack implements Watcher {
    private ZooKeeper zk;

    private String threadName;

    CountDownLatch cd = new CountDownLatch(1);

    public WatchCallBack(ZooKeeper zk, String threadName) {
        this.zk = zk;
        this.threadName = threadName;
    }

    @Override
    public void process(WatchedEvent event) {

    }


    public void tryLock() {
        try {
            cd.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void unLock() {

    }

}
