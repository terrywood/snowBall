import utils.FileLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class SimpleLogin {
    public static void main(String[] args) throws Exception {
        SimpleLogin simpleLogin = new SimpleLogin();
        simpleLogin.hi();
    }

    private void hi () throws Exception {
        HttpURLConnection connection = null;
        String areacode="86";
        String website ="https://xueqiu.com/";
        String userID="13660288080";
        String passwd="8709A6A92A8D441868D916B9EA2C07C2";
        boolean rememberMe=true;
        connection = login(areacode, userID, passwd, rememberMe);
        connection.setRequestMethod("POST");
        try {
            String cookie = connection.getHeaderFields().get("Set-Cookie")
                    .stream()
                    .map(x -> x.split(";")[0].concat(";"))
                    .filter(x -> x.contains("token=") || x.contains("s="))
                    .reduce("", String::concat);
            System.out.println(cookie);
            System.out.println(connection.getResponseCode());
            InputStream in = connection.getInputStream();
            BufferedReader bd = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String text;
            while ((text = bd.readLine()) != null) builder.append(text);
            String result = builder.toString();

            System.out.println(result);

            //FileLoader.updateCookie(cookie, website);
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    private HttpURLConnection login(String areacode,
                            String userID,
                            String passwd,
                            boolean rememberMe) throws Exception {

        areacode = areacode == null ? "86" : areacode;
        if (userID == null || passwd == null) {
            throw new IllegalArgumentException("null parameter: userID or password");
        }

        RequestParaBuilder builder = new RequestParaBuilder("https://xueqiu.com/user/login")
                .addParameter("areacode", areacode)
                .addParameter("telephone", userID)
                .addParameter("password", passwd)
                .addParameter("remember_me", rememberMe ? "on" : "off");

        URL url = new URL(builder.build());

        return (HttpURLConnection) url.openConnection();
    }

}
