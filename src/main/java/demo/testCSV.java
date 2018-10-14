package demo;

import bean.TestDataCSV;
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
 * 读取一个三行两列的csv数据，这个数据里是字符串，断言两个字符串是否相等
 */

@RunWith(Parameterized.class)
public class testCSV {
    static String filePath = System.getProperty("user.dir") + "/src/main/java/data/testdata1.csv";

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Parameterized.Parameter
    public TestDataCSV data;

    @Parameterized.Parameters()
    public static List<TestDataCSV> dataCSV() throws IOException {
        return readCSV(filePath);
    }


    public static List<TestDataCSV> readCSV(String filePath) throws IOException {
        ArrayList<TestDataCSV> data=new ArrayList<TestDataCSV>();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(TestDataCSV.class);
        File csvFile = new File(filePath);
        MappingIterator<TestDataCSV> it = mapper.readerFor(TestDataCSV.class).with(schema).readValues(csvFile);
        while (it.hasNext()) {
            TestDataCSV row = it.next();
            data.add(row);
        }
        return data;
    }

    @Test
    public void assertions(){
        collector.checkThat(data.getReal(), equalTo(data.getExpect()));
    }

}

