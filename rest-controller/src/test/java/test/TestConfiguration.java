package test;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import test.lb.MyPing;

import com.google.common.collect.Lists;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;

@Configuration
public class TestConfiguration {
  
  static Server       SERVER1 = new Server("localhost", 8080);
  static Server       SERVER2 = new Server("localhost", 8081);
  
  static List<Server> SERVERS = Lists.newArrayList(SERVER1, SERVER2);
  
  /**
   * 
   * @return
   */
  @Bean BaseLoadBalancer loadBalancer() {
    BaseLoadBalancer loadBalancer = LoadBalancerBuilder.newBuilder().buildFixedServerListLoadBalancer(SERVERS);
    loadBalancer.setPingInterval(2);
    MyPing ping = new MyPing(false, "/health");
    ping.setLoadBalancer(loadBalancer);
    
    loadBalancer.setPing(ping);
    loadBalancer.setRule(new RoundRobinRule(loadBalancer));
    loadBalancer.setMaxTotalPingTime(1);
    loadBalancer.setPingInterval(2);
    return loadBalancer;
  }
  
  @Bean RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean HttpClient httpClient() {
    return HttpClientBuilder.create().build();
  }
}
