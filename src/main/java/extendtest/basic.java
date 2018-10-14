package extendtest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class basic {

    @BeforeClass
    public static void basi_class(){
        System.out.println("调用basic的类初始化操作!");
    }

    @BeforeMethod
    public static void  basic_login(){
        System.out.println("调用basic的登录操作!");
    }


}
