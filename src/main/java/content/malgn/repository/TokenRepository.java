package content.malgn.repository;

import content.malgn.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 토큰 레파지토리
 */
public interface TokenRepository extends JpaRepository<Token, Long> {
    /**
     * 회원 아이디로 조회
     * @param memberId 회원 아이디
     * @return List<Token>
     */
    List<Token> findByMemberId(Long memberId);

    /**
     * 회원 아이디로 삭제
     * @param memberId 회원 아이디
     */
    void deleteByMemberId(Long memberId);

    /**
     * 리프레쉬 토큰으로 조회
     * @param refreshToken 리프레쉬 토큰
     * @return  Optional<Token>
     */
    Optional<Token> findByRefreshToken(String refreshToken);

}
