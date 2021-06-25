package spring.io.rest.recipes.security.algo;

import com.auth0.jwt.algorithms.Algorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import spring.io.rest.recipes.config.ApplicationProperties;
import spring.io.rest.recipes.enums.Strategy;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
@EnableConfigurationProperties(ApplicationProperties.class)
public class AlgorithmStrategy {

    private final ApplicationProperties applicationProperties;

    @Autowired
    public AlgorithmStrategy(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public Algorithm getAlgorithm(Strategy strategy) {
        Algorithm algorithm = null;
        try {
            switch (strategy) {
                case SYMMETRIC_ENCRYPTION:
                case AUTO:
                    algorithm = Algorithm.HMAC256(applicationProperties.getSecurity().getSecretKey());
                    break;
                case ASYMMETRIC_ENCRYPTION:
                    algorithm = Algorithm.RSA256(getPublicKeyFromString(applicationProperties.getSecurity().getPublicKey()),
                            getPrivateKeyFromString(applicationProperties.getSecurity().getPrivateKey()));
                    break;
            }
        } catch (GeneralSecurityException ex) {
            ex.printStackTrace();
            //throw new ApiAccessException("Problem with encryption");
        }
        return algorithm;
    }

    public RSAPrivateKey getPrivateKeyFromString(String key) throws GeneralSecurityException {

        byte[] encoded = Base64.decodeBase64(key);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) kf.generatePrivate(keySpec);
    }

    public RSAPublicKey getPublicKeyFromString(String key) throws GeneralSecurityException {
        byte[] encoded = Base64.decodeBase64(key);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));
    }

}
