package testyaml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

/***
 * 从YAML文件里读取3个数据，两个数字，一个字符串。
 * 构建一个数据驱动的测试用例，根据两个数字的相加，判断是否等于字符串。
 */

@RunWith(Parameterized.class)
public class yamlTest {


    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Parameterized.Parameter
    public yamlData data;


    @Parameterized.Parameters()
    public static List<yamlData> dataYML() throws IOException {
        String yaml_Path = System.getProperty("user.dir") + "/src/main/java/data/testdata.yaml";
        System.out.println(yaml_Path);
        return readFromYAML(yaml_Path);
    }


    public static List<yamlData> readFromYAML(String filePath) throws IOException {
        System.out.println(filePath);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        List<yamlData> data = mapper.readValue(
                new File(filePath),
                new TypeReference<List<yamlData>>(){}
        );
        System.out.println(data);
        return data;
    }

    @Test
    public void test_yaml() throws IOException {
        collector.checkThat(String.valueOf(
                data.getNumber1() +
                        data.getNumber2()),
                equalTo(data.getTotal()));

    }

}
