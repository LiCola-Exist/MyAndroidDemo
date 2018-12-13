package com.example.licola.myandroiddemo;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.licola.llogger.LLogger;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;

/**
 * @author LiCola
 * @date 2018/12/10
 */
public class HttpMockTest {

  @Test
  public void testLogin() throws IOException {

    MockWebServer webServer = new MockWebServer();

    webServer.enqueue(
        new MockResponse().setBody("ABC").throttleBody(1, 5000, TimeUnit.MILLISECONDS));

    HttpUrl baseUrl = webServer.url("/test");

    long start = System.nanoTime();
    URLConnection urlConnection = baseUrl.url().openConnection();
    InputStream inputStream = urlConnection.getInputStream();
    int read = inputStream.read();
    LLogger.d(read,read=='A');
    assertEquals('A', read);
    long end = System.nanoTime();
    LLogger.d(NANOSECONDS.toMillis(end - start));
  }
}
