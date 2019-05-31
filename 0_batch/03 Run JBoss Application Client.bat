@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-12
set JBOSS_HOME=c:\tools\wildfly-16.0.0.Final

del /F /Q %JBOSS_HOME%\appclient\log\*
del /F /Q %JBOSS_HOME%\appclient\tmp\*
call %JBOSS_HOME%\bin\appclient.bat ..\ear\target\Study03.ear#Study03_appclient.jar
pause