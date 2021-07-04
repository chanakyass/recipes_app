package spring.io.rest.recipes.security.jwt;

import spring.io.rest.recipes.exceptions.ApiAccessException;
import spring.io.rest.recipes.security.UserPrincipal;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenUtil {

    String extractTokenAndGetSubject(HttpServletRequest request) throws ApiAccessException;

    String extractToken(HttpServletRequest request);

    void validate(String token) throws ApiAccessException;

    String getSubjectFromToken(String token);

    String generateToken(UserPrincipal userPrincipal) throws ApiAccessException;

}
