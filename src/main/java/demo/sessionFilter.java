package demo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.SessionConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class sessionFilter {

    @Test
    public void sessionFilter_test(){
        RestAssured.config = RestAssured.config().sessionConfig(new SessionConfig().sessionIdName("JSESSIONID.ed8252ed"));
        SessionFilter sessionFilter = new SessionFilter();
        System.out.println(RestAssured.config.getSessionConfig().sessionIdName());
    }


}
