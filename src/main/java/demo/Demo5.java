package demo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import static config.config.jira_api;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;


public class Demo5 {


    /**
     *  全局封装
     */
    @Test
    public void test_1(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeader("name","mike");
        requestSpecBuilder.addCookie("aaaaa");
        requestSpecBuilder.addHeader("head","1111");
        RequestSpecification requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(200);
        //封装响应断言状态码200
        responseSpecBuilder.expectResponseTime(lessThan(1000L));
        //封装响应响应时间小于1000ms
        ResponseSpecification responseSpecification = responseSpecBuilder.build();
        given().spec(requestSpecification).get("/main").then().spec(responseSpecification).extract();
    }


    /**
     *  全局封装proxy
     */
    @Test
    public void test_2(){
        RestAssured.proxy("127.0.0.1", 9999);
        // 设置接口代理
        given().get("/main").then().extract();
    }

    /**
     *  全局封装proxy
     */
    @Test
    public void test_3(){
        // 设置接口代理
        given().proxy("127.0.0.1",9999).get("/main").then().extract();
    }



    /**
     *  全局封装proxy
     */
    @Test
    public void test_4(){
        // 设置接口代理
        RestAssured.baseURI = "baseURI11111";
        RestAssured.basePath = "/resource";
        given().log().all().get("/main").then().extract();
    }



    /**
     *  url不编码
     */
    @Test
    public void test_5(){
        String response = given().urlEncodingEnabled(false).get(jira_api).asString();
        System.out.println(response);
    }


    /**
     *  重置
     */
    @Test
    public void test_6(){
        RestAssured.reset();

    }
}
