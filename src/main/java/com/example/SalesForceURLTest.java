import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class SalesForceURLTest {

    public static void main(String args[]) throws Exception {

        String[] hostList = { "https://test.salesforce.com"};

        for (int i = 0; i < hostList.length; i++) {

            String url = hostList[i];
            getStatus(url);

        }

        System.out.println("Task completed...");
    }

    public static String getStatus(String url) throws IOException {

        String result = "";
        int code = 200;
        try {
            /* Without proxy settings code
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.connect();
            code = connection.getResponseCode();*/

            // With Proxy Settings
            /* System.setProperty("http.proxyHost", "Proxy url");
            System.setProperty("http.proxyPort", "8080");
            System.setProperty("http.proxyUser", "username");
            System.setProperty("http.proxyPassword", "some password");*/
            URL siteURL = new URL(url);
            /*Proxy proxy1 = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxyurl", 8080));
            HttpURLConnection connection1 = (HttpURLConnection) siteURL.openConnection(proxy1);
            connection1.setRequestMethod("GET");
            connection1.setConnectTimeout(3000);
            connection1.setRequestProperty( "http.proxyUser", "user" );
            connection1.setRequestProperty( "http.proxyPassword", "some password" );
            connection1.connect();
            code = connection1.getResponseCode();
            System.out.println(url + "\t\t Connection is using proxy with port 8080 :" + connection1.usingProxy());

            if (code == 200) {
                result = "-> Green <-\t" + "Code: " + code;
                ;
            } else {
                result = "-> Yellow <-\t" + "Code: " + code;
            }*/

            Proxy proxy2 = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy url", 8443));
            HttpURLConnection connection2 = (HttpURLConnection) siteURL.openConnection(proxy2);
            connection2.setRequestMethod("GET");
            connection2.setConnectTimeout(3000);
            connection2.setRequestProperty( "http.proxyUser", "user" );
            connection2.setRequestProperty( "http.proxyPassword", "some pass" );
            connection2.connect();
            code = connection2.getResponseCode();

            System.out.println(url + "\t\t Connection is using proxy with port 8443:" + connection2.usingProxy());


            if (code == 200) {
                result = "-> Green <-\t" + "Code: " + code;
                ;
            } else {
                result = "-> Yellow <-\t" + "Code: " + code;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "-> Red <-\t" + "Wrong domain - Exception: " + e.getMessage();

        }
        System.out.println(url + "\t\tStatus:" + result);
        return result;
    }
}
