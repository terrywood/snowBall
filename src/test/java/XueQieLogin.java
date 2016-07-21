import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class XueQieLogin {
    public static void main(String[] args) throws Exception {
        XueQieLogin simpleLogin = new XueQieLogin();
        simpleLogin.hi();
    }

    private void hi () throws Exception {
        HttpURLConnection connection = null;
        String website ="https://xueqiu.com/";
        URL url = new URL(website);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        Map<String,List<String>> map =connection.getHeaderFields();
        System.out.println(map);

        List<String> list = map.get("Set-Cookie");
        String sessionId ="";
        for(String str: list){
            System.out.println(str);
            sessionId+=str=";";
        }

        System.out.println(connection.getResponseCode());
        System.out.println("----------------------------------------");



        URL csrf = new URL("https://xueqiu.com/service/csrf?api=/user/login");
        connection = (HttpURLConnection) csrf.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Cookie", sessionId);

        List<String> list2 = map.get("Set-Cookie");
        for(String str: list2){
            System.out.println(str);

        }



        String areacode="86";
        String userID="13660288080";
        String passwd="8709A6A92A8D441868D916B9EA2C07C2";

        RequestParaBuilder builderForm = new RequestParaBuilder("https://xueqiu.com/user/login")
                .addParameter("areacode", areacode)
                .addParameter("telephone", userID)
                .addParameter("password", passwd)
                .addParameter("remember_me", "on" );
        URL login = new URL(builderForm.build());
        connection = (HttpURLConnection) login.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Cookie", sessionId);
        InputStream in = connection.getInputStream();
        BufferedReader bd = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();
        String text;
        while ((text = bd.readLine()) != null) builder.append(text);
        String result = builder.toString();




        System.out.println(result);
        if (connection != null) connection.disconnect();


    }



}
