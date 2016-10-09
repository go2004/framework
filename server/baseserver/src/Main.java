import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.NettyChannelPool;
import handler.CmdDictionary;
import net.ReqServer;
import queue.CommonQueue;
import runs.HandleCmdRunnable;


public class Main {
    private final static int port = 9090;

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
        CmdDictionary.getInstance().load();
        NettyChannelPool.getInstance();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new HandleCmdRunnable(CommonQueue.getInstance()));// 澶勭悊閫氱敤鍛戒护鐮?鍏蜂綋鐢ㄦ剰鍙傝€冪2鏉?http://vincepeng.iteye.com/blog/2171581)
        //exec.execute(new HandleCmdRunnable(LoginQueue.getInstance()));// 澶勭悊鐧诲綍鍛戒护鐮?
        // 鍒濆鍖杗etty
        exec.execute(new ReqServer(port));

        exec.shutdown();
        //exec.shutdownNow();

    }
}
