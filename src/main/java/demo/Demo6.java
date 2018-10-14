package demo;
import io.restassured.http.Header;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Demo6 {
    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();
    @Test
    public void TestAssert(){
        errorCollector.checkThat("1",equalTo("a"));
        errorCollector.checkThat(2,equalTo(2));
        errorCollector.checkThat(3,equalTo(2));
    }
    @Test
    public void run(){
        String url = "http://platform.app.autohome.com.cn/platform_v7.5.0/api/opt/propaganda";
        //设置入参
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("pm", "2");
        parameters.put("a", "2");
        parameters.put("v", "7.6.0");
        parameters.put("deviceid", "7b64fb2877cbb15900b670886ea3f71488899c90");
        parameters.put("cityid", "110100");
        //设置url请求头
        Header first = new Header("username", "xushizhao");
        Response response = given()
                .parameters(parameters)
                .header(first)
                .get(url);
//查看请求正文
        System.out.println("返回信息：" + response.asString());
        System.out.println("content -type:" + response.getContentType());
        System.out.println("状态码:" + response.getStatusCode());
        System.out.println("响应头:" + response.getHeaders());
        System.out.println("cookies:" + response.getCookies());
        System.out.println("响应时间:" + response.getTime());
    }

    @Test
    public void testBi(){
        String url = "http://alpha.bihaopro.com";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("sellnum", "2");
        parameters.put("sellprice", "2");
        parameters.put("sellpwd", "e10adc3949ba59abbe56e057f20f883e");
        parameters.put("currency_id", "2");
        parameters.put("currency_trade_id", "1");
        given().
                contentType("application/json").
                body(parameters).
                when().
                post(url +"/Trade/sell"); }
    @Test
    public void baidu(){
        given()
                .queryParam("wd", "mp3")
                .when()
                .get("http://www.baidu.com/s")
                .then()
                .log().all()
                .statusCode(200)
                .body("html.head.title", equalTo("mp3_百度搜索"));
    }

    @Test
    public void xueqiu(){
        useRelaxedHTTPSValidation();
        given()
                .log().all()
                .header("X-Requested-With", "XMLHttpRequest")
                .formParam("username", "15600534760")
                .formParam("password", "xxxxxx")
                .when()
                .post("https://xueqiu.com/snowman/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("error_description", equalTo("用户名或密码错误"));
    }
    @Test
    public void xueqiu2(){
        useRelaxedHTTPSValidation();
        given()
                .log().all()
                .header("X-Requested-With", "XMLHttpRequest")
                .body("remember_me=true&username=15600534760&password=xxx&captcha=")
                .when()
                .post("/snowman/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("error_description", equalTo("用户名或密码错误"));
    }
    @Test
    public void tester(){
        given().queryParam("limit",20)
                // .proxy("127.0.0.1",8080)
                .when()
                .get("https://testerhome.com/api/v3/topics.json")
                .then()
                .statusCode(200)
                .log().all()
                .body("topics[1].id",equalTo(15872))//失败后不再执行下面的
                .body("topics.findAll{x->x.id>15141}.title[0]",equalTo("Testerhome 深圳第四期管理沙龙"))
                .body("topics.find{it.id>15141}.title",equalTo("Testerhome 深圳第四期管理沙龙"))
                .body("topics.findAll{it.id>15141}.size()",equalTo(20))
                .body("topics.findAll{it.title=='Testerhome 深圳第四期管理沙龙'}.size()",equalTo(1))
                .body("topics.find{it.title=='关于 assert 的问题'}.user.name",equalTo("彩虹哥哥"))
                .body("topics.findAll{it.title=='关于 assert 的问题'}.user.name",hasItem("彩虹哥哥"))
                .body("topics.findAll{it.title=='关于 assert 的问题'}.user.name[0]",equalTo("彩虹哥哥"))
                .body("topics.title",hasItems("Testerhome 深圳第四期管理沙龙","Jmeter 里如何设置登陆接口只调用一次？"));
    }
    @Test
    public void testHomeAssert(){
        useRelaxedHTTPSValidation();
        Response response = given().queryParam("limit",20)
                .when()
                .get("https://testerhome.com/api/v3/topics.json")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();
//        errorCollector.checkThat(response.statusCode(),equalTo(500));
//        errorCollector.checkThat((Integer)response.path("topics[1].id"),equalTo(157960));
        //  errorCollector.checkThat(response.path("topics.title"),hasItems("Testerhome 深圳第四期管理沙龙",""));
    }
    @Test
    public void assertion(){
        useRelaxedHTTPSValidation();
        int limit=10;
        Response response = given()
                .queryParam("limit", limit)
                .when()
                .get("https://testerhome.com/api/v3/topics.json")
                .then()
                .extract().response();
        errorCollector.checkThat(response.statusCode(), equalTo(400));
        errorCollector.checkThat((Integer) response.path("topics.size()"), equalTo(3+limit));
        errorCollector.checkThat((Integer)response.path("topics[0].id"), equalTo(16142));
        errorCollector.checkThat((response.path("topics.find { x -> x.id > 16141 }.title ").toString()),
                equalTo("技术沙龙招募：从研发到测试，手把手教你打造绿色应用"));
        errorCollector.checkThat((response.path("topics.findAll { x -> x.id > 16141 }.title[0] ").toString()),
                equalTo("技术沙龙招募：从研发到测试，手把手教你打造绿色应用_error"));
        errorCollector.checkThat((Integer) response.path("topics.findAll{ it.title == '技术沙龙招募：从研发到测试，手把手教你打造绿色应用'}.size()"),
                equalTo(1));
        errorCollector.checkThat((Iterable<String>) response.path("topics.title"), hasItems(
                "技术沙龙招募：从研发到测试，手把手教你打造绿色应用",
                "美团技术沙龙北京站：千万级日活 App 的质量保证"));
    }
    @Test
    public void auth(){
        given().
                auth().basic("hogwarts","123456")
                .get("http://jenkins.testing-studio.com:9001/")
                .then().log().all()
                .statusCode(200);
    }
}


