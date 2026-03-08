package content.malgn.domain;

import content.malgn.domain.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 토큰 (리프레쉬)
 */
@Entity
@Table(name = "token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id" , nullable = false)
    private Member member; //회원 아이디

    @Column(name = "refresh_token" , nullable = false)
    private String refreshToken;//리프레쉬 토큰

    /**
     * [생성 메서드]
     * @param member 회원
     * @param refreshToken 리프레쉬 토큰
     * @return Token
     */
    public static Token create(Member member, String refreshToken) {
        Token token = new Token();
        token.member = member;
        token.refreshToken = refreshToken;
        return token;
    }


}
