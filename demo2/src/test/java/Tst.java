import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.study.MyConf;
import org.study.WatchCallBack;
import org.study.ZKUtil;

import java.io.IOException;

public class Tst {
    ZooKeeper zk;
    MyConf mc;


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
    public void getConf() throws InterruptedException {
        mc = MyConf.builder().build();
        WatchCallBack watchCallBack = new WatchCallBack(zk, mc);
        zk.exists("/AppConf", watchCallBack, watchCallBack, "ABC");
        watchCallBack.aWait();
        while (true) {
            if (mc.getConf().isBlank()) {
                System.out.println("节点数据丢了！。。。。");
                // 当节点数据被删除后，继续等待
                // 触发stat的删除事件，删除事件中，设置cd为1
                // 这里await的时候，一直阻塞，直到被重新设置了新值 cd-1，这里将不在阻塞
                watchCallBack.aWait();
            } else {
                Thread.sleep(2000);
                System.out.println(watchCallBack.getMc().getConf());

            }

        }
    }
}
