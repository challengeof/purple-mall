package net.caidingke.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import net.caidingke.common.Constants;
import net.caidingke.common.config.PurpleProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author bowen
 */
@Component
public class TokenProvider implements Serializable, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final long serialVersionUID = -61900740052154300L;
    private final StringRedisTemplate stringRedisTemplate;

    private Clock clock = DefaultClock.INSTANCE;

    private final PurpleProperties purpleProperties;

    private String secret;

    private long tokenValidityInMilliseconds;

    @Autowired
    public TokenProvider(StringRedisTemplate stringRedisTemplate,
            PurpleProperties purpleProperties) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.purpleProperties = purpleProperties;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.secret = purpleProperties.getAuth().getTokenSecret();
        this.tokenValidityInMilliseconds =
                1000 * purpleProperties.getAuth().getTokenValidityInSeconds();

    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token) {
        Objects.requireNonNull(token);
        final String username = getUsernameFromToken(token);
        String fromRedisToken = stringRedisTemplate.opsForValue().get(generateKey(username));
        return Objects.equals(token, fromRedisToken);
        // final Date expiration = getExpirationDateFromToken(token);
        //    return (username.equals(user.getUsername())
        //        && !isTokenExpired(token)
        //        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + tokenValidityInMilliseconds);
    }

    public static String generateKey(String username) {
        return String.format("%s:%s:%s", Constants.APPLICATION_NAME, Constants.PREFIX_JWT_TOKEN,
                username);
    }
}
