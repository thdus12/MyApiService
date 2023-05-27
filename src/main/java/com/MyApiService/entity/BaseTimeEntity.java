package com.MyApiService.entity;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.Getter;

/**
 * 생성 시간과 수정 시간을 자동으로 기록하기 위한 기반 엔티티 클래스
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    /**
     * 엔티티가 생성되는 시점에 자동으로 시간을 기록하기 위한 어노테이션
     */
    @CreatedDate
    private LocalDateTime registerTime;

    /**
     * 엔티티가 수정되는 시점에 자동으로 시간을 기록하기 위한 어노테이션
     */
    @LastModifiedDate
    private LocalDateTime updateTime;
}