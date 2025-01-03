package com.global.base;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuditorDto {

    private String createdBy;
    
    private LocalDateTime createdDate;
    
    private String lastModifiedBy;
    
    private LocalDateTime lastModifiedDate;

}
