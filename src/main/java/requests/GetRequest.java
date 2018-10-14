package requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static logger.Logger.log_info;

/**
 * 封装常用Requests
 * @author xinxi
 */


public class GetRequest {


    public static Response response;

    public void getRequest(){

    }

    /**
     * 求参数为json数据结构的post请求
     */
    public Response postRequest(String apiPath,String jsonData){
        Response response = given().
                contentType("application/json;charset=UTF-8").
                body(jsonData).
                when().log().all().post(apiPath.trim());
        return response;

    }


    /**
     * 请求有多个参数结构的post请求
     * 在请求URL中如果有很多个参数,可以用map进行传递,类似于字典
     */
    public Response postRequest(String apiPath, Map map){
        Response response =  given().params(map).
                when().post(apiPath);
        return response;
    }


    /**
     * 无参数的post请求
     */
    public Response postRequest(String apiPath){
        Response response =  given().when().post(apiPath);
        return response;
    }


    /**
     * 含有cookie的post请求
     */
     public Response postCookiesRequest(String apiPath, String cookies){
         System.out.println("Cookie = "+cookies);
         Response response = given().contentType("application/json; charset=UTF-8").
                 cookies("web-session", cookies).
                 when().post(apiPath.trim());
         return response;
     }



    /**
     * 通用请求方法
     */
     public static Response CommonRequest(String method,String apiPath,Object paramsMap,Object headerMap){
         RestAssured.useRelaxedHTTPSValidation();
         if (method.endsWith("post")){
             if (paramsMap != null && !paramsMap.equals("") && headerMap != null && !headerMap.equals("") ){
                 response =  given().headers((Map<String, ?>) headerMap).body((Map<String, ?>) paramsMap).
                         when().post(apiPath);
             }else {
                 log_info("post请求,body参数或者header参数必须填写!");
                 response = null;
             }
         }else if (method.endsWith("get")){
             if (paramsMap != null && !paramsMap.equals("") && headerMap != null && !headerMap.equals("") ){
                 response = given().headers((Map<String, ?>) headerMap).params((Map<String, ?>) paramsMap).
                         when().get(apiPath);
             }
         }
        return response;
    }






}
