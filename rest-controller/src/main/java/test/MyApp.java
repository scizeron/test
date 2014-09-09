package test;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class MyApp {

  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    final SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder();

    springApplicationBuilder.sources(AppConfig.class);
    springApplicationBuilder.showBanner(true);
    springApplicationBuilder.logStartupInfo(true);
    springApplicationBuilder.run(args);
  }
}
