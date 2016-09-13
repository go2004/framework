package test;

import com.google.protobuf.InvalidProtocolBufferException;
import google.protobuf.MyMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/6.
 */

public class test_protobuff {


    private static byte [] encode(MyMessage.Req req){
        return req.toByteArray();
    }

    private static MyMessage.Req decode(byte [] body)
            throws InvalidProtocolBufferException {
        return MyMessage.Req.parseFrom(body);
    }



    private static MyMessage.Req createReq(){
        MyMessage.Foo_Req.Builder req1 = MyMessage.Foo_Req.newBuilder();
        req1.setId(1);

        MyMessage.Baz_Req.Builder req2 = MyMessage.Baz_Req.newBuilder();
        req2.setId(2);

        MyMessage.Req.Builder req = MyMessage.Req.newBuilder();
        req.addType(MyMessage.Req.Type.BAR);
        req.addFoo(req1);
        req.addBaz(req2);


        return  req.build();
    }

    public static void main(String[] args)throws Exception {
        MyMessage.Req req = createReq();
        System.out.println("before encode:"+ req.toString());
        MyMessage.Req req2 = decode(encode(req));
//        System.out.println("after encode:"+ req2.toString());
//        System.out.println("Assert equal: " + req2.equals(req));
        System.out.println("ID: " + req2.getType(0).getDescriptorForType()+";"+req2.getBaz(0).getId());

    }
}
