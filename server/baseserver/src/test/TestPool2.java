package test;

/**
 * Created by Administrator on 2016/9/27.
 */


public class TestPool2 {

    public class User {
        //姓名
        private String name;
        //年龄
        private int age;
        //身高
        private String height;

        public User(String name, int age, String height) {
            super();
            this.name = name;
            this.age = age;
            this.height = height;
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder(super.toString());
            str.append(" ");
            str.append("name:" + name);
            str.append(",age:" + age);
            str.append(",height:" + height);
            return str.toString();
        }

    }

}
