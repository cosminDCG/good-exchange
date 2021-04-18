package com.platform.exchange.security;

public class SecurityConstants {

    public static final String SECRET = "SecretKey";

    public static final long EXPIRATION_TIME = 864_000_000;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    public static final String SIGN_UP_URL = "/api/users/register";

    public static final String WEB_SOCKET_URL = "/socket/app/chat";

    public static final String APPLICATION_JSON_HEADER = "application/json";

    public static final String ENCODING = "UTF-8";
}
