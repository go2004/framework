package handler;

/**
 * Created by Administrator on 2016/9/22.
 */

import java.util.List;

import google.protobuf.MyMessage;
import io.netty.channel.Channel;
import net.NettyChannel;


public abstract class CmdHandler {
    public abstract void handleMsg(MyMessage.Req req, NettyChannel channel);
}
