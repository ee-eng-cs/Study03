@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-12
set JBOSS_HOME=c:\tools\wildfly-16.0.0.Final
set CLI_BAT=%JBOSS_HOME%\bin\jboss-cli.bat --connect --echo-command
set NOPAUSE=true

::GOTO QUEUE_TOPIC_ADD
::GOTO QUEUE_TOPIC_REMOVE
GOTO QUEUE_TOPIC_SHOW

:QUEUE_TOPIC_ADD
set COMMANDS=cd /subsystem=messaging-activemq/server=default/
set COMMANDS=%COMMANDS%,./jms-queue=StudyQueueSync:add(entries=["jms/StudyQueueSync"])
set COMMANDS=%COMMANDS%,./jms-queue=StudyQueueAsync:add(entries=["jms/StudyQueueAsync"])
set COMMANDS=%COMMANDS%,./jms-topic=StudyTopicSync:add(entries=["jms/StudyTopicSync"])
set COMMANDS=%COMMANDS%,./jms-topic=StudyTopicAsync:add(entries=["jms/StudyTopicAsync"])
set COMMANDS=%COMMANDS%,./jms-queue=StudyReplyQueue:add(entries=["jms/StudyReplyQueue"])
GOTO EXEC_CLI

:QUEUE_TOPIC_REMOVE
set COMMANDS=cd /subsystem=messaging-activemq/server=default/
set COMMANDS=%COMMANDS%,./jms-queue=StudyQueueSync:remove
set COMMANDS=%COMMANDS%,./jms-queue=StudyQueueAsync:remove
set COMMANDS=%COMMANDS%,./jms-topic=StudyTopicSync:remove
set COMMANDS=%COMMANDS%,./jms-topic=StudyTopicAsync:remove
set COMMANDS=%COMMANDS%,./jms-queue=StudyReplyQueue:remove
GOTO EXEC_CLI

:QUEUE_TOPIC_SHOW
set COMMANDS=cd /subsystem=messaging-activemq/server=default/
set COMMANDS=%COMMANDS%,ls jms-queue
set COMMANDS=%COMMANDS%,ls jms-topic
set COMMANDS=%COMMANDS%,./jms-queue=StudyQueueSync:read-resource
set COMMANDS=%COMMANDS%,./jms-queue=StudyQueueAsync:read-resource
set COMMANDS=%COMMANDS%,./jms-topic=StudyTopicSync:read-resource
set COMMANDS=%COMMANDS%,./jms-topic=StudyTopicAsync:read-resource
set COMMANDS=%COMMANDS%,./jms-queue=StudyReplyQueue:read-resource
GOTO EXEC_CLI

:EXEC_CLI
call %CLI_BAT% --commands="%COMMANDS%"
pause