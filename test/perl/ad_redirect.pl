#!/usr/bin/perl
require './ad_definitions.pl';

#########################################################
# iMarc Banner Ad Rotation Program                      #
#                                                       #
#       version                   1.7                   #
#       created                   03.01.1999            #
#       modified                  01.01.2003            #
#                                                       #
# http://www.imarc.net                                  #
# dave@imarc.net                                        #
#########################################################
#       sell out!                 options script        #
#########################################################


#####################################################
# This stuff shouldn't need to be changed           #
#####################################################

# Redirection the URL:
# The URL Should be coming into this cgi like this:
# <a href="cgi-bin/ad_redirect.pl?http://www.imarc.net/">
# Got it?

$redirect_url = $ENV{'QUERY_STRING'};
&redirect_user;
&log_click;


# Let's send this tool on his way...
sub redirect_user {
	print "Location: $redirect_url\n\n\n";
}


# If you're gonna log, you might as well do it here
# This will write in the same style as Apache's combined log format
# so you can analyze your log clicks with the same software as your web logs
sub log_click {
	if ($use_log eq '1') {

		@days   = ('Sun','Mon','Tue','Wed','Thu','Fri','Sat');
		@months = ('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
		($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = localtime(time());
		if ($mday < 10) { $mday = "0$mday"; }
		if ($hour < 10) { $hour = "0$hour"; }
		if ($min < 10) { $min = "0$min"; }
		if ($sec < 10) { $sec = "0$sec"; }
	    $year += 1900;
	  	$log_time = "\[$mday/$months[$mon]/$year:$hour\:$min\:$sec\]";

		open(LOG,">> $click_log_file") || die "$! \'$LOG\'";
		print LOG "$ENV{'REMOTE_ADDR'} - - $log_time \"GET /$redirect_url HTTP/1.0\" 100 100 \"$redirect_url\" \"$ENV{'HTTP_USER_AGENT'}\"\n";
		close(LOG);
	}
}

exit(0);
