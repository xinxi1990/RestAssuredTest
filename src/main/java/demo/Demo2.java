package demo;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Demo2 {

    @Test
    public void test_find_1() {
        String url = "http://testerhome.com/api/v3/topics.json";
        Response response = (Response) given().get(url).then().extract();
        System.out.println(response.path("topics.find { topic -> topic.id > 15796 }"));
        // { topic -> topic.id > 15796 }是一个表达式，获得满足topic.id > 15796的第一个数据的name
    }

    @Test
    public void test_find_2() {
        String url = "http://testerhome.com/api/v3/topics.json";
        Response response = (Response) given().get(url).then().extract();
        System.out.println(response.path("topics.find { topic -> topic.id > 15796 }.size()"));
        // { topic -> topic.id > 15796 }是一个表达式，获得满足topic.id > 15796的第一个数据的长度
    }

    @Test
    public void test_findAll_1() {
        String url = "http://testerhome.com/api/v3/topics.json";
        Response response = (Response) given().get(url).then().extract();
        System.out.println(response.path("topics.find { topic -> topic.id > 15796 }.node_name"));
        // { topic -> topic.id > 15796 }是一个表达式，获得满足topic.id > 15796所有列表
    }


    @Test
    public void test_findAll_2() {
        String url = "http://testerhome.com/api/v3/topics.json";
        Response response = (Response) given().get(url).then().log().all().extract();
        System.out.println(response.path("topics.findAll { topic -> topic.id > 15796 }.title"));
        // { topic -> topic.id > 15796 }是一个表达式，获得满足topic.id > 15796所有列表的title属性
    }

    @Test
    public void test_findAll_3() {
        String url = "http://testerhome.com/api/v3/topics.json";
        Response response = (Response) given().get(url).then().log().all().extract();
        System.out.println(response.path("topics.findAll { topic -> topic.id > 15796 }.size()"));
        // { topic -> topic.id > 15796 }是一个表达式，获得满足topic.id > 15796所有列表的的长度
    }


    @Test
    public void test_it() {
        String url = "http://testerhome.com/api/v3/topics.json";
        Response response = (Response) given().get(url).then().extract();
        System.out.println(response.path("topics.findAll { it.id = 15796 }.title"));
    }






    /**
     * 使用findall或者find断言
     * 使用了类似Groovy语法
     */
    @Test
    public void test1() {
        String url = "http://testerhome.com/api/v3/topics.json";
        given().get(url).then().statusCode(200).
                body("topics[0].id",equalTo(16142)).
                body("topics.find { topic -> topic.id > 16141}.title",
                        equalTo("技术沙龙招募：从研发到测试，手把手教你打造绿色应用")).
                body("topics.findAll { it.id == 15574 }.size()",equalTo(1)).
                body("topics.findAll { topic -> topic.id}.size()",equalTo(1));
        }


    @Test
    public void test2() {
        String url = "http://testerhome.com/api/v3/topics.json";
        int limit = 20;
        given().queryParam("limit", limit).get(url).then().log().body().statusCode(200).
                body("topics.findAll { it.title == '美团技术沙龙北京站：千万级日活 App 的质量保证' }.user.name[0]",equalTo("美团点评")).
                body("topics.findAll { it.title == '美团技术沙龙北京站：千万级日活 App 的质量保证' }.user.name",hasItem("美团点评"));
    }

    @Test
    public void test3(){
        String url = "https://testerhome.com/api/v3/topics.json";
        int limit = 3;
        useRelaxedHTTPSValidation();
        given().proxy("127.0.0.1",9999).queryParam("limit",limit).when().log().all().get(url).then().log().all();

    }


    @Test
    public void test4(){
        String url = "https://xueqiu.com/stock/search.json?code=sogo";
        useRelaxedHTTPSValidation();
        given().proxy("127.0.0.1",9999).cookie("").when().log().all().get(url).then().log().all();

    }


    public   Map paramsMap;
    public   String apipath;
    public   Map cookiesMap;
    public RequestSpecBuilder requestSpecBuilder;
    public RequestSpecification requestSpecification;

    @Rule
    public ErrorCollector collector = new ErrorCollector();


    @BeforeMethod
    public void preData(){
        useRelaxedHTTPSValidation();
        apipath = "https://stock.xueqiu.com/v5/stock/batch/quote.json";

        paramsMap = new HashMap();
        paramsMap.put("symbol","SH000001,SZ399001,SZ399006,HKHSI,HKHSCEI,HKHSCCI,.DJI,.IXIC,.INX");

        cookiesMap = new HashMap();
        cookiesMap.put("xq_a_token","9c75d6bfbd0112c72b385fd95305e36563da53fb");
        cookiesMap.put("xq_r_token","9ad364aac7522791166c59720025e1f8f11bf9eb");

        requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addCookie("xq_a_token","9c75d6bfbd0112c72b385fd95305e36563da53fb");
        requestSpecBuilder.addCookie("xq_r_token","9ad364aac7522791166c59720025e1f8f11bf9eb");
        requestSpecification  = requestSpecBuilder.build();


    }


    /**
     * 测试点:
     * 1.断言状态码==200
     * 2.断言items_size > 0
     * 3.断言结果中SH000001存在
     * 4.断言接口响应时间不大于1s
     */
    @Test
    public void test_xueqiu(){
        Response response = (Response) given().spec(requestSpecification).params(paramsMap).when().get(apipath).then().log().all().statusCode(200).
        body("data.items.size()",greaterThan(0)).
        time(lessThan(2L),TimeUnit.SECONDS).extract();

//        Assert.assertEquals(response.path("data.items[0].quote.symbol"),equalTo("SH000001"));
        Assert.assertEquals(response.path("data.items.quote.findAll{it.symbol=='SH000001'}.size()"),equalTo(1));
        Assert.assertEquals(response.path("data.items.quote.findAll{it.symbol=='SH000001'}.name()"),equalTo("上证指数"));


//        body("data.items.quote.findAll{it.symbol=='SH000001'}.size()",equalTo(1)).
//                body("data.items.quote.findAll{it.symbol=='SH000001'}.name()",equalTo("上证指数")).
//        body("data.items[0].quote.current",is(2681.64f)).
//        body("data.items[0].quote.current",closeTo(2681.64,10));



    }

    @Test
    public void test_json(){
        HashMap hashMap = new HashMap();
        hashMap.put("name","mike");
        hashMap.put("age","18");
        given().contentType(ContentType.JSON).body(hashMap).post(apipath).then().log().all().statusCode(200).log();

    }

    /**
     * 测试点:  导出数据
     *
     */
    @Test
    public void test_extract(){
        Response response = (Response) given().params(paramsMap).cookies(cookiesMap).when().get(apipath).then().statusCode(200).
                extract();
        System.out.println(response.body().prettyPrint());
        System.out.println(response.path("data.items[-1].market.status"));
        System.out.println(response.path("data.items"));

    }


    /**
     * 测试点:认证
     *
     */
    @Test
    public void test_auto(){
        String url = "https://api.github.com/user/emails";
        Response response = (Response) given().auth().oauth2("a8d40c50cc14e1160860cf1f2013f49504b1e903").get(url).then().extract();
        System.out.println(response.body().prettyPrint());

    }

    /**
     * 测试点:
     *
     */
    @Test
    public void test_filter(){

//        filters(new Filter() {
//            public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {
//
//
//                return null;
//            }
//        });

        String url = "http://jenkins.testing-studio.com:9001/base64.json";
        Response response = (Response) given().auth().basic("hogwarts", "123456").get(url).then().extract();
        System.out.println(response.body().prettyPrint());

    }



}


