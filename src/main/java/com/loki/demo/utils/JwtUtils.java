package com.loki.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * JWTUtils
 *
 * @auto Loki
 */
@Component
public class JwtUtils extends JwtUtil {

    @Override
    public boolean verify(String token, String userId) {
        try {
            String secret = this.fastDepShiroJwtAuthorization.getSecret(userId) == null ? this.fastDepShiroJwtProperties.getSecret() : null;
            assert secret != null;

            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withSubject(userId).build();
            verifier.verify(token);
            return true;
        } catch (Exception var6) {
            return false;
        }
    }

    @Override
    public String getUserId() {
        try {
            DecodedJWT jwt = JWT.decode(SecurityUtils.getSubject().getPrincipal().toString());
            return jwt.getSubject();
        } catch (Exception var2) {
            return null;
        }
    }

    @Override
    public String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (Exception var3) {
            return null;
        }
    }

    @Override
    public String sign(String userId) {
        Date date = new Date(System.currentTimeMillis() + this.fastDepShiroJwtProperties.getExpireTime());
        String secret = this.fastDepShiroJwtAuthorization.getSecret(userId) == null ? this.fastDepShiroJwtProperties.getSecret() : null;

        assert secret != null;

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withSubject(userId).withExpiresAt(date).sign(algorithm);
    }
}
