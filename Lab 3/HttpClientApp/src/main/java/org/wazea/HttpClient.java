package org.wazea;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class HttpClient {

    private static final String[] HEADERS = {
            "Access-Control-Allow-Credentials",
            "Access-Control-Allow-Origin",
            "Content-Length",
            "Content-Type",
            "Date",
            "Server",
            "Connection"
    };

    private void printResponse(HttpResponse response) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }

            System.out.println(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printHeaders(HttpResponse response) {
        try {
            for (String it : HEADERS) {
                Header[] header = response.getHeaders(it);
                if (header.length == 0)
                    continue;
                System.out.println(header[0].getName() + " : " + header[0].getValue());
            }

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void httpGet() {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet getRequest = new HttpGet("http://httpbin.org/ip");
            HttpResponse response = client.execute(getRequest);
            printResponse(response);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    void httpHead() {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpHead headRequest = new HttpHead("http://httpbin.org/ip");
            HttpResponse response = client.execute(headRequest);
            printHeaders(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void httpPost() {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost("http://httpbin.org/post");
            postRequest.setHeader("User-Agent", "Client");
            postRequest.setEntity(new StringEntity("Test data"));
            HttpResponse response = client.execute(postRequest);
            printResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void httpPut() {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPut putRequest = new HttpPut("http://httpbin.org/put");
            putRequest.setHeader("User-Agent", "Client");
            putRequest.setEntity(new StringEntity("Test data"));
            HttpResponse response = client.execute(putRequest);
            printResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void httpPatch() {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPatch patchRequest = new HttpPatch("http://httpbin.org/patch");
            patchRequest.setHeader("User-Agent", "Client");
            patchRequest.setEntity(new StringEntity("Test data"));
            HttpResponse response = client.execute(patchRequest);
            printResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void httpDelete() {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpDelete deleteRequest = new HttpDelete("http://httpbin.org/delete");
            deleteRequest.setHeader("User-Agent", "Client");
            HttpResponse response = client.execute(deleteRequest);
            printResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
