package com.MyApiService.entity.member;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.MyApiService.entity.BaseTimeEntity;
import com.MyApiService.security.Role;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

// id를 기준으로 equals와 hashCode 메소드를 자동 생성
@EqualsAndHashCode(of = {"id"})
// 기본 생성자를 자동 생성하고, 접근 제한자를 protected로 설정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 멤버 변수에 대한 getter 메소드를 자동 생성
@Getter
// 이 클래스를 JPA 엔티티로 표시
@Entity(name = "member")
public class MemberEntity extends BaseTimeEntity implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	// 멤버의 고유 식별자
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email; // 멤버의 이메일
	private String pwd;   // 멤버의 비밀번호
	private LocalDateTime lastLoginTime; // 멤버의 마지막 로그인 시간
	@Enumerated(EnumType.STRING)
	private Role role;
	
	// 빌더 패턴을 이용한 객체 생성을 위한 생성자
	@Builder
	public MemberEntity(Long id, String email, String pwd, LocalDateTime lastLoginTime, Role role) {
	    this.id = id;
	    this.email = email;
	    this.pwd = pwd;
	    this.lastLoginTime = lastLoginTime;
	    this.role = role;
	}
	
	// 계정이 갖고있는 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(role.getRole()));
        return roles;
    }
	
	// UserDetails 인터페이스의 getUsername 메소드를 구현
	@Override
	public String getUsername() {
	    return this.email;
	}
	
	// UserDetails 인터페이스의 getPassword 메소드를 구현
	@Override
	public String getPassword() {
	    return this.pwd;
	}

    // 계정이 만료되지 않았는지 리턴 (true: 만료 안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있는지 않았는지 리턴. (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 리턴한다. (true: 만료 안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용가능)인지 리턴 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }
}