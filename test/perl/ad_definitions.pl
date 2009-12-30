#!/usr/bin/perl

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


#########################################################
# VARIABLES: used by ad_display.pl and ad_redirect.pl
#########################################################


###########################
# General Configuration
###########################
$site_url        = "http://dev.dave.imarc.net";      # The base URL of your site
$site_path       = "/usr/local/www/dave";        # The full path to your site's directory
$ad_directory    = "$site_url/barp-files/ads";   # This is where the ads are
$log_directory   = "$site_path/barp-files/logs"; # This is where the ads are

# This should be the virtual path to the redirection script
$redirect_script = "/cgi-bin/barp/ad_redirect.pl";


###########################
# Logging
###########################
# Should we log all ad-views?
$use_log         = "1"; # 1=YES; 0=NO
$view_log_file   = "$log_directory/ad_views.log";  # impression/ad-view log file
$click_log_file  = "$log_directory/ad_clicks.log"; # click-thru log file


###########################
# Display
###########################
# Do you want the alt text printed below the ad?
$print_alt_text  = "1"; # 1=YES; 0=NO

# If you are printing the ALT text ($printAlt = 1)
# Then you can specify a size, font, color, and/or class.
$alt_text_size   = "1";
$alt_text_face   = "Arial,Helvetica";
$alt_text_color  = "#999999";
#$alt_text_class = "";

$ad_align        = "bottom"; # Align the ad/image...
$ad_border       = "0"; # Do you want a border around the ad?



# Bug fix in perl
@TEST = ("test.gif");



