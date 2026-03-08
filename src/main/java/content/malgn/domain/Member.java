package content.malgn.domain;

import content.malgn.domain.baseentity.BaseTimeEntity;
import content.malgn.enums.MemberRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 정보
 */
@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; //PK

    @Column(name = "email", unique = true, nullable = false)
    private String email; //이메일

    @Column(name = "password" , nullable = false)
    private String password; //비밀번호

    @Column(name = "name", nullable = false)
    private String name; //이름

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private MemberRole role;//역할

}
