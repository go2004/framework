package common;

/**
 * Created by Administrator on 2016/9/28.
 */

import google.protobuf.MyMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import test.SubReqClientHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Dozer @ 5/24/15
 * mail@dozer.cc
 * http://www.dozer.cc
 */
public class TcpClient {
    private volatile EventLoopGroup workerGroup;
    private volatile Bootstrap bootstrap;
    private volatile ChannelFuture ClientFuture;
    private volatile boolean closed = false;
    private volatile boolean connSuccess = false;
    private final String remoteHost;
    private final int remotePort;

    public TcpClient(String remoteHost, int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    public void close() {
        closed = true;
        workerGroup.shutdownGracefully();
        System.out.println("Stopped Tcp Client: " + getServerInfo());
    }

    public void init() {
        closed = false;

        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addFirst(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        super.channelInactive(ctx);
                        ctx.channel().eventLoop().schedule(() -> doConnect(), 1, TimeUnit.SECONDS);
                    }
                });

                //todo: add more handler
                pipeline.addLast(new ProtobufVarint32FrameDecoder())
                        .addLast(new ProtobufDecoder(
                                MyMessage.Resp.getDefaultInstance()))
                        .addLast(new ProtobufVarint32LengthFieldPrepender())
                        .addLast(new ProtobufEncoder())
                        .addLast(new SubReqClientHandler());

            }
        });

        doConnect();

    }

    private void doConnect() {
        if (closed) {
            return;
        }
        ClientFuture = bootstrap.connect(new InetSocketAddress(remoteHost, remotePort));
        ClientFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture f) throws Exception {
                connSuccess = f.isSuccess();
                if (f.isSuccess()) {
                    System.out.println("Started Tcp Client: " + getServerInfo());

//                    f.channel().flush();

                } else {
                    System.out.println("Started Tcp Client Failed: " + getServerInfo());
                    f.channel().eventLoop().schedule(() -> doConnect(), 1, TimeUnit.SECONDS);
                }
            }
        });
    }


    public void doSend() {

        int i = 0;
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //发送信息
            if (connSuccess) {
                ClientFuture.channel().writeAndFlush(createReq(i));
                i = i + 1;
            }

        }
    }

    private String getServerInfo() {
        return String.format("RemoteHost=%s RemotePort=%d", remotePort, remotePort);
    }

    private static MyMessage.Req createReq(int i) {
        MyMessage.Foo_Req.Builder req1 = MyMessage.Foo_Req.newBuilder();
        req1.setId(i);

        MyMessage.Baz_Req.Builder req2 = MyMessage.Baz_Req.newBuilder();
        req2.setId(i * 10);

        MyMessage.Req.Builder req = MyMessage.Req.newBuilder();
        // System.out.println(" Type="+ Req.Type.BAR_VALUE);
        req.addType(MyMessage.Req.Type.BAR);
        req.addBaz(req2);
        System.out.println("submit:[" + req + "]");
        //  req.addBaz(req2);


        return req.build();
    }
}