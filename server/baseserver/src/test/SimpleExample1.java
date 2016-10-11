package test;

/**
 * Created by Administrator on 2016/10/10.
 */

import com.google.gson.Gson;

import java.io.IOException;

public class SimpleExample1 {
    public static void main(String[] args) throws IOException {
//        Reader reader = new InputStreamReader(SimpleExample1.class.getResourceAsStream("Server1.json"), "UTF-8");
//        Gson gson = new GsonBuilder().create();
//        Person p = gson.fromJson(reader, Person.class);
//        System.out.println(p);

        Gson gson = new Gson();
        Person p1 = new Person();
        p1.setName("没事");
        p1.setAge(10);
        Address a= new Address();
        a.setCity("chengdu");
        a.setNoid(64100);
        p1.setAddress(a);

        String str;
        str = gson.toJson(p1);
        System.out.println(str);


        str = "{\"name\":\"没事\",\"age\":10,\"address\":{\"city\":\"chengdu\",\"noid\":64101}}";
        Person person1 = gson.fromJson(str, Person.class);

        System.out.println(person1.getAddress().getNoid());
    }

}
class Address{
    private String city;
    private int noid;
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }

    public  void setNoid(int noid){
        this.noid=noid;
    }
    public int getNoid(){
        return this.noid;
    }
}
class Person {
    private String name;
    private int age;
    private Address address;
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }
    public void setAddress(Address address){
        this.address=address;

    }
    public Address getAddress(){
        return address;
    }

    @Override
    public String toString()
    {
        return name + ":" +age;
    }
}