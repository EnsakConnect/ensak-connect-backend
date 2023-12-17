package com.ensak.connect.config.security;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 86400000; // 5 days expressed in milliseconds

    public static final long REFRESH_EXPIRATION = 604800000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/password-reset",
            "/api/v1/auth/password-reset/verify",
            "/api/v1/auth/activate",
            "/api/v1/health**",
            "/api/v1/job-posts/**",

            // Swagger URLS
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };
}
