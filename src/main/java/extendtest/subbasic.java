package extendtest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class subbasic extends basic {

    @BeforeClass
    public static void subbasic_class(){
        System.out.println("调用subbasic的类初始化操作!");
    }

    @BeforeMethod
    public static void  add_shop(){
        System.out.println("调用subbasic的添加商品操作!");
    }
}
