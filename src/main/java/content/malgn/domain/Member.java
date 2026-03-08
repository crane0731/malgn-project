package content.malgn.domain;

import content.malgn.domain.baseentity.BaseTimeEntity;
import content.malgn.enums.DeleteStatus;
import content.malgn.enums.MemberRole;
import content.malgn.exception.ErrorMessage;
import content.malgn.exception.MemberCustomException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private MemberRole role;//회원 권한

    @Enumerated(EnumType.STRING)
    @Column(name = "delete_status" , nullable = false)
    private DeleteStatus deleteStatus; //삭제 상태

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;//탈퇴일

    /**
     * [생성 메서드]
     * @param email 이메일
     * @param password 패스워드
     * @param name 이름
     * @param role 권한
     * @return Member
     */
    public static Member create(String email, String password, String name, MemberRole role) {
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.name = name;
        member.role = role;
        member.deleteStatus = DeleteStatus.UNDELETED;
        member.deletedDate = null;
        return member;
    }

    /**
     * [SOFT DELETE]
     */
    public void softDelete() {
        if (this.deleteStatus == DeleteStatus.DELETED) {
            throw new MemberCustomException(ErrorMessage.ALREADY_DELETED);
        }

        this.deleteStatus = DeleteStatus.DELETED;
        this.deletedDate = LocalDateTime.now();
        this.email = "DELETED_" + this.email;
        this.name = "DELETED_" + this.name;
    }

}
