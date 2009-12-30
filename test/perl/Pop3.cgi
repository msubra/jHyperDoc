#!/usr/bin/perl  -w

#####################################################
#              POP3 Email Retriever                 #
#            Author:- Dr.Nagender Sangra            #
#           Created on :- 8 January , 2001          #
#           Last Updated:- 19 January , 2001        #
#  URL:- http://my_perl_cgi_scripts.tripod.com/     #
#  Send bugs/suggestions to nagender@nagender.com   #
#####################################################

use Net::POP3;
use CGI qw/ :standard :html3/;

############ Start of Editable variables #############
$referer='http://www.yourserver.com/pop3.htm'; # URL of referer form
$cgipath='http://www.yourserver.com/cgi-bin/pop3.cgi'; # URL of this script
############ End of Editable variables   #############


############## Routine for Main Form #########################
if ($ENV{"HTTP_REFERER"} eq $referer || ( $ENV{"HTTP_REFERER"} eq $cgipath && param('submit') eq "Main Menu" ) ) { # Check Referer

# Set this variable to your POP3 server name
print header; 
my $ServerName = param('server');
my $UserName = param('username');
my $Password = param('password');
$ServerName=~s/[^\w\d\@.]//g;
my ($Messages,$msg_id,$date,$from,$subject,$size,$attachments,$count,$number);

# Create a new POP3 object
my $pop = Net::POP3->new($ServerName); 

# If you can't connect, don't proceed with the rest of the script
Error("Couldn't log on to server") unless $pop; 

# Initiate the mail transaction
defined ($pop->login($UserName, $Password))
    or Error("Can't authenticate: $!");

# Get list of Messages
$messages = $pop->list
    or Error("Can't get list of undeleted messages: $!");
print <<FORMTOP;

<html>
<head>
<title>POP3 Mail v 1.0</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body bgcolor="#F0EEE3">
<div align="center">
  <h1><font color="#3333FF">POP3 Mail v 1.0</font></h1>
Please select the Message(s) and Press one of the buttons:
  <form method="post" action="$cgipath">
    <table width="100%" border="1" border="1" cellpadding="0" cellspacing="0">
      <tr bgcolor="#CCCCFF"> 
        <td width="4%">&nbsp; </td>
        <td width="17%">Date:</td>
        <td width="21%">From:</td>
        <td width="30%">Subject:</td>
        <td width="4%">Size(kb):</td>
        <td width="24%">Attachments:</td>
      </tr>
FORMTOP
$count=0;
foreach $msg_id (keys %$messages) {
    $size = $$messages{$msg_id};
    $size = oct($size)/1000;
    if ($size < 1) {$size = 1}
    else {$size = int $size}
    $message = $pop->get($msg_id);
    unless (defined $message) {
        warn "Couldn't fetch $msg_id from server: $!\n";
        next;
    }
    # $message is a reference to an array of lines
    ($date,$from,$subject,$attachments,$name)=Process_Header(@$message);
	# print the form
print <<FORMMIDDLE;
      <tr> 
        <td width="4%"> 
          <input type="checkbox" name="msg_id$count" value="$msg_id">
        </td>
        <td width="17%">$date</td>
        <td width="21%">$from</td>
        <td width="30%">$subject</td>
        <td width="4%">$size</td>
        <td width="24%">   
FORMMIDDLE
	$number=1;
	foreach (keys %$name)
	 {
		print $number,")",$_,":",$$name{$_},"<BR>";
		$number++;
	 }
        print "</td></tr>";

$count++;
}
# Close the connection
$pop->quit();
$count=$count--;
print<<FORMBOTTOM;
    </table>
  <p>&nbsp;</p>
<input type="submit" name="submit" value="View">
<input type="submit" name="submit" value="Delete">
<input type="hidden" name="server" value="$ServerName">
<input type="hidden" name="username" value="$UserName">
<input type="hidden" name="password" value="$Password">
<input type="hidden" name="count" value="$count">
<input type="reset" name="reset" value="Reset">
    </form>

  <p>&nbsp;</p>
</div>
</body>
</html>
FORMBOTTOM
}
########## Routine for Deleting selected mails #############
elsif (param('submit') eq "Delete") {

print header; 
	my $ServerName = param('server');
	my $UserName = param('username');
	my $Password = param('password');
	my $count = param('count');
	my ($msg_id,$deleted);
# Create a new POP3 object
my $pop = Net::POP3->new($ServerName); 

# If you can't connect, don't proceed with the rest of the script
Error("Couldn't log on to server") unless $pop; 

# Initiate the mail transaction
defined ($pop->login($UserName, $Password))
    or Error("Can't authenticate: $!");

# Get list of Messages
$messages = $pop->list
    or Error("Can't get list of undeleted messages: $!");
$count--;
foreach (0..$count)
{
 $msg_id=param("msg_id$_");
 if ($msg_id ne "") {
	$deleted++;
    $pop->delete($msg_id);
  }
}
$pop->quit();
print " $deleted Messages deleted<BR>";
print <<FORM;
<FORM METHOD="POST" ACTION="$cgipath">
<input type="hidden" name="server" value="$ServerName">
<input type="hidden" name="username" value="$UserName">
<input type="hidden" name="password" value="$Password">
<input type="submit" name="submit" value="Main Menu">
FORM

}
########### Routine for Viewing Selected Mails ############
elsif (param('submit') eq "View") {

print header; 
	my $ServerName = param('server');
	my $UserName = param('username');
	my $Password = param('password');
	my $count = param('count');
	my ($msg_id,$viewed,$date,$from,$subject,$body,$message);
# Create a new POP3 object
my $pop = Net::POP3->new($ServerName); 

# If you can't connect, don't proceed with the rest of the script
Error("Couldn't log on to server") unless $pop; 

# Initiate the mail transaction
defined ($pop->login($UserName, $Password))
    or Error("Can't authenticate: $!");

# Get list of Messages
$messages = $pop->list
    or Error("Can't get list of undeleted messages: $!");

open (FILE,">temp") or Error("Can't write temp file");
print FILE "";
close FILE;
print '<table width="100%" border="0" cellspacing="0" cellpadding="0">';
$count--;
foreach (0..$count)
{
 $msg_id=param("msg_id$_");
 if ($msg_id ne "") {
	$viewed++;
    $message = $pop->get($msg_id);
	($date,$from,$subject,$body,$attachments)=Process_Body(@$message);
	print <<HTML;
	<tr bgcolor="#CCCCFF"><td>&nbsp;</td></tr>
    <tr>
      <td>Date: $date</td>
    </tr>
    <tr>
      <td>From: $from</td>
    </tr>
    <tr>
      <td>Subject: $subject</td>
    </tr>
    <tr>
      <td>Message Text:<BR> $body</td>
    </tr>
HTML
print "<FORM METHOD=\"POST\" ACTION=\"$cgipath\">";
print "<input type=\"hidden\" name=\"server\" value=\"$ServerName\">"; 
print "<input type=\"hidden\" name=\"username\" value=\"$UserName\">"; 
print "<input type=\"hidden\" name=\"password\" value=\"$Password\">";  
for ($i=0; $i < @$attachments; $i++) {
	print "<tr bgcolor=\"#CCFFFF\"><td>Attachment: <INPUT TYPE=\"SUBMIT\" NAME=\"file\" VALUE=\"",$$attachments[$i],"\"></td></tr>";
}  
print "</FORM>";
  }
 }
$pop->quit();
print '</table>';

print <<FORM;
<FORM METHOD="POST" ACTION="$cgipath">
<input type="hidden" name="server" value="$ServerName">
<input type="hidden" name="username" value="$UserName">
<input type="hidden" name="password" value="$Password">
<input type="submit" name="submit" value="Main Menu">
FORM

}

elsif ($ENV{"HTTP_REFERER"} eq $cgipath && param('file') ne "")
 {
	my $query;
	$query = new CGI;
	my $ServerName = param('server'); 
	my $UserName = param('username'); 
	my $Password = param('password');
	my $file = param('file');
	my ($type,$encoding,$marker,$content)=("","","","");
	open (FILE,"temp") or Error("Can't open temp file");
	while (<FILE>) {
		if (/^name=$file$/) {
			($type) = <FILE> =~ /^type=(\w+\/\w+)\;*/;
			($encoding) = <FILE> =~ /^encoding=(.+)$/;
			$marker = <FILE>;
			while (<FILE>) {
				if (/$marker/) {last}
				else {$content .= $_}
			 }
		last;
		 }
	 }
	close FILE;
	if ($encoding =~ /base64/i) {$content = &Mime_decode($content)}
	elsif ($encoding =~ /quoted-printable/i) {$content = &Quoted_decode($content)} 
	print $query->header($type);
	print $content;
 }
else { # Invalid referer
print "Invalid Referer";
exit;
}
################# Subroutines start ####################
######## Return Formatted Email ##########
sub Process_Header
{
 # Assign parameter to a local variable
 my (@lines) = @_; 

 # Declare local variables
 my ($date,$from, $line, $type, $subject,$size,$boundary,$attachments,$count,@array,%name); 

$count=0;
 # Check each line in the header
 foreach $line (@lines)
 {
	$count++; 
    last if ($line=~/^$/); 

   if($line =~ m/^From: (.*)/i)
   {
      # We found the "From" field, so let's get what we need
      $from = $1;
      $from =~ s/"|<.*>//g;
      $from = substr($from, 0, 39);
   }
   elsif( $line =~ m/^Subject: (.*)/i)
   {
      # We found the "Subject" field, so let's get what we need
      $subject = $1;
      $subject = substr($subject, 0, 29);
   }
   elsif ($line =~m/^Date: (.*)/i)
   {
	  $date = $1;
	  $date = substr($date,0,16);
   }
   elsif ($line =~m/^Content-type: (.*)\;*\s+ boundary=(.+)/i)
   {
     $type = $1;
	 if ($2 ne "") {$boundary = $2}
   }
   elsif ($line =~/boundary=\"*(.+)\"*/) {$boundary = $1}   
  }
$boundary =~ s/\"+//g;
$attachments=0;$body=""; 
# Process body 
	foreach (@lines[$count..$#lines]) {$body .= $_}
	if ($boundary ne "")
	 {
		$boundary = '--'.$boundary;
		@array = split(/$boundary/, $body);
		foreach (@array[2..$#array])
		 {
			if (/Content-disposition: (.+?)\;/i) {
			    $disposition = $1;
			}
			else {$disposition=""}
			if ($disposition=~m/attachment/i) { 
				if (/^name=\"(.+)\"/i) {
					$name{$1} = length($_);
			 	}
				elsif (/Content-description: (.+)/i) {
					$name{$1} = length($_);
			 	}
				elsif (/filename=\"(.+)\"/i) {
					$name{$1} = length($_);
			 	}
			} 
		 }
	 }

	$attachments = @array - 2; 
	if ($attachments < 1) {$attachments = 0}
  # Print the result
  return($date,$from, $subject, $attachments, \%name);
}
######### Return Whole Email ########
sub Process_Body
{

 # Assign parameter to a local variable
 my (@lines) = @_; 
 
 # Declare local variables
 my ($date,$from, $line, $subject,$body,$count,$file_line,@file,@array,@attachments,$number,$type,$file,$encoding,$name,$description); 
 $count=0;
 # Check each line in the header
 foreach $line (@lines)
 {
	$count++;
    last if ($line=~/^$/);

   if($line =~ m/^From: (.*)/i)
   {
      # We found the "From" field, so let's get what we need
      $from = $1;
   }
   elsif( $line =~ m/^Subject: (.*)/i)
   {
      # We found the "Subject" field, so let's get what we need
      $subject = $1;
   }
   elsif ($line =~m/^Date: (.*)/i)
   {
	  $date = $1;
   }
   elsif ($line =~m/^Content-type: (.*)\;*\s+ boundary=(.+)/i)
   {
     $type = $1;
	 if ($2 ne "") {$boundary = $2}
   }
   elsif ($line =~/boundary=\"*(.+)\"*/) {$boundary = $1}   

  } 
$boundary =~ s/\"+//g;
$body="";  
# Process body 
	foreach (@lines[$count..$#lines]) {$body .= $_}
	if ($boundary ne "")
	 {
		$boundary = '--'.$boundary;
		@array = split(/$boundary/, $body);
		foreach (@array[2..$#array])
		 {
			@file = split(/\n/,$_);

			$number=0;$name="";$description="";$type="";$encoding="";$disposition="";
			foreach $file_line (@file[1..$#file]) 
 			{
				$number++; 
				if ($file_line =~ m/^Content-Type: (.*?)\;/i) 
				{$type = $1}
				elsif ($file_line =~m/Content-disposition: (.*)\;/i)
				{
					$disposition = $1;
					if ($file_line =~m/filename=\"(.*)\"/) {$name=$1}
				}
				elsif ($file_line =~m/name=\"(.*?)\"/i) 
   				{$name = $1} 
 			  	elsif ($file_line =~m/^Content-Transfer-Encoding: (.*)/i) 
   				{$encoding = $1}
				elsif ($file_line =~m/^Content-description: (.+)/i)
				{$description = $1}
				else {last if ($file_line=~/^$/)} 
 
  			}
			$boundary =~ s/\s+//;
			if ($disposition=~m/attachment/) {
				push (@attachments,$name);
				open (SAVE,">>temp") or Error("Can't write $name file");
				print SAVE "name=$name\n";
				print SAVE "type=$type\n";
				print SAVE "encoding=$encoding\n";
				print SAVE "--- FILE MARKER ---\n";
				foreach (@file[$number..$#file])  {
					if (/$boundary/i) {last}
						print SAVE
		 	 	}
				print SAVE "\n--- FILE MARKER ---\n";
				close SAVE;
			 }
		 }
	  $body = "";
	  if ($array[1]=~m/Content-Type: multipart\/alternative\;/i)
	   {
		  ($boundary) = $array[1]=~m/boundary=\"(.+)\"/i;
		  $boundary= '--'.$boundary;
		  @file = split(/$boundary/,$array[1]);
		  @lines = split(/\n/,$file[2]);
		  $number=0;$type="";$encoding="";
 
	      foreach $file_line (@lines[1..$#lines]) {
				$number++; 
   			        if ($file_line =~ m/^Content-Type: (.*?)\;/i) 
				{$type = $1}
 			  	elsif ($file_line =~m/^Content-Transfer-Encoding: (.*)/i) 
   				{$encoding = $1}
				else {last if ($file_line=~/^$/)}
		   }
		push (@attachments,"Alternative text");
		open (SAVE,">>temp") or Error("Can't write $name file");
		print SAVE "name=Alternative text\n";
		print SAVE "type=$type\n";
		print SAVE "encoding=$encoding\n";
		print SAVE "--- FILE MARKER ---\n";
		foreach (@lines[$number..$#lines])  {print SAVE}
		print SAVE "\n--- FILE MARKER ---\n";
		close SAVE;
		$body = formatting($file[1]);
       }
	   else {
		$body = formatting($array[1]);
		} 
	 }
  $body = formatting($body);
  # Print the result
  return($date,$from,$subject,$body,\@attachments);
}
##### Removes the Content type headers #####
sub formatting
{
  my ($body) = @_;
  my (@lines);
  @lines = split(/\n/,$body);
  $body="";
  foreach (@lines) {
	if (!/^Content|charset/i) {$body .= "$_<BR>"}
   }
  return($body);
 }


######### Mime Decoding Subroutine #########
sub Mime_decode
 {
    my @str = @_;
    $str=join(//,@str); 
    #local($^W) = 0; # unpack("u",...) gives bogus warning in 5.00[123] 
    my $res = ""; 
    $str =~tr|A-Za-z0-9+=/||cd;        # remove non-base64 chars 
    if (length($str) % 4) { 
	Error("Base64 decoder requires string length to be a multiple of 4");
    } 
    $str =~ s/=+$//;                        # remove padding 
    $str =~ tr|A-Za-z0-9+/| -_|;            # convert to uuencoded format 
    while ($str =~ /(.{1,60})/gs) { 
	my $len = chr(32 + length($1)*3/4); # compute length byte 
	$res .= unpack("u", $len . $1 );    # uudecode 
    } 
    return($res); 

}
########## Quoted Printable Decoding #########
sub Quoted_decode($)
 {
	my $str = shift;
    $str =~ s/[ \t]+?(\r?\n)/$1/g;  # rule #3 (trailing space must be deleted)
    $str =~ s/=\r?\n//g;            # rule #5 (soft line breaks)
    $str =~ s/=([\da-fA-F]{2})/pack("C", hex($1))/ge;
	return($str);
 }
########## Error Subroutine ##########
sub Error
{
 print $_[0];
 exit;
}
################## Subroutines End #######################
 
