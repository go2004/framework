package queue;

/**
 * Created by Administrator on 2016/9/22.
 */
public class LoginQueue extends  BaseQueue{
    private static final LoginQueue INSTANCE = new LoginQueue();

    private LoginQueue() {
    }

    public static LoginQueue getInstance() {
        return INSTANCE;
    }
}
