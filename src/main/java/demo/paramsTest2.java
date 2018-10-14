package demo;

import bean.TestData2;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

/***
 * 数据驱动测试
 */

@RunWith(Parameterized.class)
public class paramsTest2 {

    static String filePath = System.getProperty("user.dir") + "/src/main/java/data/testdata.csv";

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Parameterized.Parameter
    public TestData2 data;

    @Parameterized.Parameters()
    public static List<TestData2> dataCSV() throws IOException {
      return readCSV(filePath);
    }


    public static List<TestData2> readCSV(String filePath) throws IOException {
        ArrayList<TestData2> data=new ArrayList<TestData2>();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(TestData2.class);
        //mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        File csvFile = new File(filePath); // or from String, URL etc
        //System.out.println(csvFile.toString());
        MappingIterator<TestData2> it = mapper.readerFor(TestData2.class).with(schema).readValues(csvFile);
        while (it.hasNext()) {
            TestData2 row = it.next();
            data.add(row);
        }
        return data;
    }



    @Test
    public void assertions(){
        collector.checkThat(data.getReal(), equalTo(data.getExpect()));
    }

}
