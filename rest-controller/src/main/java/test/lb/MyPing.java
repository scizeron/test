package test.lb;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerPing;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.Server;

public class MyPing extends AbstractLoadBalancerPing {

    private PingUrl ping;
    
    /**
     * 
     * @param isSecure
     * @param pingAppendString
     */
    public MyPing(boolean isSecure, String pingAppendString) {
      this(isSecure, pingAppendString, null);
    }
    
    /**
     * 
     * @param isSecure
     * @param pingAppendString
     * @param expectedContent
     */
    public MyPing(boolean isSecure, String pingAppendString, String expectedContent) {
      this.ping = new PingUrl(isSecure, pingAppendString);
      this.ping.setExpectedContent(expectedContent);
    }
    
    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
      /** EMPTY **/
    }

    @Override
    public boolean isAlive(Server server) {
     return this.ping.isAlive(server);
    }
  }
