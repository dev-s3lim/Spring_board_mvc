package com.beyond.basic.common.service;

import com.beyond.basic.author.domain.Author;
import com.beyond.basic.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Session Login 흐름
// 1. 화면에서 doLogin 메서드 호출
// 2. Spring에서 사전에 구현된 doLogin 메서드에서 loadUserByUsername 메서드를 실행
// 3. loadUserByUsername 메서드에서 return 받는 User 객체를 사용하여, 사용자가 입력한 email/password와 비교
// 4. 비교하여, 검증이 완료되면 (일치하면) Authentication 객체로 저장 및 ***세션 ID 발급***
// 5. 사용자는 세션 ID를 발급받고(쿠키), 서버는 세변 ID를 저장하여 (메모리), API 요청 시 마다, 세션 ID를 검증한다.

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final AuthorRepository authorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author = authorRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("!!! AUTHOR NOT FOUND !!!"));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + author.getRole()));
        return new User(author.getEmail(), author.getPassword(), authorities);
    }
}
