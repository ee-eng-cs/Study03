@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-12
set JBOSS_HOME=c:\tools\wildfly-16.0.0.Final
set MAIN_JAR=..\se\target\Study03_se.jar
set LIB_JARS=..\common\target\Study03_common.jar;%JBOSS_HOME%\bin\client\jboss-client.jar 
if not exist %MAIN_JAR% goto end
call %JAVA_HOME%\bin\java -cp %MAIN_JAR%;%LIB_JARS% kp.se.MainEntry
:end
pause
