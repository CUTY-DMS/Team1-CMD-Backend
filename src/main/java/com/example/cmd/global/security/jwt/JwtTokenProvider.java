package com.example.cmd.global.security.jwt;


import com.example.cmd.domain.entity.RefreshToken;
import com.example.cmd.domain.controller.dto.response.TokenResponse;
import com.example.cmd.domain.repository.RefreshTokenRepository;
import com.example.cmd.domain.service.exception.TokenExpiredException;
import com.example.cmd.domain.service.exception.TokenInvalidException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

// 토큰을 생성하고 검증하는 클래스입니다.
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다.
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String accessSecretKey = "cmdproject";

    // 토큰 유효시간 30분
    private long accessTokenValidTime = 30 * 60 * 1000L;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        accessSecretKey = Base64.getEncoder().encodeToString(accessSecretKey.getBytes());
    }

    // JWT 토큰 생성

    public String createAccessToken(String email) {
        Claims claims = Jwts.claims().setSubject(email); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
        //claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();


        /*return TokenResponse.builder().
                accessToken(accessToken)
                .key(email)
                .build();*/
    }

    public TokenResponse createToken(String email){
        return TokenResponse
                .builder()
                .accessToken(createAccessToken(email))
                .key(email)
                .refreshToken(createRefreshToken(email))
                .build();
    }

    private String createRefreshToken(String email) {

        Date now = new Date();

        String refreshToken = Jwts.builder()
                .setSubject(email)
                .claim("type", "refresh")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getRefreshExp() * 1000))//만료시간은
                .signWith(SignatureAlgorithm.HS512, accessSecretKey)
                .compact();

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .email(email)
                        .refreshToken(refreshToken)
                        .expiration(jwtProperties.getRefreshExp())
                        .build());

        return refreshToken;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public TokenResponse reissue(String refreshToken) {

        if(!isRefreshToken(refreshToken))
            throw TokenInvalidException.EXCEPTION;

        String email = getId(refreshToken);

        refreshTokenRepository.findById(email)
                .filter(token -> token.getRefreshToken().equals(refreshToken))
                .map(token -> token.updateExpiration(jwtProperties.getRefreshExp()))
                .orElseThrow(() -> TokenInvalidException.EXCEPTION);

        return TokenResponse.builder()//access토큰은 다시 만들어서 반환하고 리프레쉬는 안만든다
                .accessToken(createAccessToken(email))
                .refreshToken(refreshToken)
                .build();
    }

    private boolean isRefreshToken(String token) {
        return getClaims(token).get("type").equals("refresh");
    }
    private String getId(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(accessSecretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw TokenExpiredException.EXCEPTION;
        } catch (Exception e) {
            throw TokenInvalidException.EXCEPTION;
        }
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser()
                .setSigningKey(accessSecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}