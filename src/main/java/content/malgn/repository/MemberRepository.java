package content.malgn.repository;

import content.malgn.domain.Member;
import content.malgn.dto.member.MemberInfoResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * 회원 레파지토리
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * 이메일로 회원 조회
     * @param email 이메일
     * @return  Optional<Member>
     */
    Optional<Member> findByEmail(String email);


    /**
     * 이메일로 존재 여부 확인
     * @param email 이메일
     * @return Boolean
     */
    @Query("SELECT CASE WHEN count(m) > 0 THEN TRUE ELSE FALSE END FROM Member m WHERE m.email= :email ")
    Boolean existsByEmail(String email);


    @Query("""
    SELECT new content.malgn.dto.member.MemberInfoResponseDto(
        m.id,
        m.email,
        m.name,
        m.createdDate,
        m.lastModifiedDate,
       (
            SELECT COUNT (c.id)
            FROM Content c
            WHERE c.member.id = m.id and c.deleteStatus = 'UNDELETED'
       )
    )
    FROM Member m
    WHERE m.id= :memberId
    """)
    Optional<MemberInfoResponseDto> findMemberInfo(Long memberId);

}
