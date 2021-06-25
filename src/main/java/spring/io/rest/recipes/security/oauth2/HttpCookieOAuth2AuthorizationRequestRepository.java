package spring.io.rest.recipes.security.oauth2;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

enum CookieDetails {
OAUTH2_AUTH_REQUEST("oauth2_auth_request"), OAUTH2_REDIRECT_URI("redirect_uri");
private final String cookieName;
    CookieDetails(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getCookieDetail() {
        return cookieName;
    }
}

public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    private static final int cookieExpireSeconds = 180;
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest httpServletRequest) {
        return CookieUtils.getCookie(httpServletRequest,
                CookieDetails.OAUTH2_AUTH_REQUEST.getCookieDetail())
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            CookieUtils.deleteCookie(request, response, CookieDetails.OAUTH2_AUTH_REQUEST.getCookieDetail());
            CookieUtils.deleteCookie(request, response, CookieDetails.OAUTH2_REDIRECT_URI.getCookieDetail());
            return;
        }

        CookieUtils.addCookie(response, CookieDetails.OAUTH2_AUTH_REQUEST.getCookieDetail(), CookieUtils.serialize(authorizationRequest), cookieExpireSeconds);
        String redirectUriAfterLogin = request.getParameter(CookieDetails.OAUTH2_REDIRECT_URI.getCookieDetail());
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtils.addCookie(response, CookieDetails.OAUTH2_REDIRECT_URI.getCookieDetail(), redirectUriAfterLogin, cookieExpireSeconds);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, CookieDetails.OAUTH2_AUTH_REQUEST.getCookieDetail());
        CookieUtils.deleteCookie(request, response, CookieDetails.OAUTH2_REDIRECT_URI.getCookieDetail());
    }
}
