package search.manager.core;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@RequiredArgsConstructor
public class SearchMessageSourceConfig {

  @Bean
  public ReloadableResourceBundleMessageSource searchMessageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames(
        "classpath:/messages/sample"
    );
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Bean(name = "certificateMessages")
  public MessageSourceAccessor certificateMessages() {
    return new MessageSourceAccessor(searchMessageSource());
  }
}
