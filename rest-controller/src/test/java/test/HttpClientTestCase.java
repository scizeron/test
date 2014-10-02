package test;

import javax.inject.Inject;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.netflix.loadbalancer.Server;

/**
 * 
 * @author ByTel
 * 
 */

public class HttpClientTestCase extends AbstractLoadBalancerTestCase {

  @Inject
  private HttpClient                  client;

  private static ThreadLocal<HttpGet> REQUEST = new ThreadLocal<>();

  @Override
  protected int getResponseStatus(Object response) {
    return ((HttpResponse) response).getStatusLine().getStatusCode();
  }

  @Override
  protected String getResponseBody(Object response) throws Exception {
    return EntityUtils.toString(((HttpResponse) response).getEntity());
  }

  @Override
  protected Object invoke(Server server, String urlStr) throws Exception {
    HttpGet request = new HttpGet(urlStr);
    REQUEST.set(request);
    return this.client.execute(new HttpHost(server.getHost(), server.getPort()), request);
  }

  @Override
  protected void terminateExecution() {
    REQUEST.get().abort();
    REQUEST.remove();
  }
}
