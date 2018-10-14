package demo;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;


/**
 * 入门
 * @author xinxi
 */


public class Demo1 {


    @Test
    public void test1() {
        //charles如果代理localhost必须替换电脑的ip or localhost.
        String url = "http://192.168.1.105:8080/j_acegi_security_check";
        String url1 = "http://localhost.:8080";
        Response response = get(url);//发起get请求，并获取响应
        if (response.getStatusCode() == 200) {//如果响应码等于200
            System.out.println((response.getStatusCode()));
        }
    }


    /**
     * 设置RestAssured
     */
    @BeforeMethod
    public void setup(){
        RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().
                defaultCharsetForContentType("utf-8","application/x-www-form-urlencoded")).
                httpClient(HttpClientConfig.httpClientConfig().setParam("http.connection.timeout",2000).
                        setParam("http.socket.timeout",2000));
    }



    @Test
    public void test_xueqiu() {
        RestAssured.useRelaxedHTTPSValidation();
        //设置所有都跳过https认证
        Map headerMap = new HashMap();
        headerMap.put("Accept","application/json");
        headerMap.put("Cookie","xq_a_token=5b04b7085058212b78cc7170a7f0639b7417cee7; u=2434077225");
        headerMap.put("User-Agent","Xueqiu iPhone 11.5");
        headerMap.put("Accept-Language","zh-Hans-CN;q=1");
        headerMap.put("Accept-Encoding","br, gzip, deflate");
        headerMap.put("Connection","keep-alive");
        headerMap.put("Host","api.xueqiu.com");
        String url = "https://101.201.62.20/statuses/comments_excellent.json";
        String params = "count=20&id=113160051&page=1&reply=1&_=1535869337009&x=" +
                "0.353&_s=1ed9b2&_t=157CAA56-2803-4335-B1E1-CAC8F15122D5.2434077225.1535869287698.1535869337010";
        Map paramsMap = new HashMap();
        paramsMap.put("count",20);
        paramsMap.put("id",113160051);
        paramsMap.put("page",1);
        paramsMap.put("reply",1);
        paramsMap.put("_","1535869337009");
        paramsMap.put("x",0.353);
        paramsMap.put("_s","1ed9b2");
        paramsMap.put("_t","157CAA56-2803-4335-B1E1-CAC8F15122D5.2434077225.1535869287698.1535869337010");
        Response response = given().headers(headerMap).params(paramsMap).get(url);
        System.out.println(response.getBody().print()); //打印response未格式化
        System.out.println(response.prettyPrint());
        response.then().body("comments", hasSize(2)); //判断list的长度是2
    }


    /**
     * 使用jsonpath解析key和value
     */
    @Test
    public void test3() {
        String url1 = "https://testerhome.com/api/v3/topics.json?limit=3&offset=0&type=last_actived";
        Response response1 = given().get(url1);
        JSONObject jsonObject = JSONObject.fromObject(response1.prettyPrint());
        //List obj = JsonPath.read(jsonObject.toString(), "$.topics[*].user.login");
        //Object obj = JsonPath.read(jsonObject.toString(), "$.topics[*]");
        List obj1 = JsonPath.read(jsonObject.toString(), "$.topics[*][?(@.node_id < 100)]");
        for (int i = 0; i < obj1.size(); i++) {
            Map map = (Map) obj1.get(i);
            System.out.println(map.get("node_id"));
            Assert.assertNotEquals(map.get("node_id"),null);
            JSONArray array = (JSONArray) jsonObject.get("topics");
            for (int j = 0; j < array.size() ; j++) {
                if (array.get(i) instanceof JSONObject){
                    JSONObject jsonObject1 = JSONObject.fromObject(array.get(i));
                    Object jsonObject2 = jsonObject1.get("user");
                    if (jsonObject2 instanceof JSONObject){
                        System.out.println(((JSONObject) jsonObject2).get("login"));
                    }
                }
            }
        }
    }

}

