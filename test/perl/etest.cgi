#!/usr/bin/perl

 ###If you use this on a local server like Indigo, change  ###
 ### the path to perl by removing the /usr/bin/            ###

print "Content-type: text/html\n\n";
print "<html><head></head><body>\n";
print "<font face=verdana,arial size=2>\n";
print "Your path is working and pointing to Perl version:<p>\n";
print "<B>$]</B><p>\n";
print "The complete path to this script is:<p>\n";
print "<B>$ENV{'SCRIPT_FILENAME'}</B><p>\n";
print "The relative URL of this script (used in forms) is:<p>\n";
print "<B>$ENV{'SCRIPT_NAME'}</B>\n";
print "</font>\n";
print "</body></html>\n";

exit;



