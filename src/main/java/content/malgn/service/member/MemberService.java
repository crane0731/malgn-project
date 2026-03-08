package content.malgn.service.member;

import content.malgn.domain.Member;
import content.malgn.domain.Token;
import content.malgn.domain.userdetails.CustomUserDetails;
import content.malgn.dto.member.LoginRequestDto;
import content.malgn.dto.member.MemberInfoResponseDto;
import content.malgn.dto.member.SignUpRequestDto;
import content.malgn.dto.token.TokenResponseDto;
import content.malgn.enums.MemberRole;
import content.malgn.exception.ErrorMessage;
import content.malgn.exception.MemberCustomException;
import content.malgn.repository.MemberRepository;
import content.malgn.service.token.RefreshTokenService;
import content.malgn.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 회원 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;//회원 레파지토리
    private final RefreshTokenService refreshTokenService;//리프레쉬 토큰 서비스
    private final TokenService tokenService;//토큰 서비스

    private final BCryptPasswordEncoder bCryptPasswordEncoder; //패스워드 인코더
    private final AuthenticationManager authenticationManager; //시큐리티 인증 매니저


    /**
     * [서비스 로직]
     * 현재 스프링시큐리티에 로그인된 회원의 정보를 가져오기
     * @return Member
     */
    public Member getLoginMember() {

        //시큐리티 컨텍스트 홀더에서 인증 객체 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        String email = authentication.getName();

        return findByEmail(email);

    }

    /**
     * [서비스 로직]
     * 회원 가입
     * @param dto 회원가입 요청 DTO
     */
    @Transactional
    public void signup(SignUpRequestDto dto){

        //이메일 검증
        if (memberRepository.existsByEmail(dto.getEmail())){
            throw new MemberCustomException(ErrorMessage.DUPLICATED_EMAIL);
        }

        //비밀번호 검증
        if(!dto.getPassword().equals(dto.getPasswordCheck())){
            throw new MemberCustomException(ErrorMessage.PASSWORD_MISMATCH);
        }

        //패스워드 인코딩
        String encoded = bCryptPasswordEncoder.encode(dto.getPassword());

        //회원 생성
        Member member = Member.create(dto.getEmail(), encoded, dto.getName(), MemberRole.MEMBER);

        //저장
        save(member);

    }

    /**
     * [서비스 로직]
     * 회원 로그인
     * @param dto 로그인 요청 DTO
     * @return  TokenResponseDto  토큰 응답 DTO
     */
    @Transactional
    public TokenResponseDto login(LoginRequestDto dto){

        //스프링 시큐리티 수동 로그인
        Member member = securityLogin(dto);

        //JWT 토큰 생성 , 반환
        return makeToken(member);

    }

    /**
     * [서비스 로직]
     * 로그 아웃
     */
    @Transactional
    public void logout(){

        //현재 로그인한 회원 조회
        Member member = getLoginMember();

        //현재 로그인한 회원의 모든 리프레쉬 토큰 조회
        List<Token> tokens = refreshTokenService.findByMemberId(member.getId());

        //이미 로그아웃한 회원인지 검증
        if(tokens.isEmpty()){
            throw new MemberCustomException(ErrorMessage.ALREADY_LOGOUT_MEMBER);
        }

        //리프레쉬 토큰 삭제
        refreshTokenService.delete(member.getId());

    }


    /**
     * [서비스 로직]
     * 회원 자신의 상세 정보 조회
     * @return MemberInfoResponseDto
     */
    public MemberInfoResponseDto getMyInfo(){

        //현재 로그인한 회원 조회
        Member member = getLoginMember();

        return memberRepository.findMemberInfo(member.getId()).orElseThrow(()->new MemberCustomException(ErrorMessage.NOT_FOUND_MEMBER));

    }


    /**
     * [서비스 로직]
     * 회원 탈퇴
     */
    @Transactional
    public void withdraw(){
        //현재 로그인한 회원 조회
        Member member = getLoginMember();

        //회원 삭제
        memberRepository.delete(member);

        //리프레쉬 토큰 삭제
        refreshTokenService.delete(member.getId());
    }


    /**
     * [조회]
     * PK 값으로 조회
     * @param id PK
     * @return  Member
     */
    public Member getById(Long id){
        return memberRepository.
                findById(id).orElseThrow(()->new MemberCustomException(ErrorMessage.NOT_FOUND_MEMBER));
    }

    /**
     * [조회]
     * 이메일로 조회
     * @param email 이메일
     * @return Member
     */
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(()->new MemberCustomException(ErrorMessage.NOT_FOUND_MEMBER));
    }


    /**
     * [저장]
     */
    @Transactional
    public void save(Member member){
        memberRepository.save(member);
    }



    //==스프링 시큐리티 수동 로그인==//
    private Member securityLogin(LoginRequestDto dto) {
        //UsernamePasswordAuthenticationToken 생성
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());


        //AuthenticationManager로 인증 시도 (loadUser + password 체크 내부 수행)
        Authentication authentication = authenticationManager.authenticate(authToken);


        //인증 정보 SecurityContext에 저장 (로그인 처리)
        SecurityContextHolder.getContext().setAuthentication(authentication);


        //로그인된 회원 정보 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();


        return findByEmail(userDetails.getUsername());
    }

    //==리프레쉬 토큰 + 엑세스 토큰 생성==//
    private TokenResponseDto makeToken(Member member) {
        //리프레시 토큰 생성
        String refreshToken = refreshTokenService.createRefreshToken(member);

        //엑세스 토큰 생성
        String accessToken = tokenService.createNewAccessToken(refreshToken);

        //응답 DTO 반환
        return TokenResponseDto.create(accessToken, refreshToken);
    }
}
