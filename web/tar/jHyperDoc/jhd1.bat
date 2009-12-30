@echo off
echo %1%
java -cp "jar/jHyperDoc.jar;jar/antlr.jar" jhd.Main %1% %2%
pause