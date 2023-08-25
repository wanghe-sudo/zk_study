package org.study;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        // zk有session的概念，没有连接池的概念
//        watch有两类
// new的时候，是session级别
        // wacth只在读中

        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("192.168.64.81:2181,192.168.64.82:2181,192.168.64.83:2181",
                3000,
                new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        Event.KeeperState state = watchedEvent.getState();
                        Event.EventType type = watchedEvent.getType();
                        String path = watchedEvent.getPath();

                        switch (state) {
                            case Unknown -> {
                            }
                            case Disconnected -> {
                            }
                            case NoSyncConnected -> {
                            }
                            case SyncConnected -> {
                                System.out.println("connected");
                                countDownLatch.countDown();
                            }
                            case AuthFailed -> {
                            }
                            case ConnectedReadOnly -> {
                            }
                            case SaslAuthenticated -> {
                            }
                            case Expired -> {
                            }
                            case Closed -> {
                            }
                        }

                        switch (type) {
                            case None -> {
                            }
                            case NodeCreated -> {
                            }
                            case NodeDeleted -> {
                            }
                            case NodeDataChanged -> {
                            }
                            case NodeChildrenChanged -> {
                            }
                            case DataWatchRemoved -> {
                            }
                            case ChildWatchRemoved -> {
                            }
                            case PersistentWatchRemoved -> {
                            }
                        }

                    }
                });
        // 阻塞
        countDownLatch.await();
        ZooKeeper.States state = zooKeeper.getState();
        switch (state) {
            case CONNECTING -> {
                System.out.println("ing..........");
            }
            case ASSOCIATING -> {
            }
            case CONNECTED -> {
                System.out.println("connected............");
            }
            case CONNECTEDREADONLY -> {
            }
            case CLOSED -> {
            }
            case AUTH_FAILED -> {
            }
            case NOT_CONNECTED -> {
            }
        }
        // 创建节点
        String path = zooKeeper.create("/ooxx1", "olddata".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        System.out.println("-------------------------async start-------------------------");
        zooKeeper.getData(path,
                new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        System.out.println("-------------------------watch-------------------------");
                    }
                },
                new AsyncCallback.DataCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                        System.out.println("-------------------------callBack-------------------------");
                        System.out.println("-------------------------" + stat.toString() + "-------------------------");
                    }
                },
                "abc");
        System.out.println("-------------------------async over-------------------------");
    }
}