package demo;

import bean.TestData2;
import bean.TestDataYaml;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.runners.Parameterized;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/***
 * 从yaml中读取
 */


public class yamlTest {

    @Parameterized.Parameter
    public TestData2 data;
    static String yaml_Path = System.getProperty("user.dir") + "/src/main/java/data/testdata.yaml";


    public static List<TestDataYaml> readFromYAML(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        List<TestDataYaml> data = mapper.readValue(
                new File(filePath),
                new TypeReference<List<TestDataYaml>>(){}
        );

        return data;
    }

//    public static List<TestDataYaml> readFromJSON(String filePath) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        List<TestDataYaml> data = mapper.readValue(
//                new File(filePath),
//                new TypeReference<List<TestDataYaml>>(){}
//        );
//
//        return data;
//    }

    @Test
    public void test() throws IOException {
        readFromYAML(yaml_Path);

    }

}
