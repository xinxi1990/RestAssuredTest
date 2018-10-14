package demo;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.sf.json.JSON;
import org.apache.http.client.methods.RequestBuilder;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.RestAssured.*;
import static  io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath ;
import static  org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.lessThan;



public class Demo4 {


    /**
     *  发送json请求
     */
    @Test
    public void test_1(){
        Map<String,Object> map =  new HashMap<String, Object>();
        map.put("name","age");
        map.put("age","18");
        given().contentType(ContentType.JSON).body(map).log().all().post("www.baidu.com");
        /*
        Body:
        {
            "name": "age",
                "age": "18"
        }
        */
    }


    /**
     *  断言响应时间
     */
    @Test
    public void test_2(){
        Response response = (Response) given().get("www.baidu.com").then().time(lessThan(1000L)).extract();
        System.out.println("接口响应时间:"+ response.time() + "ms");
        // lessThan是最大范围, greaterThan是最小
        System.out.println(response.body());


    }


    /**
     *  schema校验
     */
    @Test
    public void test_3(){
          String json = "";
          assertThat(json,matchesJsonSchemaInClasspath(String.valueOf(new File("/Users/xinxi/Desktop/RestAssuredTest/src/test/resources/product.schema"))));
//        String url = "http://testerhome.com/api/v3/topics.json";
//        Response response = (Response) given().get("/products").then().assertThat().body(matchesJsonSchemaInClasspath("/Users/xinxi/Desktop/RestAssuredTest/src/test/resources/product.schema"));

    }


    /**
     *  认证basic
     */
    @Test
    public void test_4(){
        given().auth().basic("username", "password").get("/main");
    }


    /**
     *  认证OAuth 2
     */
    @Test
    public void test_5(){
        given().auth().oauth2("xxxxx").get("/main");
    }


    /**
     *  提交file文件类型
     */
    @Test
    public void test_6(){
        given().
                multiPart(new File("/path/to/file")).
                when().
                post("/upload");
    }


}
