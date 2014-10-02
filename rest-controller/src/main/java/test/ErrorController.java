package test;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler{

//  @ExceptionHandler(value=Exception.class)
//  public ResponseEntity<Object> defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
//    HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE; // mapping en fonction de l'exception, fait dans le handler par defaut, pour l'ex
//    return handleExceptionInternal(e,  new ErrorInfo(request.getRequestURL().toString(), e),  new HttpHeaders(), status, new ServletWebRequest(request));
//  }
}
