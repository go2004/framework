package handler;

import google.protobuf.MyMessage;
import io.netty.channel.Channel;
import net.NettyChannel;

/**
 * Created by Administrator on 2016/9/23.
 */
public class BarHanlder extends CmdHandler {


        @Override
        public void handleMsg(MyMessage.Req req, NettyChannel channel) {
            if (req.getBazCount()>0) {
                google.protobuf.MyMessage.Baz_Req message = req.getBaz(0);

                doMsg(message, channel);
            }
        }
        public void doMsg(MyMessage.Baz_Req message,NettyChannel channel ){
            System.out.println("BarHanlder:->"+message.getId());
        }

}
