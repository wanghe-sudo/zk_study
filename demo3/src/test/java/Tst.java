
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.study.WatchCallBack;
import org.study.ZKUtil;

import java.io.IOException;

public class Tst {

    ZooKeeper zk;


    @Before
    public void conn() {
        try {
            zk = ZKUtil.getZK();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void tst() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                String name = Thread.currentThread().getName();
                WatchCallBack callBack = new WatchCallBack(zk, name);

                callBack.tryLock();

                System.out.println("干活！！！");

                callBack.unLock();
            });
        }
    }
}
