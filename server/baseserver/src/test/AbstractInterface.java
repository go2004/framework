package test;

/**
 * Created by Administrator on 2016/9/29.
 */
public class AbstractInterface {
    public static void main(String[] args) throws Exception {
        AlarmDoor D1= new AlarmDoor();
        D1.open();
        D1.close();
        D1.alarm();
    }
}

interface Alarm{
    void alarm();
    void alarm1();

    String AlarmName ="炒芭在工";
}
abstract class Door{
    Door(){

    }
    void open(){
        System.out.println("1:open");
    }

    void close() {
        System.out.println("2:close");
    }
}

class AlarmDoor extends Door implements Alarm{
    @Override
    void open() {
        super.open();
        System.out.println("1:open->1");
    }
    @Override
    void close() {
        super.close();
        System.out.println("2:close->2");
    }

    public void alarm(){
        System.out.println("3:alarm");
        //System.out.println("3:"+AlarmName);

    }
    public void alarm1(){
        System.out.println("4:"+AlarmName);
    }
}