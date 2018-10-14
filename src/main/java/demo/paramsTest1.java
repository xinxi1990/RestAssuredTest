package demo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

/***
 * 数据驱动测试
 */

@RunWith(Parameterized.class)
public class paramsTest1 {

    static String filePath = System.getProperty("user.dir") +  "/src/main/java/data/testdata.csv";

    @Rule
    public ErrorCollector collector = new ErrorCollector();


    @Parameterized.Parameters
    public static Object[][] data(){
        return  new Object[][] {
                { 0, 0},
                { 1, 1},
                { 3, 2},
                { 4, 3}
        };
    }

    @Parameterized.Parameter
    public int exp;
    @Parameterized.Parameter(1)
    public int real;

//    public static List<TestData1> dataList(){
//        ArrayList<TestData1> datas=new ArrayList<TestData1>();
//        datas.add(new TestData1(1, 2));
//        datas.add(new TestData1(2, 2));
//        return datas;
//    }


    /**
     * 断言失败继续测试
     */
    @Test
    public void test_1(){
        collector.checkThat(exp,equalTo(real));
    }








}
