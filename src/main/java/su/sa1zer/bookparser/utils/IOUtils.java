package su.sa1zer.bookparser.utils;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@UtilityClass
public class IOUtils {

    public static String readInputStream(InputStream inputStream) {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            StringBuilder sb = new StringBuilder();

            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }

            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static URLConnection newConnection(String url) throws IOException {
        return newConnection(new URL(url));
    }

    public static URLConnection newConnection(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        if (connection instanceof HttpURLConnection) {
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)"); // Fix for stupid servers
        } else {
            connection.setUseCaches(false);
        }
        connection.setDoInput(true);
        connection.setDoOutput(false);
        return connection;
    }
}
