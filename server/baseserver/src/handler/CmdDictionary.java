package handler;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016/9/23.
 */
public class CmdDictionary {
    private static final Logger log = Logger.getLogger(CmdDictionary.class);
    private static final CmdHandler[] HandlerList = new CmdHandler[128];


    public static CmdDictionary getInstance() {

        return CmdDictionary.Singleton.INSTANCE.getMessageDictionary();
    }

    private CmdDictionary() {

    }

    private static enum Singleton {
        INSTANCE;

        CmdDictionary processor = new CmdDictionary();

        private Singleton() {

        }

        CmdDictionary getMessageDictionary() {
            return this.processor;
        }
    }


    public void load() throws Exception {
        HandlerList[0] = new FooHanlder();
        HandlerList[2] = new BarHanlder();
    }

    public CmdHandler getHandler(int cmd) {

        if (HandlerList.length > cmd && HandlerList[cmd] != null) {
            return HandlerList[cmd];
        } else {
            return null;
        }
    }
}
