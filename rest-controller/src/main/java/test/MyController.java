package test;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/ctrl")
public class MyController implements MyInterface {

  @Override
  @RequestMapping(value="/process", produces={MediaType.APPLICATION_JSON_VALUE})
  public String process() {
    return "ok";
  }
  
  @RequestMapping(value="/required", produces={MediaType.APPLICATION_JSON_VALUE})
  public ResultObject required(@RequestParam(value="type") String type) {
    return new ResultObject("required", true);
  }

}
