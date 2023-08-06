package com.dfoff.demo.oauth;

import com.dfoff.demo.enums.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();
}