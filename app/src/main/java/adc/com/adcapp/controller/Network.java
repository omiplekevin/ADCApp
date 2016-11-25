package adc.com.adcapp.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Place all network related calls in this class
 */
public class Network {

    public static String NetworkRequest(String sourceUrl, String method, Map<String, String> map){
        StringBuilder responseString = new StringBuilder();
        try {
            //throws MalformedURLException
            URL urlSource = new URL(sourceUrl);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlSource.openConnection();
            httpUrlConnection.setRequestMethod(method.toUpperCase(Locale.getDefault()));
            httpUrlConnection.setRequestProperty("Accept-Encoding", "gzip");

            if (method.equals("POST")) {
                StringBuilder params = new StringBuilder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setInstanceFollowRedirects(false);
                httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpUrlConnection.setRequestProperty("charset", "utf-8");
                OutputStreamWriter writer = new OutputStreamWriter(httpUrlConnection.getOutputStream());
                writer.write(params.substring(0, params.length() - 1));
                writer.flush();
            }

            BufferedReader bufferedReader;

            if ("gzip".equals(httpUrlConnection.getContentEncoding())) {
                GZIPInputStream gzis = new GZIPInputStream(httpUrlConnection.getInputStream());
                InputStreamReader reader = new InputStreamReader(gzis);
                bufferedReader = new BufferedReader(reader);
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
            }

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                responseString.append(line);
            }

            httpUrlConnection.getInputStream().close();
            httpUrlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            responseString = new StringBuilder("");
            return responseString.toString();
        }
        return responseString.toString();
    }
}
