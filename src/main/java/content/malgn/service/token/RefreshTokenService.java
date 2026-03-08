package content.malgn.service.token;

import content.malgn.config.jwt.TokenProvider;
import content.malgn.domain.Member;
import content.malgn.domain.Token;
import content.malgn.enums.TokenType;
import content.malgn.exception.ErrorMessage;
import content.malgn.exception.JwtCustomException;
import content.malgn.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

/**
 * 리프레쉬 토큰 서비스
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {


    private final TokenRepository tokenRepository;//토큰 레파지 토리
    private final TokenProvider tokenProvider; //토큰 프로바이더

    private final Duration refreshTokenValidity = Duration.ofMinutes(120); //리프레쉬 토큰 유효 시간(120분)


    /**
     * [조회]
     * 회원 아이디(PK) 값을 통해 조회
     * @param memberId 회원 아이디
     * @return List<Token>
     */
    public List<Token>findByMemberId(Long memberId) {
        return tokenRepository.findByMemberId(memberId);
    }

    /**
     * [삭제]
     * 회원 아이디 (PK) 값을 통해 삭제
     * @param memberId 회원 아이디
     */
    @Transactional
    public void delete (Long memberId) {
        tokenRepository.deleteByMemberId(memberId);
    }


    /**
     * [조회]
     * 리프레쉬 토큰 조회
     * @param refreshToken 리프레쉬 토큰
     * @return RefreshToken
     */
    public Token findByRefreshToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new JwtCustomException(ErrorMessage.NOT_FOUND_REFRESH_TOKEN));
    }


    /**
     * 리프레쉬 토큰 생성 , 저장
     * @param member 회원
     */
    @Transactional
    public String createRefreshToken(Member member) {

        //리프레쉬 토큰 생성
        String token = tokenProvider.generateToken(member, refreshTokenValidity, TokenType.REFRESH);

        //리프레쉬 토큰 저장
        tokenRepository.save(Token.create(member, token));

        return token;

    }

}
