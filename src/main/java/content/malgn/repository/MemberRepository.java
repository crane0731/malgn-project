package content.malgn.repository;

import content.malgn.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 회원 레파지토리
 */
public interface MemberRepository extends JpaRepository<Member, Integer> {

    /**
     * 이메일로 회원 조회
     * @param email 이메일
     * @return  Optional<Member>
     */
    Optional<Member> findByEmail(String email);

}
