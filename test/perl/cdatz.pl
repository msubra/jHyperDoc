#!/usr/bin/perl

# cdatz.pl
# Cdatz - Configurable Date and Time Zone Script
# Ted Hawkins, April 2003
# Free to use as you wish
# From Scriptles (http://scriptles.bluwall.com)
# Email - scriptles@bluwall.com

##########################################################################
############## Put this part in your configuration file ##################
##########################################################################
# Time Zone settings - All time zones are based from London(Greenwich/GMT)
# Look at your clock and calculate the difference.
# Daylight Savings Time is automatic if your server has that set-up.
# Times set at quarter hour intervals (.25, .5, .75)
# 15 minutes past the hour would be .25 etc-etc.
$zone = "0";         # Server time zone: (London = 0, New York = -5, Tokyo = 9)
$yourtime = "0";     # Your location time zone: (London = 0, New York = -5, Tokyo = 9)
$town = "Current Date and Time in London is:";      # Change London to your town name
# Change for date format
$thisdate = "1";     # 0=American(MDY)  1=European(DMY) 2=ISO(YMD)
$fface = "Verdana";   # Font face

##########################################################################
################# Put this part in your main perl script #################
##########################################################################
# Core script - do not change
  ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = gmtime(time);
if ($isdst eq "1") { $tzdiff = ($yourtime - $zone - 1); }
  else { $tzdiff = ($yourtime - $zone) };
  $tzdiff = $tzdiff * 3600;
  ($sec, $min, $hour, $mday, $mon, $year, $wday, $yday, $isdst) = localtime(time + $tzdiff);
$year = 1900 + $year;
 $lastyear = ($year - 1);
 $lastmonth = $mon;
 $thismonth = ($mon + 1);
 if ($lastmonth <10) {$lastmonth = "0$lastmonth";}
 if ($thismonth <10) {$thismonth = "0$thismonth";}
 if ($mday <10) {$mday = "0$mday";}
 if ($hour<10) {$hour = "0$hour";}
 if ($min<10) {$min = "0$min";}
$nowtime = "$hour:$min";
 if ($thisdate == 0) {$thisdate = "$thismonth/$mday/$year-$nowtime"; } # American
 if ($thisdate == 1) {$thisdate = "$mday/$thismonth/$year-$nowtime"; } # European
 if ($thisdate == 2) {$thisdate = "$year/$thismonth/$mday-$nowtime"; } # ISO
$todaysdate = "$thisdate";

##########################################################################
###### This is the printable part.  Put it where ever you need it. #######
##########################################################################
# Print the Date and time
print "Content-type: text/html\n\n";
print "<font face=$fface>\n";
print "$town\n";                   # calls the town
print "$todaysdate\n";             # calls the date and time
print "</font>\n";
##########################################################################





