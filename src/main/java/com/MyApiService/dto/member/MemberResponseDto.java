package com.MyApiService.dto.member;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원 정보 응답 DTO 클래스
 */
@Data
@NoArgsConstructor
public class MemberResponseDto {

	private Long id;           // 사용자의 고유 식별자(ID)
    private String email;      // 사용자의 이메일
    private String password;   // 사용자의 비밀번호

    /**
     * MemberResponseDto의 생성자
     *
     * @param id       사용자의 고유 식별자(ID)
     * @param email    사용자의 이메일
     * @param password 사용자의 비밀번호
     */
    @Builder
    public MemberResponseDto(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    /**
     * 객체를 문자열로 표현하기 위해 재정의된 toString() 메서드
     *
     * @return 객체의 문자열 표현
     */
    @Override
    public String toString() {
        return "MemberResponseDto ["
                + "id=" + id
                + ", email=" + email
                + ", password=" + password
                + "]";
    }
}