#!/usr/bin/perl
require './ad_definitions.pl';

#########################################################
# iMarc Banner Ad Rotation Program                      #
#                                                       #
#       version                   1.7                   #
#       created                   03.01.1999            #
#       modified                  01.01.2002            #
#                                                       #
# http://www.imarc.net                                  #
# dave@imarc.net                                        #
#########################################################
#       sell out!                 options script        #
#########################################################



# Now that we've got a random number, let's see which ad it is...
# If it's a Dynamic Ad with HTML
# If it's boring old crappy ad that's just an animated GIF
sub display_ad {
	
	# Generate Random Number
	srand(time ^ $$);
	$num = rand(@images);

	print "Content-type: text/html\n\n";
	if ($urls[$num]) {
		print "<a href=\"$redirect_script";
		print "?";
		print "$urls[$num]\">";
	}
	print "<img src=\"$ad_directory/$images[$num]\"";
	if ($ad_border ne "") {
		print " border=\"$ad_border\"";
	}
	if ($ad_align) {
		print " align=\"$ad_align\"";
	}
	if ($ad_align[$num]) {
		print " alt=\"$alt[$num]\"";
	}
	print ">";
	if ($urls[$num]) {
		print "</a>";
	}
	print "\n";
	if ($print_alt_text) {
		print "<br>";
		if ($urls[$num]) {
			print "<a href=\"$redirect_script";
			print "?";
			print "$urls[$num]\">";
		}
		if ($alt_text_size || $alt_text_face || $alt_text_color || $alt_text_class) {
			print "<font";
			if ($alt_text_class) { 
				print " class=\"$alt_text_class\""
			} else {
				if ($alt_text_size)  { print " size=\"$alt_text_size\"" }
				if ($alt_text_face)  { print " face=\"$alt_text_face\"" }
				if ($alt_text_color) { print " color=\"$alt_text_color\"" }
			}
			print ">";
		}
		print "$alt[$num]";
		if ($alt_text_size || $alt_text_face || $alt_text_color || $alt_text_class) {
			print "</font>";
		}
		if ($urls[$num]) {
			print "</a>";
		}
		print "\n";
	}
	&log_view;
}

# If you're gonna log, you might as well do it here
# This will write in the same style as Apache's combined log format
# so you can analyze your log views with the same software as your web logs

sub log_view {
	if ($use_log) {
		
		@days   = ('Sun','Mon','Tue','Wed','Thu','Fri','Sat');
		@months = ('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
		($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = localtime(time());
		if ($mday < 10) { $mday = "0$mday"; }
		if ($hour < 10) { $hour = "0$hour"; }
		if ($min < 10) { $min = "0$min"; }
		if ($sec < 10) { $sec = "0$sec"; }
	    $year += 1900;
	  	$log_time = "\[$mday/$months[$mon]/$year:$hour\:$min\:$sec\]";
		
		open(LOG, ">>$view_log_file");
		print LOG "$ENV{'REMOTE_ADDR'} - - $log_time \"GET /$client[$num] HTTP/1.0\" 100 100 \"$baseDir$images[$num]\" \"$ENV{'HTTP_USER_AGENT'}\"\n";
		close(LOG);
	}
}

# Bug fix in perl
@TEST2 = ("test.gif");
