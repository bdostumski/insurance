package com.insurance.application.utils;

import com.insurance.application.models.UserInfo;
import com.insurance.application.security.jwt.JwtTokenUtil;
import com.insurance.application.services.UserInfoService;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public class Validator {

    public static UserInfo loadUser(Principal principal, UserInfoService userInfoService) {
        if (principal != null) {
            return userInfoService.getByEmail(principal.getName());
        } else {
            return new UserInfo();
        }
    }

    public static String getUserEmail(HttpServletRequest request, JwtTokenUtil jwtTokenUtil) {
        final String requestTokenHeader = request.getHeader("Authorization");

        String email = null;
        String jwtToken;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")
        ) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                email = jwtTokenUtil.getEmailFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        }
        return email;
    }
}
