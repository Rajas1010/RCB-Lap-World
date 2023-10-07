@echo off
 echo JAVA Program Compiler with Database
  set name=LapShowRoom
  set file=%name%.class
  if exist %file% (del %file%)
 echo File Name : %name%
 @echo.
 javac -cp connector.jar;. LapShowRoom.java
 @echo.
 if not exist %file% (echo Compilation Time Error)
 if exist %file% ( java -cp connector.jar;. %name% )
 @echo.
 pause