package test;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public abstract class AbstractLoadBalancerTestCase {

  @Inject
  protected BaseLoadBalancer   loadBalancer;

  protected static final int   EXECUTIONS   = 100;

  protected static final long  WAITING_TIME = 100;

  protected AtomicInteger      failure      = new AtomicInteger(0);

  private static final String  REQUEST_URI  = "/ctrl/process";

  private static final boolean SECURE       = false;

  /**
   * 
   * @param status
   * @param body
   */
  protected void assertResponse(int index, String urlStr, int status, String body) throws Exception {
    Assert.assertThat(status, CoreMatchers.is(200));
    Assert.assertThat(body, CoreMatchers.is("ok"));
    System.out.println("urlStr(" + index + "): " + urlStr + " OK");
    Thread.sleep(WAITING_TIME);
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  protected String getUrl(boolean isSecure) throws Exception {
    return (isSecure ? "https://" : "http://") + this.loadBalancer.chooseServer().getHostPort() + "/ctrl/process";
  }

  /**
   * 
   * @param server
   * @param urlStr
   * @return
   */
  protected abstract Object invoke(Server server, String urlStr) throws Exception;

  /**
   * 
   * @param response
   * @return
   */
  protected abstract int getResponseStatus(Object response);

  /**
   * 
   */
  protected void terminateExecution() {
    /** EMPTY **/
  }

  /**
   * 
   * @param response
   * @return
   */
  protected abstract String getResponseBody(Object response) throws Exception;

  @Test
  public void process() throws Exception {
    AtomicInteger failure = new AtomicInteger(0);
    String urlStr = null;
    Server server = null;
    for (int i = 0; i < EXECUTIONS; i++) {
      try {
        server = this.loadBalancer.chooseServer();
        urlStr = (SECURE ? "https://" : "http://") + server.getHostPort() + REQUEST_URI;
        Object response = invoke(server, urlStr);
        assertResponse(i, urlStr, getResponseStatus(response), getResponseBody(response));
      } catch (Exception e) {
        failure.incrementAndGet();
        System.err.println("urlStr(" + i + "): " + urlStr + " KO, " + e.getMessage());
      } finally {
        terminateExecution();
      }
    }

    System.out.println("-------------------------------------------");
    System.out.println("- executions : " + EXECUTIONS);
    System.out.println("- failures   : " + failure.get());
    System.out.println("-------------------------------------------");
  }

}
