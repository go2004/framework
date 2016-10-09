package handler;

import google.protobuf.MyMessage;
import io.netty.channel.Channel;
import net.NettyChannel;

/**
 * Created by Administrator on 2016/9/23.
 */
public class FooHanlder extends CmdHandler {
    @Override
    public void handleMsg(MyMessage.Req req, NettyChannel channel) {

        if (req.getFooCount() > 0) {
            doMsg(req.getFoo(0), channel);
        }
    }

    public void doMsg(MyMessage.Foo_Req message, NettyChannel channel) {
        System.out.println("FooHanlder:->" + message.getId());
    }
}