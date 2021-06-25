package spring.io.rest.recipes.security.jwt;

import spring.io.rest.recipes.security.UserPrincipal;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenUtil {

    String extractTokenAndGetSubject(HttpServletRequest request);

    String extractToken(HttpServletRequest request);

    boolean validate(String token);

    String getSubjectFromToken(String token);

    String generateToken(UserPrincipal userPrincipal);

}
