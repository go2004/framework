package net;

import google.protobuf.MyMessage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

public class ReqServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(ReqServerHandler.class.getName());

    String keyChannel = "Channel";
    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        ctx.channel().attr(AttributeKey.valueOf(keyChannel)).set(new NettyChannel(keyChannel, new Date()));
        //String id = ctx.channel().id();
        System.out.println("----- channel id:=:"+ ctx.channel().id() + ","+ctx.channel().id().asShortText()+","+ctx.channel().id().asLongText());
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        MyMessage.Req req = (MyMessage.Req) msg;
        System.out.println("SubReqServerHandler channelRead:"+ req);
//        if ("leeka".equalsIgnoreCase(req.getUserName())) {
//            System.out.println("service accept client subscribe req:[" + req + "]");
//            ctx.writeAndFlush(resp(req.getSubReqID()));

//    }

        Channel ch = ctx.channel();
        ch.write(resp(req.getType(1).getNumber()));
        ch.flush();
        NettyChannel t= (NettyChannel) ctx.channel().attr(AttributeKey.valueOf(keyChannel)).get();
        System.out.println("channelRead :[" + t.getName() + ", "+t.getCreateDate()+"]");

      //  Integer clientID = (Integer)ctx.channel().attr(null).get();

    }

    private MyMessage.Resp resp(int id) {

        MyMessage.Baz_Resp.Builder resp1 =MyMessage.Baz_Resp.newBuilder();
        resp1.setId(id);
        resp1.setDesc("baz desc");

        MyMessage.Resp.Builder builder = MyMessage.Resp.newBuilder();
        builder.addType(MyMessage.Resp.Type.BAR);
        builder.addBaz(resp1);

        return builder.build();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        //cause.printStackTrace();
        logger.warning("unexpected exception from downstream:" + cause.getMessage());
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                System.out.println("READER_IDLE");
                // 超时关闭channel
              //  ctx.close();
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                System.out.println("WRITER_IDLE");
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                System.out.println("ALL_IDLE");
                // 发送心跳
                ctx.channel().write("ping\n");
            }
        }
        super.userEventTriggered(ctx, evt);
    }

}