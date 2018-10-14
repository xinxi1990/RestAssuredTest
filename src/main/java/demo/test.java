package demo;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.*;
import io.restassured.response.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static io.restassured.path.json.JsonPath.from;

public class test {

    private static String apipath = "https://devcas.cbim.org.cn/login";

    public static void main(String[] args) throws UnsupportedEncodingException {
        RestAssured.useRelaxedHTTPSValidation();

        String excution = "13420d91-8b0b-47df-9657-7dc7bc23ca58_ZXlKaGJHY2lPaUpJVXpVeE1pSjkuZURrM1RrWmlXSFJZVTFBdmFUUlZjVEpxYlhSQlJVRnhNbmMxYzIxVWJGQlhlblJDYzBSR1NteERUMWwzTVRBMWF5dGFhRTAwVERnck1XUmxXVlY2VkdSSFRqSkJVelJzYlRsM1JsUkhhbWhZSzBGMVQxRlFkV014TkVsWGVXZG9kbUZwYkdWTWIyNVhUMkY0VVV0SVpXdzFjbFF3Y1VaWlZtSkdOR3huTkdneFdtVmpOSFUyV2sxbFpsTkpSMGhPYm1WaVdITjZTa2RhTTJ0T1VqQldUblU0ZDA5T2NtSlFURzF5YVVRMFlXSnFhazg1VG1nNVJHZzJWSEZzT0ZGV2R5OTRlbVJVTmtaVVVWTTFZeloyWVdsM1Z5OXRSMnd6ZUZkbFEyMURWVzlhTVZoVlpXMTBXVWh3VG0xUVkwaEJOSEkzY0RGV1JYZEJWa1puU1V4UFkwRjVZMlF6UlRselJYTXpiRzFuVkhSdFZESnBkalYxYUV0SU9IaGtaRzlMYkZOM2NHNTVMMFZrVFhaSmRXNU1WR1JLV1ZsSVJuQjVTRGQwV25Od1QxWnNOblJzYXpSMUwyMDFTR2xhTVRaUk9FSk1aR0owWlVKTWMzcDJPV0pHWW1WVFVHUm9iV1JXZVVSV1pqZElOWGhaT1VkbVJrc3ZZMmt2ZERObUwyRTNNVVZqVFRGaWJFVXJSM1paWWxwSFprWXdaV3RoZW5WdVNtWmlWVVZZVWxSWldUWkVObTA1YzFoMWJHMU1lVUpoYTFjMFpFMTBhWE5vUkdsYU9VcG1kM294ZGpORlVWQktjM1l3TWxnd2RGSlBiMlpEUTFCcFUzZHRWWFIwUzBKTlpVUmhSbWhSVG1sTGNtSkxWalpUY1V0bWFFZGpjVmgwZGtGc1QwSlpTMkpHU0ZSTmRXbHVhbEl3WWtVd1ZDOVVkSEkyYnpGS05sWjVSM3BGYWxGdVZtdzRjbWxIYlVjclVrSkZjRk5yVlhWc1UyUmxSbFpaZHpSSlFraEtNVGhvT0dVeWVFNUJabm8yUzIwdmVGWTRhbk5RYVhWcldreFpWSGxEU0RoNFZ6UkpUMmhSUTA4eVRGcHFSbXhXUjBkMUwyaHZUVmt6ZW5CSU9GTXdVR3BhTnpScGRtTnVMMmxqVjJKM1YyTm5UbVpVS3pGV09WQmpkWGx5ZFZkYVIyZFVSSFl2VmxsRUwzbFBOR3AzUlZaR1ZHRk5XVzlyVWpjM1FUSlBSR001VEhkTmJHazNSVFl5VVZCc2JVcHZhblI2T1VKWlFqSkhlbWRMYmtKeWR5dFROVFIyVUZkWVIxRnBSMkp0VDFsUlVDOURTazEwWXpkUE4zTjVZVWhuU1hSSVJWZHpRWFFyVFZndlNGcE9lazFIVW05QlVEbE1NMFpqYUdkaVFUQlhURnBXZFRVd1JGbHNUa2xMZDBkUlpHWXJORzlaY2tsRGVVTnJSRWhRV0RsbE1raEdNbGsxZUcxVVpsUkhUSEpEUW0xNWEzTnZaekEyTVRnMFRXRkJhbFkyY1dkblVEVllWbWROYXpsNE9HTkdTM2d3Tld3M05FcEVXRVppZDNkdEsxRXpWMnhxYVZGaFNIVndiRE5RUjJOMU1GazNSMjRyY0VJelZrdDVVRlI1UW5aUVNsWm5iMmh0U25GS1kyZDNZaXMwT0daMWIxTmtOMWd6VUM5c1VEUnpTRkpuZVhKbllXVmlZWEpqYlRoV1VqTldSRXhMVUdKSVNXMXFkazV5ZHpJMWRDdHRRbWx4Y0hKQmJVZEJiekkxTUdwU2QzbGtaVmM0ZGpGUGVtTk9ORzF3ZUhObFYzZ3JkRnBLYm5jNE1YaHpNRFZSWlc1VU5HSlRhbVl2ZFhKMVJYSm9hV1ZoWlZOamRrVnhLMlp2ZFRKbVZqQnVZMUIxVm1kS1QwNXNSVVZIY0d3ek5ETTFjSEFyYUN0RmIwUnBWRXd3YkhOSlVWZEhiRkV6WjNGRU1VZEJTVlJVYUVGaWRXWjBkVWQ0VlhwMGExZE9NbTF6YVRoMmJVWnpiMmQxUVRWRFkwRjFNbmhTWTBwNGExZzJUVTlyYlV4MFl6WnlWekk0U1RKeWVWcDJaVzVhWWl0WWQySm1LMmw2Vms5UlVVZzRPVmh0ZFROQ2VtZGtjRWh4WlRRNFZtWjZOR2huZUVweE9IUlBUR0ppU2pWcE0yVlBaMDh4WTB0MWRXOVJUekI1YVZKQ1ZtWXhTbXBUV0dJMVQzbEhZMmhTWjJoMWNWaEpURzFUYVZSdlNUTkZiREpGZGxoeVFuUkJNMmxyYjBsbllWVm9abVpVU0dNMlluRkVVMU5zZEdGclptWlNTVU5rWTJWQ0t6Tm5TRGRZVVdkTVdtVndWVlpzWlhGRFlUaExVV3QyTjJoRVJHMUhhMDVSYURsc01UTndORVIzVlZjd2FXSXdlbmxhVVdscWVFc3pNMk5tVWxWNlRXcFdVRFp3YlV4VUwyOUpZM0JISzNwT1JVNUZTV2hxZEZkS1VpOWpkRE53WjJ4MlZteHRVMngwTkcxNU1Yb3phR3BQSzNwTlZFTlBSbWQ1VUZWbmVVWkRkbFpvWjI4NGFESm5UR1ZKWjFKcVNGbDJNRk5VUldKSFZsY3ljSFEzWmpCU2IyRnNTMWxwWTBsU09DdEhRMFpGVTNwQ1FXTnZNRk5UV1ZoNk5UQnZZWEJpZVNzMlZ6RnpORFpyTlhKaWJsb3hPRE5KVmtzeVpHTXlSbEpXYzFOamNGaGFielZvUVVOdlluWTJWeXREVFRaak1VcE1iRzFyUWxST2VGSmtkM0pOVERSbGFGVjRVazFXUjFoMlVVOXlLMVJJV1d4emN6VlBZMHhuYVN0SlIySkxRbTFGTkhOMFJuRTJSR0p5YjJGQlkzcG9NMHdyTWl0V2JYSmhWbU5vU21KSmFIZEdRVFpxVEZaTmRHRlVTMFp3T0c1T2NHWlhWa1k1YUd0dkt6TnpWMmt5ZDNGSUwxVnllbXg1UXpWWGExQkpZVGwwUWxablZWTXhjSFpEUm5CV0sxZGlhVVZYYWxKd2N6bHRZbTlqVmpkNmEwODBURGRDWVU0eFZYbEhjblpXTlRZMWMydGpTRk5LZDNjMGMwbHNWVGd3ZUVndmFtRXpaV05FUm1JMk4wdE1kV1l4Vm5CYWFtOUdXSEZSTDJvNVdVMTBZVFJKVTBvd056aHZLMVp5UlZaWWVEaHVRMDFCY0ZCRmQzUkRSRTkyUlc5dk0yaE1jVk5tUWtWT0sydGpkWGR5Y0Rsa01YSlBPV3hpWjJoV09XRTNMekZZVkRSRFdYbExkRGRCTm1kQmNrcDVjVkJrWm5weWFGRmpMMnhhY1ZSUlUzQkZkV1F6TDFCRFltOU5TRFYyUlU5NWRtMVlhamhIUmpkME5uRlZXR0p6WnpOT2QzbE9iVXAyTDJvd1Jta3ZRVkp0V0N0c2MwRkZORTQwY2s1eVVWRkNVbWg0TVVFMllrZGxjRGgzT1Zod1ZUQjFNRkIyVld0dFFsZEhMMnhJYVVaV1UyMXRkM1o1TWxGWmNrSlFiMkV2Y1RacVZYWm5aWEJ2VmxWUmRuVXhVak5PV0d4d1psaDVkVU5hYlV4dmRXSm5hRlZJY2sxUWJsTkdWWEpMVW00MldqTm5aREJaTlVSdWNWVnZlbFpuWlVJMFNtdFpVREVyYW5walYyaEhkRWhzWWpaa1RVOUdkRzluV0Rac1ZVWXhhMFJ5V210TVJtOVNXV1pxVDFORVRFMDJha1pFYlhkbFNreDZXVVJQWW5keVJrWldhbVJ1Wmt0VlluaHlaWFZsVVZocVVIRklUbFp0YUdWVVNtbzBNSE5IVFZkME1ucEtXQzlIZG04d09WTXJkVUpNT0VGa2RqUjVjR0l5UkhCeWMxVlZXbEJDWkM4MFRtaE9SRWhpYURoMmJrVTBaRTAxTVZSSkwxVmFUUzg0TnpkT09WUnNiREJZVlRWMlNDOW9jMGxRWW5SWVRXZEhiVzVRYms1cVpsWjVRVUVyYUhoaVUyaHRVRzFPYmpWSFQyUkNjRWcwTkUxcFpGYzJaa05TYW1sU2FsaFZlR0o2Ynpoc1IwdDVTMGRGWWtSWVNYcFROVTlKWWpodFZVbFNjbE5IVm1GbVdYbG5hSGRHT1VwNlNXRkhiWFJxY0hCRVUwMVBkbU5rYkVsdU9YSldSbk55ZVZSa09UWmhkbHBqUjNocUszbElWVFl5VTNvM1VFUk9UVmhIUkZndmVFVkpVVkU1VkZWSFQyaG9jVFpYWjJwVVdUbHFVVmR6YVhSTFFUZGxjMlJvU0U4eVJuazFaRU5LYjB0eVJXNXRXREpaTUdVMFNXc3ZkbkpHUTB0UU5HaEpZMjl2TWsxS1UySXplV2Q1YlRSRWNXeENNVkJ5Wkdwc2JHazJUemhHVDFNMmEwbE1TeXR3WkhWbE1VaE9jbnBJUWxVMGNYZE1hMVJYVEhBNFpVUm5TM2RxUVVwMlNtMXpXbUV4UjFObVdHMVFWRVZ6YmxweU9YaENVMmhNUW5KbVJGbEtXa05pTm1sTlkxZFhURkp3UjFaRGF6TkpjSGsxZEdWMWRFTktjVGhwZUcxcWRuQkpibnBLYzB4clZVd3hhWFIwTDFkSmMyTndVbmROYURaeWRXbERaMWgwV0ZaaVJYWk1aa3haYzBwc1IyaHlTbVpHV1ZFdlMyMHJlVzB5VkZOamRIcHhPVkpsSzFFMVVEY3ZiR2hIYVVJNVdISTFRbkpZWldoMloxRXhZbE14VEhrek1EaEpZWFZwVG5sRVdFSlpVQzh5Y0dsc1dVTmxhbnBXTnpWNFltNUhTbmhPU25Oc2JWbGpNbFZUYTBsdWRsWnZiVnBoZWprM1puRkVZV1JoUm05bGRrZDJRMUZxY1hFMlYyczBNVmhpVEZreVIyUjZabXRHYVRWTE5sQm9hRFYzYlcxME1HRk1jMDltUjFZclRrRmlhakUyVGtSa2NESldTQ3MyTDBGb2MyNHZhREZ3VDIxekt6bE5lalpPYmt3d1ZXdERNVVJuYmtsM1dsa3JlR2t6ZUVaQ2QyNTBaVzl4ZDB0Q1YyNWpVamR3UjFWbWQyVkZhVXhxTXpjM2NFZzNTMDltYWpoV1NVdHFVRXBFVkN0QlpsZDJZVk4xV1hZMU4xSlNXVE5yYkRac1QyUm9WbGhaYml0VVpUQTFkVWxzZW10S1pXSTNaVGR1WTA5NlMxUm5SVVJaVjNoMWQxSTJSRGgzU0VzM2RXNU1SRVJPUVRKVFdWcExlR0pOVm5OMWNEQlFTRzFDWTBObFIySmxUMmN2WnpNd09ITlVkMFo0WTBvelZFdEdUazVETml0VWRIQkJkVFJYV0daNmNHUlhUSGc1UTNwdlJraEpXa2xLUzFjMGFtaFZSamt4TmxRdldteDFSbmQyZFZocFlqRnJTMGwwYW5CTU9IUXdUMFZtUVV0R2NWZDZkbFY0TVhkWmMwaHlLM2QxYlRST2IxRnBNamd3YXpKck4ybEJPRFUwVTBaVFNsbFFUWFZHVXpKc2NYbDBOR2hKYldKdWFBLnBoaGt6U0Jra0kxcjM5MVhfeU5kT21KdloyYi0wanh6OHNoaEVqZWpxWTRQNmFQSlUxRVRxeHRtZVA1QllvVk5aSHdYNWViVXQwQU5tTUg4dlZzcnlR";

        Response response = RestAssured.given().config(RestAssured.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs("x-www-form-urlencoded",
                                        ContentType.URLENC)))
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", "15201320982@163.com")
                .formParam("entcode", "liyang1211")
                .formParam("password", "123456")
                .formParam("rememberMe", "false")
                .formParam("execution", excution)
                .formParam("_eventId","submit")
                .formParam("geolocation","").redirects().follow(false).request().post(apipath);

        Map loginCookies = response.getCookies();
        System.out.println("login中的cookise:" + loginCookies);
        String headerLocationValue = response.getHeader("Location");
        System.out.println("login中的定向url:" + headerLocationValue);
        String encodedString = URLDecoder.decode(headerLocationValue);
        System.out.println("Encoded String: " + encodedString);
        Response resp2 =
                given().
                        cookies(loginCookies).
                        when().
                        get(headerLocationValue);
        String JSESSIONID = resp2.getCookie("JSESSIONID");

        System.out.println(JSESSIONID);

        Response response1 = given().cookies(loginCookies).get("https://test.china-bim.org/api/user/uaa/login?redirect_uri=https%3A%2F%2Ftest.china-bim.org%2Fres%2Findex.html%3Ftoken%3Dbearer+e4945642c191805c937eacd7f3d0e841cb670980f37891b7ab3177cf1f25b776&ticket=ST-21190-S75onxLwkshKlg0o4u51-oSnKSQ-localhost");
        System.out.println(response1.getCookies());




//
//
//
//        Map headerMap = new HashMap();
//        headerMap.put("Content-Type","application/json;charset=UTF-8");
//
//        Map cookies = new HashMap();
//        cookies.put("JSESSIONID",JSESSIONID);
//        cookies.put("X-AUTH-TOKEN","3bdc271379136cfb18fc4ae55319a01221c3c139d9395647285d9bd39451f6cb");
//        cookies.put("X-AUTH-TOKEN","3bdc271379136cfb18fc4ae55319a01221c3c139d9395647285d9bd39451f6cb");
//
//
//        String collectpath = "https://test.china-bim.org/api/tracks/v1/collect/add?id=6272";
//        Map paramsmap = new HashMap();
//        paramsmap.put("id","6272");
//        Response collectresponse = given().cookies(cookies).body(paramsmap).when().get(collectpath);
//        System.out.println(collectresponse.body().prettyPrint());

    }



}
