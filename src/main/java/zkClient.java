import org.apache.zookeeper.*;


/**
 * Created by Administrator on 2020/4/22.
 * javaAPI 基本操作
 */
public class zkClient {

    public static void main(String[] args) throws Exception {
        /*
        构造java的zookeeper客户端
        参数1：zookeeper集群地址，逗号分隔
        参数2：会话超时时间
        参数3：Watcher监听
         */
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 30000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                //获取通知状态
                System.out.println("状态:" + watchedEvent.getState());
                //获取时间类型
                System.out.println("类型:" + watchedEvent.getType());
                //获取节点路径
                System.out.println("节点路径:" + watchedEvent.getPath());
            }
        });


        /*
            注册监听-如果只是单纯的注册则不需要返回值去接收，当然如果想获取值也可以去接收
            参数1：节点路径
            参数2：相当于对节点设置watcher,数据发生改变就会触发
         */
        byte[] data = zk.getData("/java", true, null);
        System.out.println("节点的数据：" + new String(data));

        /*
            创建节点
            参数1：节点路径
            参数2：节点数据
            参数3：权限
            参数4：节点类型（持久化节点/临时节点等 ）
         */
        zk.create("/java", "java".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        /*
            更新节点，若之前注册了监听，所以将会触发监听的回调函数
            参数1：节点路径
            参数2：节点的数据
            参数3：版本号，-1表示系统自行决定
         */
        zk.setData("/java", "123".getBytes(), -1);
        zk.close();
    }
}
