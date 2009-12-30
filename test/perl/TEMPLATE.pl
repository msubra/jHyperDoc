#!/usr/bin/perl

############################################################
# Configuration
############################################################

# Here are all the ads
@images = ("ad_1.gif",
           "ad_2.gif",
           "ad_3.gif",
           "ad_4.gif");

# This is where the ads are linked to
# keep this list in thesame order as the list above
@urls = ("http://www.ad_1_link.com",
         "http://www.ad_2_link.com",
         "http://www.ad_3_link.com",
         "http://www.ad_4_link.com");

# Here's the alt text and text below each ad (also in the same order)
@alt = ("Click here to for buy Ad 1's Product",
        "Click here to for buy Ad 2's Product",
        "Click here to for buy Ad 3's Product",
        "Click here to for buy Ad 4's Product");

# And this is who's paying for the ad (also in the same order)
# Do NOT USE SPACES in the clients name (use an underscore instead)
@client = ("Client_1",
           "Client_1",
           "Client_2",
           "Client_3");



############################################################
# DON'T CHANGE BELOW THIS LINE
############################################################
require './ad_display.pl';
&display_ad;