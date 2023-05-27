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

/**
 * 회원 정보를 담고 있는 엔티티 클래스
 */
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "member")
public class MemberEntity extends BaseTimeEntity implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;                     // 회원의 고유 식별자(ID)
	private String email;                // 회원의 이메일
	private String pwd;                  // 회원의 비밀번호
	private LocalDateTime lastLoginTime; // 회원의 마지막 로그인 시간
	@Enumerated(EnumType.STRING)
	private Role role;                   // 회원의 권한(Role)
	
	/**
	 * MemberEntity의 생성자
	 *
	 * @param id             회원의 고유 식별자(ID)
	 * @param email          회원의 이메일
	 * @param pwd            회원의 비밀번호
	 * @param lastLoginTime  회원의 마지막 로그인 시간
	 * @param role           회원의 권한(Role)
	 */
	@Builder
	public MemberEntity(Long id, String email, String pwd, LocalDateTime lastLoginTime, Role role) {
	    this.id = id;
	    this.email = email;
	    this.pwd = pwd;
	    this.lastLoginTime = lastLoginTime;
	    this.role = role;
	}
	
	/**
	 * 회원이 갖고 있는 권한을 반환하는 메서드
	 *
	 * @return 회원의 권한(Set<GrantedAuthority>)
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    Set<GrantedAuthority> roles = new HashSet<>();
	    roles.add(new SimpleGrantedAuthority(role.getRole()));
	    return roles;
	}
	
	/**
	 * UserDetails 인터페이스의 getUsername 메서드를 구현
	 *
	 * @return 회원의 이메일
	 */
	@Override
	public String getUsername() {
	    return this.email;
	}
	
	/**
	 * UserDetails 인터페이스의 getPassword 메서드를 구현
	 *
	 * @return 회원의 비밀번호
	 */
	@Override
	public String getPassword() {
	    return this.pwd;
	}

    /**
     * 계정이 만료되지 않았는지 리턴한다. (true: 만료 안됨)
     *
     * @return 계정의 만료 여부
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정이 잠겨있는지 않았는지 리턴한다. (true: 잠기지 않음)
     *
     * @return 계정의 잠금 여부
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 비밀번호가 만료되지 않았는지 리턴한다. (true: 만료 안됨)
     *
     * @return 비밀번호의 만료 여부
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정이 활성화(사용 가능)인지 리턴한다. (true: 활성화)
     *
     * @return 계정의 활성화 여부
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}