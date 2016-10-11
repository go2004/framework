package net;

import common.NettyChannelPool;
import google.protobuf.MyMessage;
import handler.CmdDictionary;
import handler.CmdHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.logging.Logger;

public class ReqServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(ReqServerHandler.class.getName());

    String KeyChannel = "KeyChannel";

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        NettyChannel data = NettyChannelPool.getInstance().getObject();
        data.setChannel(ctx.channel());
        data.setCreateDate(new Date());
        ctx.channel().attr(AttributeKey.valueOf(KeyChannel)).set(data);
        System.out.println("getNumActive="+ NettyChannelPool.getInstance().getNumActive()+";getNumIdle="+NettyChannelPool.getInstance().getNumIdle());

        //String id = ctx.channel().id();
        System.out.println("----- channel id:=:" + ctx.channel().id() + "," + ctx.channel().id().asShortText() + "," + ctx.channel().id().asLongText());
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("==============channel-inactive==============");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        MyMessage.Req req = (MyMessage.Req) msg;
        if (req.getTypeCount() < 1)
            return;

        //直接调用处理
        int Cmd = req.getType(0).getNumber();
        CmdHandler Handle = CmdDictionary.getInstance().getHandler(Cmd);
        if (Handle ==null)
            return;
        NettyChannel Channel= (NettyChannel) ctx.channel().attr(AttributeKey.valueOf(KeyChannel)).get();
        Handle.handleMsg(req,Channel);

        /*
        //进入队列，通过队列进入处理
        message.MsgEntity MsgEntity = new message.MsgEntity();
        NettyChannel Channel = (NettyChannel) ctx.channel().attr(AttributeKey.valueOf(KeyChannel)).get();
        MsgEntity.setReq(req);
        MsgEntity.setChannel(Channel);
        CommonQueue.getInstance().put(MsgEntity);
*/


    }

    private MyMessage.Resp resp(int id) {

        MyMessage.Baz_Resp.Builder resp1 = MyMessage.Baz_Resp.newBuilder();
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
        NettyChannel Object= (NettyChannel) ctx.channel().attr(AttributeKey.valueOf(KeyChannel)).get();
        System.out.println(Object.getCreateDate());

        NettyChannelPool.getInstance().freeObject(Object);
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
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("==============channel-read-complete==============");
        ctx.flush();
    }

    @Override
    public boolean isSharable() {
        System.out.println("==============handler-sharable==============");
        return super.isSharable();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("==============channel-register==============");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("==============channel-unregister==============");
    }

}