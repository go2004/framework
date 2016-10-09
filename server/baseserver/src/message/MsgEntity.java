package message;

/**
 * Created by Administrator on 2016/9/22.
 */

import google.protobuf.MyMessage;
import io.netty.channel.Channel;
import net.NettyChannel;

/**
 * 后台处理逻辑的核心实体类
 */
public class MsgEntity {
    private MyMessage.Req req;
    private NettyChannel channel;// 当前玩家的channel

    public  MyMessage.Req getReq() {
        return req;
    }

    public void setReq(MyMessage.Req Req) {
        this.req = Req;
    }

    public NettyChannel getChannel() {
        return channel;
    }

    public void setChannel(NettyChannel channel) {
        this.channel = channel;
    }
}