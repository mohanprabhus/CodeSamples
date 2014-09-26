-- A collection of stored procedures used for convenience


-- Drops a table, independant of whether it already exists or not.
--
-- @param tableName The name of the table to drop.
--  
CREATE OR REPLACE PROCEDURE DROP_TABLE_IF_EXISTS(tableName IN VARCHAR2) IS
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ' || tableName;
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/


-- Drops a sequence, independant of whether it already exists or not.
--
-- @param sequenceName The name of the sequence to drop.
--  
CREATE OR REPLACE PROCEDURE DROP_SEQUENCE_IF_EXISTS(sequenceName IN VARCHAR2) IS
BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE ' || sequenceName;
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -2289 THEN
         RAISE;
      END IF;
END;
/


-- Show tablespaces
SELECT TABLESPACE_NAME FROM dba_tablespaces;

SELECT tablespace_name,
       bytes / (1024*1024) as "Bytes (MiB)", blocks, 
       maxbytes / (1024*1024) as "Max Bytes (MiB)", maxblocks, 
       user_bytes / (1024*1024) as "User Bytes (MiB)", user_blocks,
       substr(file_name, 0, 40)  
FROM dba_data_files;

-- Create user
-- Oracle Database automatically creates a schema when you create a user.
CREATE USER userName 
    IDENTIFIED BY "password"    -- NOTE: quotes required when password starts with letter!
    DEFAULT TABLESPACE users
    QUOTA unlimited on users    -- NOTE: users require quota on tablespace, default is 0
    TEMPORARY TABLESPACE temp;
        
-- ALTER USER userName QUOTA unlimited on users;

GRANT connect TO userName;
GRANT create table TO userName;
GRANT create sequence TO userName;
GRANT create view TO userName;
GRANT create procedure to userName;

-- show grants for user
-- System privileges
SELECT * 
FROM dba_sys_privs 
WHERE grantee='userName';

-- Table privileges
SELECT * 
FROM DBA_TAB_PRIVS 
WHERE grantee = 'userName';


-- select available users
SELECT UserName, Password, DEFAULT_TABLESPACE, TEMPORARY_TABLESPACE, PROFILE
FROM DBA_USERS;

-- Select tables for user from data dictionary
SELECT table_name 
FROM user_tables
WHERE table_name LIKE 'MY_%'
ORDER BY table_name;

-- Select all columns for a table
SELECT column_name, data_type, data_length, owner
FROM   all_tab_cols
WHERE  table_name = 'MY_TABLE'
ORDER BY column_name;

-- Select view definition for a given view
SELECT TEXT
FROM DBA_VIEWS
WHERE VIEW_NAME='MY_VIEW';

-- Select table definition for a given table
SELECT TABLE_NAME, TABLESPACE_NAME, STATUS, TEMPORARY
FROM DBA_TABLES
WHERE TABLE_NAME LIKE 'xyz%';

-- Select all temporary tables
SELECT TABLE_NAME, TABLESPACE_NAME, STATUS
FROM DBA_TABLES
WHERE TEMPORARY='Y'
ORDER BY TABLE_NAME;

-- Select all current USER (not BACKGROUND) sessions
-- "process" is the process ID of the client
SELECT sid, serial#, username, status, process, machine, sql_id
FROM v$session 
WHERE type='USER';

-- Select current SQL statement for user, including the bind variables
-- (Note: each bind variable results in one row - TODO: Use the PIVOT operator ...) 
SELECT rows_processed, sql_text, Name, Position, Datatype_String,Value_String
FROM v$sql
JOIN v$session ON v$session.sql_id=v$sql.sql_id
JOIN v$sql_bind_capture ON v$session.sql_id=v$sql_bind_capture.sql_id
WHERE username='userName';
