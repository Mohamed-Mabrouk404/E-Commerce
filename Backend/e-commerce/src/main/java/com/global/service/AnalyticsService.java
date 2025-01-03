package com.global.service;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class AnalyticsService {
	
    public static int getWeekOfYear(LocalDate date) {
    	int weekOfMonth = getWeekOfMonth(date);
    	int month = date.getMonthValue();
    	return weekOfMonth + ((month-1)*4);
    }
	
    public static int getWeekOfMonth(LocalDate date) {
        // Get the day of the month
        int dayOfMonth = date.getDayOfMonth();

        // Calculate the week of the month
        int weekOfMonth = (dayOfMonth - 1) / 7 + 1;
        
        // Ensure the result is between 1 and 4
        if (weekOfMonth > 4) {
            weekOfMonth = 4;
        }
        return weekOfMonth;
    }
    
}
