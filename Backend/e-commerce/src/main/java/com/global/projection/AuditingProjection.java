package com.global.projection;

import java.time.LocalDateTime;

public interface AuditingProjection {
	
    String getCreatedBy();
    
    LocalDateTime getCreatedDate();
    
    String getLastModifiedBy();
    
    LocalDateTime getLastModifiedDate();

}
