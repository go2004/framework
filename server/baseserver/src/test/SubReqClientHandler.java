//package com.serial.java;
//
///**
// * Created by Administrator on 2016/8/4.
// */
//public class SubReqClientHandler {
//}
package test;

import google.protobuf.MyMessage;
import google.protobuf.MyMessage.Req;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

//import io.netty.channel.ChannelHandlerAdapter;

public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(SubReqClientHandler.class.getName());

    public SubReqClientHandler() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        for (int i = 1; i < 10; i++) {
//            ctx.write(createReq(i));
//            TimeUnit.MILLISECONDS.sleep(10);// 如果已经取完则让给其他线程一些时间片
//            ctx.flush();
//        }
//        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("receive server response:["+msg+"]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        logger.warning("unexpected exception from downstream:"+ cause.getMessage());
        ctx.close();
    }

    private static MyMessage.Req createReq(int i){
        MyMessage.Foo_Req.Builder req1 = MyMessage.Foo_Req.newBuilder();
        req1.setId(i);

        MyMessage.Baz_Req.Builder req2 = MyMessage.Baz_Req.newBuilder();
        req2.setId(i*10);

        MyMessage.Req.Builder req = MyMessage.Req.newBuilder();
       // System.out.println(" Type="+ Req.Type.BAR_VALUE);
        req.addType(MyMessage.Req.Type.BAR);
        req.addBaz(req2);
        System.out.println("submit:["+req+"]");
      //  req.addBaz(req2);


        return  req.build();
    }

}