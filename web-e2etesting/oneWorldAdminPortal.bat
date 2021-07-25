@echo off
rem -------------------------------------------------------------------------
rem Execute Automation Script for OneWorldAdminPortal in Windows
rem -------------------------------------------------------------------------
rem
rem A simple utility for executing the automation script
rem for oneWorldAdminPortal in Windows

mvn test -DCONFIG="oneWorldAdminPortal" -Dtestcase="ALL"

