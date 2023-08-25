package org.study;

import lombok.Builder;
import lombok.Data;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

@Data
@Builder
public class WatchCallBack implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {

    ZooKeeper zk;
    MyConf mc;
    static CountDownLatch cd = new CountDownLatch(1);

    public WatchCallBack(ZooKeeper zk, MyConf mc) {
        this.zk = zk;
        this.mc = mc;
    }

    @Override
    public void process(WatchedEvent event) {
        // 关心发生的事件：
        switch (event.getType()) {
            case None -> {
            }
            // 节点被创建
            case NodeCreated -> {
                // TODO 节点被创建（说明之前没有创建）
                // 把数据同步过来
                System.out.println("节点被创建，重新拉取数据");
                zk.getData("/AppConf", this, this, "ABC");
            }
            // 节点被删除怎么办？
            case NodeDeleted -> {
                // TODO 容忍性
                System.out.println("节点被删除，清空数据 TODO");
            }
            // 节点数据变更怎么办？
            case NodeDataChanged -> {
                System.out.println("节点被修改，重新获取数据");
                zk.getData("/AppConf", this, this, "ABC");
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

    /**
     * 节点存在，且取数据
     *
     * @param rc   The return code or the result of the call.
     * @param path The path that we passed to asynchronous calls.
     * @param ctx  Whatever context object that we passed to asynchronous calls.
     * @param data The data of the node.很重要！
     * @param stat {@link Stat} object of the node on given path.
     */
    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        if (null != data) {
            String s = new String(data);
            System.out.println("获取到data，并设置到我的配置中： " + s);
            mc.setConf(s);
            cd.countDown();
        }
    }

    /**
     * @param rc   The return code or the result of the call.
     * @param path The path that we passed to asynchronous calls.
     * @param ctx  Whatever context object that we passed to asynchronous calls.
     * @param stat {@link Stat} object of the node on given path.
     */
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        if (stat != null) {
            zk.getData("/AppConf", this, this, ctx);

        }
    }


    public void aWait() throws InterruptedException {
        zk.exists("/AppConf", this, this, "");
        cd.await();
    }
}
