@echo off
rem -------------------------------------------------------------------------
rem Execute Automation Script for OneWorldClientPortal in Windows
rem -------------------------------------------------------------------------
rem
rem A simple utility for executing the automation script
rem for oneWorldClientPortal in Windows

mvn test -DCONFIG="oneWorldClientPortal" -Dtestcase="ALL"