package handler;

/**
 * Created by Administrator on 2016/9/23.
 */
import google.protobuf.MyMessage;
public abstract class CMDTestHandler {
    public abstract void handleMsg(MyMessage.Req req);
}