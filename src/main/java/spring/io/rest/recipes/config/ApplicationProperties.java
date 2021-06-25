package spring.io.rest.recipes.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import spring.io.rest.recipes.enums.Strategy;

@ConfigurationProperties(prefix = "app")
@Component
@Getter
@Setter
public class ApplicationProperties {

    private Security security;
    private OAuth2 oAuth2;

    @Getter
    @Setter
    public static class Security {
        private String secretKey;
        private String publicKey;
        private String privateKey;
        private String issuer;
        private Strategy strategy;
    }

    @Getter
    @Setter
    public static class OAuth2{
        private String authorizedRedirectUri;
    }

    @Bean("applicationProperties")
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
