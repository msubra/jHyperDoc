#!/usr/bin/perl

#####################################################
#                 FTP Downloader                    #
#           Author:- Dr.Nagender Sangra             #
#           Created on :- 28 December,2000          #
#           Last Updated:-31 December,2000          #
#           Current Version:- 1.0                   #
#  URL:- http://my_perl_cgi_scripts.tripod.com/     #
# Send bug reports/suggestions to nagender@vsnl.com #
#####################################################

use CGI qw/ :standard :html3/;
use Net::FTP;

######### Editable Variable ##########
$referer='http://www.yourserver.com/ftp.htm'; # Referring HTML Form

######### Start of Main Program ########
if ($ENV{'HTTP_REFERER'} eq $referer) {

$cgi = new CGI;
print $cgi->header;
$server=$cgi->param('sname');
$user=$cgi->param('user');
$password=$cgi->param('passwd');
$spath=$cgi->param('spath');
$filename=$cgi->param('filename');
$tpath=$cgi->param('tpath');
if ($server eq "" || $user eq "" || $password eq "" || $spath eq "" || $filename eq "" || $tpath eq "" )
{Error("One or more of field has not been filled properly")}

my $ftp;
	chdir $tpath;
	$ftp = Net::FTP -> new($server) or Error("Can't open server");
	print $ftp -> message(), "<BR>";
    $ftp -> login($user, $password);
    print $ftp -> message(), "<BR>";
    $ftp -> binary();
    print $ftp -> message(), "<BR>";
    $ftp -> cwd($spath);
    print $ftp -> message(), "<BR>";
    $ftp -> get( "$filename", "$filename");
    print $ftp -> message(), "<BR>";
    print "Completed $_<BR><BR>";
	$ftp -> quit();
    exit;
}
else {print header;Error("Invalid refering URL");}
######### End of Main Program #############

############## Subroutines ################
sub Error()
{
 print $_[0];
 exit;
}
############# End ##############