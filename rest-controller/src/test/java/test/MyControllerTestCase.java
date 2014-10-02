package test;

import java.io.IOException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
/**
 * 
 * @author ByTel
 *
 */
public class MyControllerTestCase {
  
  /**
   * 
   */
  @Test public void processOK() {
    String result = new RestTemplate().getForObject("http://localhost:8080/ctrl/process", String.class);
    Assert.assertThat(result, CoreMatchers.is("ok"));
  }
  
  /**
   * 
   */
  @Test public void getForObjectOK() {
    ResultObject result = new RestTemplate().getForObject("http://localhost:8080/ctrl/required?type=value", ResultObject.class);
    Assert.assertThat(result.isResult(), CoreMatchers.is(true));
  }
  
  @Test(expected=HttpClientErrorException.class) public void getForObjectKO() {
    new RestTemplate().getForObject("http://localhost:8080/ctrl/required", ResultObject.class);
  }
  
  /**
   * 
   */
  @Test public void getForEntityKO() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(new ResponseErrorHandler() {
      
      @Override
      public boolean hasError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
          return false;
        }
        return true;
      }
      
      @Override
      public void handleError(ClientHttpResponse response) throws IOException {
        // TODO Auto-generated method stub
        
      }
    });
    ResponseEntity<ResultObject> resultEntity = restTemplate.getForEntity("http://localhost:8080/ctrl/required", ResultObject.class);
    Assert.assertThat(resultEntity.getStatusCode(), CoreMatchers.is(HttpStatus.BAD_REQUEST));
    Assert.assertThat(resultEntity.getBody(), CoreMatchers.nullValue());
  }
}
