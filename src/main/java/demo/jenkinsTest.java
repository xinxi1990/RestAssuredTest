package demo;

import io.restassured.builder.ResponseBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.commons.codec.binary.Base64;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static config.config.*;
import static io.restassured.RestAssured.filters;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class jenkinsTest {

    public static ArrayList arrayList = new ArrayList();


    /**
     * 实现自动登陆jenkins，并自动更新每个后续请求的cookie，
     * 以触发demo job的构建为例
     */
    @Test
    public void test1(){
        String loginapi = login_api;
        HashMap paramsMap = new HashMap();
        paramsMap.put("j_username",j_username);
        paramsMap.put("j_password",j_password);
        Response response = (Response) given().redirects().follow(false).params(paramsMap).get(loginapi).then().extract();
        final Map<String, String> logincookie = response.getCookies();
        System.out.println("获取登录的Cookies:" + logincookie);
        System.out.println("开始创建filters" );
        filters(new Filter() {
            public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext filterContext) {
                requestSpec.cookies(logincookie);
                Response response = filterContext.next(requestSpec, responseSpec);
                return response;
            }
        });
        Map headerMap  = new HashMap();
        given().log().all().headers(headerMap).post(buildapi).then().statusCode(201).extract();
    }


    /**
     * 解密并进行结果断言
     */
    @Test
    public void  test2(){
        filters(new Filter() {
            public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
                Response response = ctx.next(requestSpec, responseSpec);
                System.out.println("origin response");
                System.out.println(response.getBody().asString());
                System.out.println("decode response");
                String str  = deepdecodeBase64(response.getBody().asString());
                Response newResponse = new ResponseBuilder().clone(response)
                        .setContentType(ContentType.JSON)
                        .setBody(str)
                        .build();
                System.out.println("response filter");
                System.out.println(newResponse.getBody().asString());
                return newResponse;

            }
        });

        Response response = (Response) given().auth().basic(base_user, base_pwd).
                get(base_url).then().extract();
        Assert.assertEquals(response.path("data.items.quote.findAll{it.symbol=='SH000001'}.size()"),1);
        }


    /***
     * 递归解析base64
     * @param str
     * @return
     */
    public static String deepdecodeBase64(String str){
        if (str.contains("{") && str.contains("}")  ){
            return str;
        }else {
            String newStr = new String(Base64.decodeBase64(str));
            return deepdecodeBase64(newStr);
        }
    }



}
