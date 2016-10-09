package net;

/**
 * Created by Administrator on 2016/8/30.
 */
import client.ClientInfo;
import io.netty.channel.Channel;

import java.util.Date;

public class NettyChannel {
    private Channel channel;                //名字
    private Date createDate;            //生成日期
    private ClientInfo clientInfo;

    public NettyChannel(Channel channel, Date createDate) {
        this.channel = channel;
        this.createDate = createDate;
    }

    public Channel getchannel() {
        return channel;
    }


    public void setChannel(Channel channel) {
        this.channel = channel;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setClientInfo(ClientInfo Info){this.clientInfo = Info;}

    public ClientInfo getClientInfo(){return this.clientInfo;}
}
