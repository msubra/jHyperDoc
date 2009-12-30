#!/usr/bin/perl -w

#####################################################
#                Simple Guestbook                   #
#           Author:- Dr.Nagender Sangra             #
#           Created on :- 1 August,2000             #
#           Last Updated:-3 September,2000          #
#           Current Version:- 1.1                   #
#  URL:- http://my_perl_cgi_scripts.tripod.com/     #
# Send bug reports/suggestions to nagender@vsnl.com #
#####################################################

use CGI qw/ :standard :html3/;

########### Start of Editable Variables ############
$guestbook = "guestbook.txt"; # Path and name of file to store guestbook data

$title="Guestbook v 1.1"; # Title of guestbook

$max_records=50; # Maximum records to keep

########### End of Editable Variables ##############

@days=("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday");
@months=("January","February","March","April","May","June","July","August","September","October","November","December");

$font1 = "<FONT face=\"helvetica, arial, verdana, sans serif\" size=1>";
$font2 = "<FONT face=\"helvetica, arial, verdana, sans serif\" size=2>";
$font4 = "<FONT face=\"helvetica, arial, verdana, sans serif\" size=4>";

########### Start of Main Program ##################
print header;
print "<HTML><HEAD><TITLE>$title</TITLE></HEAD><BODY bgcolor=#9bff99>";

if ($ENV{'REQUEST_METHOD'} eq "POST")
 {&process()}
else {&showform("Welcome to $title");}

sub process {
my ($name,$city,$state,$country,$email,$comment);
$name=param('name');
$city=param('city');
$state=param('state');
$country=param('country');
$email=param('email');
$comment=param('comment');
 $name=~s/[^\w\s.]+//g;
$city=~s/[^\w\s]+//g;
$state=~s/[^\w\s]+//g;
$country=~s/[^\w\s]+//g;
$email=~s/[^\w@.]+//g;
$comment=~s/[^\w\s.]+//g;

unless ($name) {&showform("Please fill in your Name",$name,$city,$state,$country,$email,$comment)}

unless ($comment) {&showform("Please fill in the Comments",$name,$city,$state,$country,$email,$comment)}
unless ($city) {$city="Somecity"}
unless ($state) {$state="Somestate"}
unless ($country) {$country="Somecountry"}

unless ($email=~/[\w]+[.]*[@][\w.]+/) {$email='someone@somebody.com'}

#unless ($email) {$email='someone@somebody.com'}

&show_guestbook($name,$city,$state,$country,$email,$comment);
&showform("Add to Guestbook");
}

############# End of Main Program ####################

############ Subroutines Starts #####################

### Show Guestbook and add new entry ###

sub show_guestbook {

my ($name,$city,$state,$country,$email,$comment)=($_[0],$_[1],$_[2],$_[3],$_[4],$_[5]);
my ($line1,$line2,$line3,$line4,$lines,@line); 

$line1="<DIV ALIGN=RIGHT>$font1 Message Posted on " . $days[(localtime)[6]] . "," . $months[(localtime)[4]] . " " . (localtime)[3] . "," . (1900+(localtime)[5]) . " at " . (localtime)[2] . ":" . (localtime)[1] . "</DIV></FONT><BR>";
$line2="$font2 I am <B>" . $name . "</B> from <B>" . $city . "," . $state . "," . $country . "</B><BR>";
$line3="I want to say  <I>" . $comment . "</I><BR>";
$line4="You can contact me at <a href=mailto:$email>$email</a></FONT><HR>";

if (open(FILE,"$guestbook")) {$lines=<FILE>;
close FILE;}
@line=split(/<HR>/i,$lines);
if (scalar(@line) >= $max_records) {$#line=$max_records}
$lines=join('<HR>',@line);
print STDOUT "$font4 <b>$title</b></FONT><BR>";
open (SAVE,">$guestbook") or die "Can't write guestbook:#!";
flock(SAVE,2);
foreach ($line1,$line2,$line3,$line4) {print SAVE;print STDOUT;}
print SAVE $lines;

flock(SAVE,8);
print STDOUT $lines;
close SAVE;

}

### Show User input form ###
sub showform {
my ($title,$name,$city,$state,$country,$email,$comment)=($_[0],$_[1],$_[2],$_[3],$_[4],$_[5],$_[6]);
print <<Showform;
<HR><BR>$font4 <b>$title</b></FONT>
<FORM METHOD="POST" ACTION=""><TABLE>
<TR><TD ALIGN="RIGHT">NAME: <INPUT TYPE="text" NAME="name" SIZE=30 VALUE=$name></TD></TR>
<TR><TD ALIGN="RIGHT">CITY: <INPUT TYPE="text" NAME="city" SIZE=30 VALUE=$city></TD></TR>
<TR><TD ALIGN="RIGHT">STATE: <INPUT TYPE="text" NAME="state" SIZE=30 VALUE=$state></TD></TR>
<TR><TD ALIGN="RIGHT">COUNTRY: <INPUT TYPE="text" NAME="country" SIZE=30 VALUE=$country></TD></TR>
<TR><TD ALIGN="RIGHT">E-MAIL:<INPUT TYPE="text" NAME="email" SIZE=30 VALUE=$email></TD></TR>
<TR><TD ALIGN="RIGHT">COMMENTS:<TEXTAREA NAME="comment" ROWS=5 COLS=23 WRAP="virtual">$comment</TEXTAREA></TD></TR>
<TR><TD ALIGN="RIGHT"><INPUT TYPE="submit" NAME="submit" VALUE="Add to Guestbook">
<INPUT TYPE="reset" NAME="reset" VALUE="Do it again"></TD></TR>
</TABLE></FORM>
Showform
&footer;
exit;
}
### Show footer ###
sub footer {
  print "<DIV ALIGN=CENTER><BR><HR><BR>$font2 This script has been developed by Dr.Nagender Sangra of <a href=http://www.nagender.com/>nagender.com</a>";
  print " and can be found at <a href=http://my_perl_cgi_scripts.tripod.com/>my_perl_cgi_scripts.tripod.com</a></FONT><BR>";
  print "$font1 © Copyright 2000 Dr.Nagender Sangra , All rights reserved . For any suggestions or comments, please email at <a href=mailto:nagender\@vsnl.com>nagender\@vsnl.com</a> . </FONT></DIV>";
}
