DROP FUNCTION IF EXISTS getWeekOfYear;
DELIMITER //
CREATE FUNCTION getWeekOfYear(inDate DATE)
RETURNS INT
DETERMINISTIC
NO SQL
BEGIN
    DECLARE MonthOfYear INT;
    DECLARE WeekOfMonth INT;
    
    SET MonthOfYear = MONTH(inDate);
    SET WeekOfMonth = getWeekOfMonth(inDate);
    
    RETURN (MonthOfYear-1)*4 + WeekOfMonth;
END; //

DELIMITER ;