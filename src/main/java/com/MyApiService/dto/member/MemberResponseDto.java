package com.MyApiService.dto.member;

import com.MyApiService.security.Role;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String email;
    private String password;
    private Role role;
    
    @Builder
    public MemberResponseDto(Long id, String email, String password,Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "MemberListDto ["
        		+ "id=" + id
        		+ ", email=" + email 
        		+ ", password=" + password 
        		+ ", role=" + role + "]";
    }
}