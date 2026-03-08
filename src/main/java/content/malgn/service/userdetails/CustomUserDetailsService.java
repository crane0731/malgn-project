package content.malgn.service.userdetails;

import content.malgn.domain.Member;
import content.malgn.domain.userdetails.CustomUserDetails;
import content.malgn.exception.ErrorMessage;
import content.malgn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 커스텀 유저 디테일 서비스
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.NOT_FOUND_MEMBER));
        return new CustomUserDetails(member);
    }

}
