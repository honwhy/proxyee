package com.github.monkeywie.proxyee;

import cn.hutool.http.HttpRequest;
import com.github.monkeywie.proxyee.server.HttpProxyServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.CompletionStage;

public class TestSocksProxyServer {
    private HttpProxyServer httpProxyServer;
    private final static int port = 1080;

    @Before
    public void before() {
        httpProxyServer = new HttpProxyServer();
        CompletionStage<Void> voidCompletionStage = httpProxyServer.startAsync(port);
        //voidCompletionStage.exceptionally(throwable -> {throwable.printStackTrace(); return null;});
    }

    @Test
    public void test_http_proxy() {
        String url = "https://baidu.com";
        HttpRequest httpRequest = new HttpRequest(url);
        httpRequest.setHttpProxy("127.0.0.1", port);
        String body = httpRequest.execute().body();
        System.out.println(body);
    }

    @Test
    public void test_socks_proxy() {
        String url = "https://baidu.com";
        HttpRequest httpRequest = new HttpRequest(url);
        httpRequest.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", port)));
        String body = httpRequest.execute().body();
        System.out.println(body);
    }

    @After
    public void teardown() {
        if (httpProxyServer != null) {
            httpProxyServer.close();
        }
    }
}
