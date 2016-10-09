package runs;

/**
 * Created by Administrator on 2016/9/22.
 */

import java.util.ArrayList;
import java.util.List;

import google.protobuf.MyMessage;
import handler.CmdDictionary;
import handler.CmdHandler;
import net.NettyChannel;
import queue.BaseQueue;
import message.MsgEntity;

public class HandleCmdRunnable extends AbstractCmdRunnable {

    public HandleCmdRunnable(BaseQueue<MsgEntity> INSTANCE) {
        super(INSTANCE);
    }

   // private static CmdHandler CmdHandler = new DemoHanlder();// 这里命令码较少.暂时放置到同一个handler中.如果较多最好按照cmdCode分别放置存入hashMap中

    /**
     * 处理消息的核心方法
     * */
    @Override
    public void handleMsg(MyMessage.Req req, NettyChannel netChannel) {
        try {

            int Cmd = req.getType(0).getNumber();
            CmdHandler Handle = CmdDictionary.getInstance().getHandler(Cmd);
            if (Handle ==null)
                return;
            Handle.handleMsg(req,netChannel);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}