import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-4-14.
 */
public class ClientFormLogin {


    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
              //  .setRedirectStrategy(new LaxRedirectStrategy())
                .setUserAgent("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E)")
                .build();

        try {
 /*
           HttpGet httpget0 = new HttpGet("https://xueqiu.com/user/login");
            CloseableHttpResponse response = httpclient.execute(httpget0);
            try {
                HttpEntity entity = response.getEntity();
                System.out.println("Login form get: " + response.getStatusLine());
                List<Cookie> cookies = cookieStore.getCookies();
                if (cookies.isEmpty()) {
                    System.out.println("None");
                } else {
                    for (int i = 0; i < cookies.size(); i++) {
                        System.out.println("- " + cookies.get(i).toString());
                    }
                }
            } finally {
                response.close();
            }

      */
         HttpGet httpget = new HttpGet("https://xueqiu.com/service/csrf?api=/user/login");
            CloseableHttpResponse response1 = httpclient.execute(httpget);
            try {
                HttpEntity entity = response1.getEntity();

                System.out.println("Login form get: " + response1.getStatusLine());
                EntityUtils.consume(entity);

                System.out.println("Initial set of cookies:");
                List<Cookie> cookies = cookieStore.getCookies();
                if (cookies.isEmpty()) {
                    System.out.println("None");
                } else {
                    for (int i = 0; i < cookies.size(); i++) {
                        System.out.println("- " + cookies.get(i).toString());
                    }
                }
            } finally {
                response1.close();
            }


            HttpPost httpPost = new HttpPost("https://xueqiu.com/user/login");
            List <NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("areacode", "86"));
            nvps.add(new BasicNameValuePair("telephone", "13660288081"));
            nvps.add(new BasicNameValuePair("remember_me", "on"));
            nvps.add(new BasicNameValuePair("password", "8709A6A92A8D441868D916B9EA2C07C2"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response2.getEntity();
                System.out.println("Login form get: " + response2.getStatusLine());
                String result = IOUtils.toString(entity.getContent(), "UTF-8");     ;
                EntityUtils.consume(entity);
                System.out.println("result:" +result);
                System.out.println("Post logon cookies:");
                List<Cookie> cookies = cookieStore.getCookies();
                if (cookies.isEmpty()) {
                    System.out.println("None");
                } else {
                    for (int i = 0; i < cookies.size(); i++) {
                        System.out.println("- " + cookies.get(i).toString());
                    }
                }
            } finally {
                response2.close();
            }

          /* HttpGet leftHand = new HttpGet("https://xueqiu.com/cubes/rebalancing/history.json?cube_symbol=ZH902949&count=20&page=1");
            CloseableHttpResponse response3 = httpclient.execute(leftHand);
            try {
                HttpEntity entity = response3.getEntity();
                String result = EntityUtils.toString(entity);
                System.out.println("----------------------");
                System.out.println(result);
                EntityUtils.consume(entity);
            } finally {
                response3.close();
            }*/


        } finally {
            httpclient.close();
        }

        long end=  System.currentTimeMillis() - start;
        System.out.println("use times :"+end);
    }
}
