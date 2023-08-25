package org.study;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultWatch implements Watcher {
    private CountDownLatch countDownLatch;

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event.toString());

        switch (event.getState()) {
            case Unknown -> {
            }
            case Disconnected -> {
                System.out.println("怎么断开了？？？");
            }
            case NoSyncConnected -> {
            }
            case SyncConnected -> {
                System.out.println("已经连接到zk！");
                countDownLatch.countDown();
            }
            case AuthFailed -> {
            }
            case ConnectedReadOnly -> {
            }
            case SaslAuthenticated -> {
            }
            case Expired -> {
                System.out.println("怎么超时了？？？");
            }
            case Closed -> {
                System.out.println("主动关闭了连接！！");
            }
        }

    }
}
