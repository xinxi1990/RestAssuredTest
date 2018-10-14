package extendtest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class test extends subbasic {

    @BeforeClass
    public static void test_class(){
        System.out.println("调用test的类初始化操作!");
    }

    @BeforeClass
    public static void testc_method(){
        System.out.println("调用test的方法初始化操作!");
    }



    @Test
    public void test_shop(){
        System.out.println("测试添加商品");
    }
}
