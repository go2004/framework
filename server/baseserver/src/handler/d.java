package handler;

/**
 * Created by Administrator on 2016/9/26.
 */
public class d {
    private static d ourInstance = new d();

    public static d getInstance() {
        return ourInstance;
    }

    private d() {
        System.out.println("INIT ...");
    }
    public void show()
    {
        System.out.println("abcd");
    }
}
