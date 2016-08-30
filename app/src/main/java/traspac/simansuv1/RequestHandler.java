package traspac.simansuv1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by UI-21 on 12/08/2016.
 */
public class RequestHandler {

    public String sendGetRequest(String requestUrl)
    {
        StringBuilder sb =new StringBuilder();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String s;
            while ((s=br.readLine()) != null) {
                sb.append(s+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
