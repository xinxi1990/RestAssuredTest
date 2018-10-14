package demo;

import bean.TestData1;
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
public class paramsTest3 {

    @Rule
    public ErrorCollector collector = new ErrorCollector();


    @Parameterized.Parameter
    public TestData1 data;

    @Parameterized.Parameters()
    public static List<TestData1> dataList(){
        ArrayList<TestData1> datas=new ArrayList<TestData1>();
        datas.add(new TestData1(1, 2));
        datas.add(new TestData1(2, 2));
        return datas;
    }


    /**
     * 断言失败继续测试
     */
    @Test
    public void test_1(){
        collector.checkThat(data.getReal(), equalTo(data.getExpect()));
    }








}
