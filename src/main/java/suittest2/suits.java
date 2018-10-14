package suittest2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * runsuit运行
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        test2.class,
        test1.class,
        test3.class
})


public class suits {

}
