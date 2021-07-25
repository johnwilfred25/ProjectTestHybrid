# 7Test  - SevenTablet's QA Automation Tool

7Test is a lightweight java test automation framework for end to end testing.
It utilizes the Hybrid approach(Keyword-driven + Data Driven) and easy to use syntax. 
Automation developer can create new test cases from the spreadsheets.

## Getting Started

Follow instructions here to download and set up 7Test QA Automation tool:

https://confluence.seventablets.com/display/PHP/Installation+of+7Test#Installationof7Test-InstallJava

## Running the tests

* Get somebody that knows how to use this to give you a tutorial

* DO NOT RUN AGAINST STAGE !!
```
* To run on QA  
```
mvn test -DCONFIG="<name of config file in .../config/AppConfig>"

e.g, to use the agent_info_update.properties file, do:

mvn test -DCONFIG="agent_info_update" 

## Sample config file:

```