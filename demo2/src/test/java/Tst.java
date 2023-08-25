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
            Thread.sleep(2000);
            System.out.println("判断两个引用是否是一个：" + (watchCallBack.getMc() == mc));
            System.out.println(watchCallBack.getMc().getConf());
        }
    }
}
