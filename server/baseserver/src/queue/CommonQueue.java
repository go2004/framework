package queue;


/**
 * Created by Administrator on 2016/9/22.
 */

import message.MsgEntity;

/**
 * 通用消息处理queue
 * */
public class CommonQueue extends BaseQueue<MsgEntity> {
    // private BaseQueue<MsgEntity> baseQueue = new BaseQueue<MsgEntity>();
    private static final CommonQueue INSTANCE = new CommonQueue();

    private CommonQueue() {
    }

    public static CommonQueue getInstance() {
        return INSTANCE;
    }



}