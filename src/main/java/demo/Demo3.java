package demo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.Matchers.equalTo;




public class Demo3 {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    /**
     * 断言失败继续测试
     */
    @Test
    public void test_(){
        collector.checkThat(1,equalTo(2));
        collector.checkThat(1,equalTo(1));
        collector.checkThat(1,equalTo(2));
    }


}
