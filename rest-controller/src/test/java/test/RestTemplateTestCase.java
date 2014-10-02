package test;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.Server;

/**
 * 
 * @author ByTel
 * 
 */
public class RestTemplateTestCase extends AbstractLoadBalancerTestCase {

  @Inject
  private RestTemplate restTemplate;

  @Override
  protected Object invoke(Server server, String urlStr) throws Exception {
    return this.restTemplate.getForEntity(urlStr, String.class);
  }

  @Override
  protected int getResponseStatus(Object response) {
    return ((ResponseEntity<?>)response).getStatusCode().value();
  }

  @Override
  protected String getResponseBody(Object response) throws Exception {
    return ((ResponseEntity<?>)response).getBody().toString();
  }
}
