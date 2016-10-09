package runs;

/**
 * Created by Administrator on 2016/9/22.
 */

import java.util.concurrent.TimeUnit;

import google.protobuf.MyMessage;
import io.netty.channel.Channel;
import message.MsgEntity;
import net.NettyChannel;
import queue.BaseQueue;

public abstract class AbstractCmdRunnable implements Runnable {
    private BaseQueue<MsgEntity> INSTANCE;

    public AbstractCmdRunnable(BaseQueue<MsgEntity> INSTANCE) {
        this.INSTANCE = INSTANCE;
    }

    @Override
    public void run() {
        for (;;) {
            try {
                MsgEntity msgEntity = null;
                while (INSTANCE.getQueueSize() > 0) {
                    msgEntity = INSTANCE.take();
                    if (msgEntity != null) {
                        handleMsg(msgEntity.getReq(), msgEntity.getChannel());
                    }
                }
                TimeUnit.MILLISECONDS.sleep(50);// 如果已经取完则让给其他线程一些时间片
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void handleMsg(MyMessage.Req req, NettyChannel channel);

}
