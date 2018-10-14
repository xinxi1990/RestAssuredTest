import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import net.sf.json.JSONObject;
import requests.GetRequest;

import static logger.Logger.log_info;
import static untils.fileUntils.yamlToJson;

public class run {

    public static Response response;
    public static String rootPath = System.getProperty("user.dir");
    public static String casefile = rootPath + "/src/main/java/testcase/ebook.yaml";


    /**
     * 生成测试用例
     * return timeStr
     */
    public static void generateCase(String yamlPath) throws InterruptedException {
        String str = yamlToJson(yamlPath);
        String baseurl = null;
        JSONObject jsonObject = JSONObject.fromObject(str);
        for (Object key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (key.toString().startsWith("config")) {
                JSONObject configjson = JSONObject.fromObject(value);
                baseurl = JsonPath.read(configjson, "$.baseurl");
                log_info("解析用例中域名:" + baseurl);
            } else if (key.toString().startsWith("testcase")) {
                log_info("用例名字:" + value);
                JSONObject testcasejson = JSONObject.fromObject(value);
                String name = JsonPath.read(testcasejson, "$.name");
                String pathurl = JsonPath.read(testcasejson, "$.pathurl");
                String method = JsonPath.read(testcasejson, "$.method");
                Object params = JsonPath.read(testcasejson, "$.params");
                Object header = JsonPath.read(testcasejson, "$.header");
                Object validate = JsonPath.read(testcasejson, "$.validate");
                String apipath = baseurl + pathurl;
                log_info("用例名字:" + name);
                log_info("接口url:" + apipath);
                log_info("请求方法:" + method);
                log_info("请求参数:" + params);
                log_info("header:" + header);
                log_info("断言:" + validate);
                response = GetRequest.CommonRequest(method,apipath,params,header);
                log_info("接口状态码:" + response.statusCode());
                //log_info("接口响应结果:" + response.prettyPrint());


            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        generateCase(casefile);
    }


}
