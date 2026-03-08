package content.malgn.service.token;

import content.malgn.config.jwt.TokenProvider;
import content.malgn.domain.Member;
import content.malgn.enums.TokenType;
import content.malgn.exception.ErrorMessage;
import content.malgn.exception.JwtCustomException;
import content.malgn.exception.MemberCustomException;
import content.malgn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * 토큰 서비스
 */
@Service
@RequiredArgsConstructor
public class TokenService {
    private final MemberRepository memberRepository;//회원 레파지토리
    private final TokenProvider tokenProvider;//토큰 프로바이더
    private final RefreshTokenService refreshTokenService;//리프레쉬 토큰 서비스


    /**
     * 리프레쉬 토큰을 통해 새로운 엑세스 토큰을 생성
     * @param refreshToken 리프레쉬 토큰
     * @return String 엑세스 토큰
     */
    public String createNewAccessToken(String refreshToken) throws JwtCustomException {

        //토큰 유효성 검사에 실패하면 예외 발생
        if (!tokenProvider.validRefreshToken(refreshToken)) {
            throw new JwtCustomException(ErrorMessage.INVALID_TOKEN);
        }

        Long memberId = refreshTokenService.findByRefreshToken(refreshToken).getMember().getId();


        Member member = memberRepository.findById(memberId).orElseThrow(()-> new MemberCustomException(ErrorMessage.NOT_FOUND_MEMBER));

        return tokenProvider.generateToken(member, Duration.ofMinutes(30), TokenType.ACCESS);

    }


}
