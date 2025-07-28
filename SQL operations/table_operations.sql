
ALTER USER 'root'@'localhost' IDENTIFIED BY 'Rixs@01xx';
FLUSH PRIVILEGES;
--
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE Applications;
TRUNCATE TABLE Students;
TRUNCATE TABLE Courses;
SET FOREIGN_KEY_CHECKS = 1;

-- insert Students
INSERT INTO Students (name, dob, email, phone, marks, category, status) VALUES
('Ravi Kumar', '2005-07-20', 'ravi.kumar@example.com', '9876543210', 91.5, 'General', 'pending'),
('Sneha Patil', '2005-05-15', 'sneha.patil@example.com', '9876543211', 82.0, 'OBC', 'pending'),
('Amit Desai', '2004-12-30', 'amit.desai@example.com', '9876543212', 74.0, 'SC', 'pending'),
('Priya Joshi', '2005-09-01', 'priya.joshi@example.com', '9876543213', 78.0, 'General', 'pending');
select *from Students;
-- insert Courses
INSERT INTO Courses (course_name, cutoff_marks, available_seats) VALUES
('Computer Science', 85.0, 3),
('Electronics', 75.0, 2),
('Mechanical', 70.0, 2);
select *from Courses;
-- insert Applications
INSERT INTO Applications (student_id, course_id, merit_score) VALUES
(1, 1, 91.5),
(2, 2, 82.0),
(3, 3, 74.0),
(4, 2, 78.0);
select *from Applications;

--Update 
ALTER TABLE Courses
ADD cutoff_general FLOAT NOT NULL DEFAULT 0,
ADD cutoff_obc FLOAT NOT NULL DEFAULT 0,
ADD cutoff_sc FLOAT NOT NULL DEFAULT 0,
ADD cutoff_st FLOAT NOT NULL DEFAULT 0;

UPDATE Courses SET 
    cutoff_general = 85, 
    cutoff_obc = 80, 
    cutoff_sc = 75, 
    cutoff_st = 70 
WHERE course_name = 'Computer Science';

UPDATE Courses SET 
    cutoff_general = 75, 
    cutoff_obc = 70, 
    cutoff_sc = 65, 
    cutoff_st = 60 
WHERE course_name = 'Electronics';

UPDATE Courses SET 
    cutoff_general = 70, 
    cutoff_obc = 65, 
    cutoff_sc = 60, 
    cutoff_st = 55 
WHERE course_name = 'Mechanical';
-- Verification queries (optional)
-- SELECT * FROM Students;
-- SELECT * FROM Courses;
-- SELECT * FROM Applications;
