package suittest1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class test1{

    @Rule
    public TestName testName = new TestName();

    @Test
    public void test_shop(){
        System.out.println("测试添加商品1");
        System.out.println(testName.getMethodName());
    }
}
