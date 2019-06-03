package sk.fri.uniza.auth;

import javax.ws.rs.FormParam;

public class OAuth2Client {

    private String clientId;
    private String redirectUri;
    private Long userId;

    public OAuth2Client(String clientId, String redirectUri, String clientSecrete, Long userId) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.clientSecrete = clientSecrete;
        this.userId = userId;
    }

    private String clientSecrete;

    public String getClientSecrete() {
        return clientSecrete;
    }

    public void setClientSecrete(String clientSecrete) {
        this.clientSecrete = clientSecrete;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
