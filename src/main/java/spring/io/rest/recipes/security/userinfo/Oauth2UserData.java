package spring.io.rest.recipes.security.userinfo;

public interface Oauth2UserData {
    public String getId();

    public String getName();

    public String getEmail();

    public String getImageUrl();
}
