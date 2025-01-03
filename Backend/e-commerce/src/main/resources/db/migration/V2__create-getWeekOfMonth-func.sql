DROP FUNCTION IF EXISTS getWeekOfMonth;
DELIMITER //
CREATE FUNCTION getWeekOfMonth(inDate DATE)
RETURNS INT
DETERMINISTIC
NO SQL
BEGIN
    DECLARE DayOfMonth INT;
    DECLARE WeekOfMonth INT;
    
    SET DayOfMonth = DAY(inDate);
    SET WeekOfMonth = floor((DayOfMonth - 1) / 7) + 1;
    
    IF WeekOfMonth > 4 THEN SET WeekOfMonth = 4;
    END IF;
    
    RETURN WeekOfMonth;
END; //

DELIMITER ;