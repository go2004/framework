package test;

/**
 * Created by Administrator on 2016/9/29.
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Async;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.concurrent.FutureCallback;
public class QuickStart {

    public static void main(String[] args) throws Exception {

        // Use pool of two threads
        ExecutorService threadpool = Executors.newFixedThreadPool(2);
        Async async = Async.newInstance().use(threadpool);

        Request[] requests = new Request[] {
                Request.Get("http://www.google.com/"),
                Request.Get("http://www.yahoo.com/"),
                Request.Get("http://www.apache.com/"),
                Request.Get("http://www.apple.com/")
        };


        Queue<Future<Content>> queue = new LinkedList<Future<Content>>();
        // Execute requests asynchronously
        for (final Request request: requests) {
            Future<Content> future = async.execute(request, new FutureCallback<Content>() {

                @Override
                public void failed(final Exception ex) {
                    System.out.println(ex.getMessage() + ": " + request);
                }

                @Override
                public void completed(final Content content) {
                    System.out.println("Request completed: " + request);
                }

                @Override
                public void cancelled() {
                }

            });
            queue.add(future);
        }

        while(!queue.isEmpty()) {
            Future<Content> future = queue.remove();
            try {
                future.get();
            } catch (ExecutionException ex) {
            }
        }
        System.out.println("Done");
        threadpool.shutdown();


       /* CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet("http://www.baidu.com/get");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager.
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity1);
            } finally {
                response1.close();
            }

            HttpPost httpPost = new HttpPost("http://www.baidu.com/post");
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("username", "vip"));
            nvps.add(new BasicNameValuePair("password", "secret"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            try {
                System.out.println(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }
        } finally {
            httpclient.close();
        }
        */
    }

}
