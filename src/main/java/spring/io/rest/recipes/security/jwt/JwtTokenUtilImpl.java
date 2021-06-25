package spring.io.rest.recipes.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import spring.io.rest.recipes.config.ApplicationProperties;
import spring.io.rest.recipes.security.UserPrincipal;
import spring.io.rest.recipes.security.algo.AlgorithmStrategy;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@EnableConfigurationProperties(ApplicationProperties.class)
public class JwtTokenUtilImpl implements JwtTokenUtil {

    private final ApplicationProperties applicationProperties;
    private final AlgorithmStrategy algorithmStrategy;

    @Autowired
    public JwtTokenUtilImpl(ApplicationProperties applicationProperties, AlgorithmStrategy algorithmStrategy) {
        this.applicationProperties = applicationProperties;
        this.algorithmStrategy = algorithmStrategy;
    }

    @Override
    public boolean validate(String token) {

        try {
            JWTVerifier verifier = JWT.require(algorithmStrategy.getAlgorithm(applicationProperties.getSecurity().getStrategy()))
                    .withIssuer(applicationProperties.getSecurity().getIssuer())
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            return false;
        }
    }

    public String generateToken(UserPrincipal userPrincipal) {
        String key = userPrincipal.getUsername();
        String token = "";
        try {
            token = JWT.create()
                    .withSubject(+userPrincipal.getId()+":"
                            +userPrincipal.getUsername())
                    .withIssuer(applicationProperties.getSecurity().getIssuer())
                    .sign(algorithmStrategy.getAlgorithm(applicationProperties.getSecurity().getStrategy()));
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
        }

        return token;

    }

    @Override
    public String getSubjectFromToken(String token) {

        DecodedJWT decodedJWT = JWT.decode(token);

        return decodedJWT.getSubject();


    }

    @Override
    public String extractTokenAndGetSubject(HttpServletRequest request) {

        String token = Optional.of(extractToken(request)).orElseThrow(()->
                new IllegalStateException("Empty Token")
        );
        return getSubjectFromToken(token);
    }

    @Override
    public String extractToken(HttpServletRequest request) {

        // Get authorization header and validate
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return (header == null || header.isEmpty() || !header.startsWith("Bearer ")) ?
                null : header.split(" ")[1].trim();

    }
}