package com.MyApiService.dto.member;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String email;
    private String password;
    
    @Builder
    public MemberResponseDto(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "MemberListDto ["
        		+ "id=" + id
        		+ ", email=" + email 
        		+ ", password=" + password + "]";
    }
}