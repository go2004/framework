package test;


import db.DBConn;
import google.protobuf.MyMessage;
import handler.CMDTestHandler;
/**
 * Created by Administrator on 2016/9/23.
 */
public class test {
    public enum Light {

        // 利用构造函数传参

        RED(1), GREEN(3), YELLOW(2);

        // 定义私有变量

        private int nCode;

        // 构造函数，枚举类型只能为私有

        private Light(int _nCode) {

            this.nCode = _nCode;

        }

        @Override
        public String toString() {

            return String.valueOf(this.nCode);

        }

    }
    private static MyMessage.Req createReq(int i){
        MyMessage.Foo_Req.Builder req1 = MyMessage.Foo_Req.newBuilder();
        req1.setId(i);

        MyMessage.Baz_Req.Builder req2 = MyMessage.Baz_Req.newBuilder();
        req2.setId(i*10);

        MyMessage.Req.Builder req = MyMessage.Req.newBuilder();
        // System.out.println(" Type="+ Req.Type.BAR_VALUE);
        req.addType(MyMessage.Req.Type.BAR);
        req.addFoo(req1);
        //System.out.println("submit:["+req+"]");
        //  req.addBaz(req2);


        return  req.build();
    }


    public static class D1Hanlder extends CMDTestHandler {
        @Override
        public void handleMsg(MyMessage.Req req) {
            System.out.println("D1Hanlder");
            Light[] allLight = Light.values();
            for (Light aLight : allLight) {

                System.out.println("当前灯name：" + aLight.name());

                System.out.println("当前灯ordinal：" + aLight.ordinal());

                System.out.println("当前灯：" + aLight);

            }
        }
    }

    public static class D2Hanlder extends CMDTestHandler {
        @Override
        public void handleMsg(MyMessage.Req req) {
            google.protobuf.MyMessage.Foo_Req message = req.getFoo(0);
            System.out.println("D2Hanlder:->"+message.getId());
            show();

        }
        public void show(){
            System.out.println("show-------------");
        }
    }

    public static void main(String[] args)throws Exception {
/*        Light[] allLight = Light.values();
        for (Light aLight : allLight) {

            System.out.println("当前灯name：" + aLight.name());

            System.out.println("当前灯ordinal：" + aLight.ordinal());

            System.out.println("当前灯：" + aLight);

        }
        */
/*        MyMessage.Req req= createReq(1);
        int Type = req.getType(0).getNumber();

        System.out.println("Type ="+Type);
        google.protobuf.MyMessage.Foo_Req message = req.getFoo(0);

        CMDTestHandler[] Handler= new CMDTestHandler[2];
        Handler[0]=new D1Hanlder();
        Handler[1]=new D2Hanlder();


        MsgEntity msgEntity =new MsgEntity();
        List<MsgEntity> commandList = new ArrayList<>();
        int index = Type;
        if (Handler.length>index && Handler[index] != null)
        {
            CMDTestHandler cmd =  Handler[index];
            cmd.handleMsg(req);
        }



        CmdDictionary.getInstance().load();
        CmdHandler Handle = CmdDictionary.getInstance().getHandler(2);
        Handle.handleMsg(req,null);
*/
        //d.getInstance().show();
/*        Gen<test,MyMessage.Req> intOb = new Gen<test,MyMessage.Req>(new test(),createReq(1));
        intOb.showType();

        System.out.println("value= " + intOb.getOb());
*/
//        TcpClient client = new TcpClient("127.0.0.1", 9090);
//        client.init();
//
//        client.doSend();


        //执行查询返回一个唯一user对象的sql
        db.model.User user = DBConn.getInstance().selectUsers();
        if (user !=null) {
            System.out.println("----------1:"+user.getUser());
        }
        db.model.User user2 = DBConn.getInstance().selectUsers();
        System.out.println("----------2:"+user2.getUser());

        db.model.User user3 = DBConn.getInstance().selectUsers();
        System.out.println("----------3:"+user3.getUser());

        DBConn.getInstance().close();


    }
}
class Gen<T1,T2> {
    private T1 ob1; // 定义泛型成员变量
    private T2 ob2; // 定义泛型成员变量

    public Gen(T1 ob1,T2 ob2) {
        this.ob1 = ob1;
        this.ob2 = ob2;
    }

    public T1 getOb() {
        return ob1;
    }

    public void setOb(T1 ob1) {
        this.ob1 = ob1;
    }

    public void showType() {
        System.out.println("T1的实际类型是: " + ob1.getClass().getName());
        System.out.println("T2的实际类型是: " + ob2.getClass().getName());
    }
}

