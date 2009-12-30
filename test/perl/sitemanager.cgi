#!/usr/bin/perl -w

#####################################################
#                 Site Manager                      #
#           Author:- Dr.Nagender Sangra             #
#           Created on :- 1 August,2000             #
#           Last Updated:-2 September,2000          #
#           Current Version:- 1.2                   #
#  URL:- http://my_perl_cgi_scripts.tripod.com/     #
# Send bug reports/suggestions to nagender@vsnl.com #
#####################################################
use CGI qw/ :standard :html3/;

############# Start of Editable Variables #############
$path="/home/httpd/html/";
$title= "Site Manager ver 1.2";
$domain= "www.yourserver.com";
$cgipath= '/cgi-bin/sitemanager.cgi';
$top_dir="/home/httpd/";
@upload_files=("txt","htm","html","gif","jpg","jpeg");
@editable_files=("txt","htm","html");
@textmode_files=("txt","htm","html");    
############# End of Editable Variables  ##############

$font1 = "<FONT face=\"helvetica, arial, verdana, sans serif\" size=1>";
$font2 = "<FONT face=\"helvetica, arial, verdana, sans serif\" size=2>";
$font_w = "<FONT face=\"helvetica, arial, verdana, sans serif\" size=2 color=#FFFFFF>";

############# Start of the Main Program #############
if ( ($ENV{'REQUEST_METHOD'} eq "POST") && ($ENV{'SCRIPT_NAME'} eq $cgipath) && ($ENV{'REMOTE_ADDR'} eq $domain) ) {    
   if (param('Submit') eq "Submit") {

     if (param('username') && param('password')){&process()}
     else {authenticate()}
    }
   elsif (param('choice')=~/\bUpload Files\b/ || param('Uploadnewdir')) {&mainform("Upload")}
   elsif (param('choice')=~/\bCopy Files\b/ || param('Copynewdir')) {&mainform("Copy")}
   elsif (param('choice')=~/\bMove Files\b/ || param('Movenewdir')) {&mainform("Move")}
   elsif (param('choice')=~/\bEdit Files\b/ || param('Editnewdir')) {&mainform("Edit")}

   elsif (param('choice')=~/\bRename Files\b/ || param('Renamenewdir')) {&mainform("Rename")}
   elsif (param('choice')=~/\bDelete Files\b/ || param('Deletenewdir')) {&mainform("Delete")}
   elsif (param('choice')=~/\bCreate Directory\b/ || param('Create directorynewdir')) {&mainform("Create directory")}
   elsif (param('choice')=~/\bRemove Directory\b/ || param('Remove directorynewdir')) {&mainform("Remove directory")}
   elsif (param('choice')=~/\bShow Directory\b/ || param('Show directorynewdir')) {&mainform("Show directory")}
   elsif (param('Upload')) {&uploadfile()}
   elsif (param('Copy')) {&copyfile()}
   elsif (param('Move')) {&movefile()}
   elsif (param('Edit')) {&editfile()}
   elsif (param('Save')) {&savefile()}
   elsif (param('Rename')) {&renamefile()}
   elsif (param('Delete')) {&deletefile()}
   elsif (param('Create directory')) {&createnewdir()}
   elsif (param('Remove directory')) {&removedir()}
   elsif (param('showform')) {&showform()}
 }
else {authenticate()}
############ End of the Main Program ############

### Shows Authentication form ### 
sub authenticate {

print header;
print <<Form;
<HTML><HEAD><TITLE>User Authentication</TITLE></HEAD>
<BODY bgcolor=#9bff99>$font2 <b>$title</b></FONT><BR>$font2 User Authentication:</FONT><BR>
<TABLE cellPadding=0 cellSpacing=0 WIDTH="276" BORDER="0" ALIGN="CENTER">
<TR BGCOLOR=#494949><TD colspan=2 align=center>$font_w --- LOGIN --- </TD></FONT></TR>
<TR BGCOLOR=#494949><TD HEIGHT="114" WIDTH="68" VALIGN="TOP">$font_w<P>Username:</P>
<P>Password:</P></FONT><P>&nbsp;</P></TD><TD HEIGHT="114" WIDTH="192">
<FORM METHOD="post" ACTION="http://$domain$cgipath"><P>
<INPUT TYPE="text" NAME="username" SIZE="30"></P><P>
<INPUT TYPE="PASSWORD" NAME="password" SIZE="30"></P><P>
<INPUT TYPE="submit" NAME="Submit" VALUE="Submit">
<INPUT TYPE="reset" NAME="Reset" VALUE="Reset"></P>
</FORM></TD></TR></TABLE> 
Form
&footer();
print "</BODY></HTML>";
}

### Process the authentication request ###
sub process {
  my (@pairs,$count,$found,);
  open (FILE,"password.txt") or error("Can't open password file: #!");
  while (<FILE>) {push(@pairs,split(/\s+/,$_))}
  for ($count=0,$found=0;$count<scalar(@pairs);$count+=2)
   {
     if ($pairs[$count] eq param('username')) {

        if (($pairs[$count+1].(length($pairs[$count+1])**3)) eq param('password')) {
            $found="superuser";last;
           }
        elsif ($pairs[$count+1] eq param('password')) {
 	        $found="user";last;
	       }
	   }
   }
  close (FILE);
  if ($found eq "superuser" || $found eq "user") {
	  &showform($found);

    } else { 
       &authenticate();
    }
 }

### Show Main Menu ###
sub showform {
my ($user,$cur_dir,$submit,$count,$files,$type,$size,$date,@files,@size,@type,@date);
$user=join('',@_);
if ($user eq "") {
 $user=param('user');
 $cur_dir=param('cur_dir');
}
else {$cur_dir=$path}
if ($user eq "superuser") {$submit="submit"}
else {$submit="reset"}
($files,$type,$size,$date)=&givefiles($cur_dir);
@files=@$files;
@type=@$type;
@size=@$size;
@date=@$date;

print header;
print <<Choices;
<HTML>
<HEAD>
<TITLE>Site Management</TITLE></HEAD><BODY bgcolor=#9bff99>
$font2<b>$title</b></FONT><BR>
$font2<b>Site Manager</b></FONT><BR>
<FORM METHOD="post" ACTION="http://$domain$cgipath">
<INPUT TYPE="hidden" NAME="user" VALUE=$user>
<INPUT TYPE="hidden" NAME="cur_dir" VALUE="$cur_dir">
<TABLE  border=0 cellPadding=0 cellSpacing=0 ALIGN="CENTER" WIDTH="100%"><TR BGCOLOR=#494949>
<TD align="center"><INPUT TYPE=$submit NAME="choice" VALUE="Upload Files"></TD>
<TD align="center"><INPUT TYPE=$submit NAME="choice" VALUE="Copy Files"></TD>
<TD align="center"><INPUT TYPE=$submit NAME="choice" VALUE="Move Files"></TD>
<TD align="center"><INPUT TYPE=$submit NAME="choice" VALUE="Edit Files"></TD>
<TD align="center"><INPUT TYPE=$submit NAME="choice" VALUE="Rename Files"></TD>
<TD align="center"><INPUT TYPE=$submit NAME="choice" VALUE="Delete Files"></TD></TR><TR BGCOLOR=#494949>
<TD colspan=2 align="center"><INPUT TYPE=$submit NAME="choice" VALUE="Create Directory"></TD>
<TD colspan=2 align="center"><INPUT TYPE=$submit NAME="choice" VALUE="Remove Directory"></TD>
<TD colspan=2 align="center"><INPUT TYPE="submit" NAME="choice" VALUE="Show Directory"></TD>
</TR></TABLE>
</FORM>
<TABLE border=0 cellPadding=0 cellSpacing=0 WIDTH="100%">
<TR BGCOLOR="#660000">
<TD><FONT face="helvetica, arial, verdana, sans serif" size=1 color=#FFFFFF>Current Directory: $cur_dir</FONT></TD>
<TD><FONT face="helvetica, arial, verdana, sans serif" size=1 color=#FFFFFF>Domain:$domain</FONT></TD>
<TD><FONT face="helvetica, arial, verdana, sans serif" size=1 color=#FFFFFF>Top Directory: $top_dir</FONT></TD></TR>
</TABLE><BR>
<TABLE border=0 cellPadding=0 cellSpacing=0 WIDTH="100%"><TR BGCOLOR="494949"><TD>$font_w Name of File</FONT></TD><TD>$font_w Type</FONT></TD><TD>$font_w Size(Kb)</FONT></TD><TD>$font_w<CENTER>Last Modified</CENTER></FONT></TD></FONT></TR>
<TR BGCOLOR=#FFFFFF>
Choices

foreach (@files)
{
  print "<TD>$font2 $_</FONT></TD>";
  print "<TD>$font2 $type[$count]</FONT></TD><TD>$font2<CENTER>$size[$count]</CENTER></FONT></TD><TD align=center>$font2 $date[$count]</FONT></TD></TR><TR BGCOLOR=#FFFFFF>";
  $count++;
}
print "</TR></TABLE>";  
&footer;
print "</BODY></HTML>";
}

### Shows Main Site Operations Form ###
sub mainform {

my ($input,$input_type,$user,$files,$type,$size,$date,$testfile,$cur_dir,$parent_dir,@sub_dirs,@files,@type,@temp_type,@size,@temp_size,@date,@temp_date,@temp,$count,$number);

$input=join('',@_);
$user=param('user');
$cur_dir=param('cur_dir');
if (param('target_dir')) {$target_dir=param('target_dir')}
else {$target_dir=$cur_dir}
if ( length($top_dir) >= length($cur_dir) ) {$cur_dir=$top_dir}
if ( length($top_dir) >= length($target_dir) ) {$target_dir=$top_dir}
($parent_dir,@sub_dirs)=&givedir($cur_dir);
($targetparent_dir,@targetsub_dirs)=&givedir($target_dir);
($files,$type,$size,$date)=&givefiles($cur_dir);
@files=@$files;
@type=@$type;
@size=@$size;
@date=@$date;
if ($input eq "Edit") {
 $count=0;
 foreach $testfile (@files)
  {
    foreach (@editable_files)
     {
       if ($testfile=~/($_)$/i) {
           push @temp,$testfile;
           push @temp_type,$type[$count];
           push @temp_size,$size[$count];
           push @temp_date,$date[$count];
           last;
       }
     }
   $count++;
  }
if ($temp[0] ne "") {@files=@temp;@type=@temp_type;@size=@temp_size;@date=@temp_date;} else {undef @files}

}

print header;
print <<Formtop1;
<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML//EN">
<HTML><HEAD><TITLE>$input Files</TITLE></HEAD>
<BODY bgColor=#9bff99>$font2<b>$title</b></FONT><HR>
<FORM METHOD="post" ACTION="http://$domain$cgipath">
$font2<b>Select Source Directory for operation :$input</b></FONT>
<TABLE><TR BGCOLOR="494949"><TD WIDTH=250>$font_w Parent Directory</FONT></TD><TD WIDTH=250>$font_w Current Directory</FONT></TD><TD WIDTH=250>$font_w Sub Directories</FONT></TD></TR>
<TR><INPUT TYPE="hidden" NAME="user" VALUE="$user">
<INPUT TYPE="hidden" NAME="target_dir" VALUE="$target_dir">
Formtop1


print "<INPUT TYPE=\"hidden\" NAME=\"",$input,"newdir\" VALUE=1>";

if ($parent_dir ne "") 
 {
  print "<TD><INPUT TYPE=\"submit\" NAME=\"cur_dir\" VALUE=\"$parent_dir\"></TD>"
 }
else {print "<TD>$font2 Not Available</FONT></TD>"}
print "<TD>$font2 $cur_dir</FONT></TD>";
if (scalar(@sub_dirs) > 0) {
 print "<TD>";
 foreach (@sub_dirs) {
  print "<INPUT TYPE=\"submit\" NAME=\"cur_dir\" VALUE=\"$_\">";
  print "<BR>";
 }
 print "</TD>";
}
print "</TR></TABLE></FORM><HR>";

if ($input eq "Copy" || $input eq "Move") { ## Start:if copy|move
print <<Formtop2;
<FORM METHOD="post" ACTION="http://$domain$cgipath">
$font2<b>Select Target Directory for operation:$input</b></FONT>
<TABLE><TR BGCOLOR="494949"><TD WIDTH=250>$font_w Parent Directory</FONT></TD><TD WIDTH=250>$font_w Current Directory</FONT></TD><TD WIDTH=250>$font_w Sub Directories</FONT></TD></TR>
<TR><INPUT TYPE="hidden" NAME="user" VALUE="$user">
<INPUT TYPE="hidden" NAME="cur_dir" VALUE="$cur_dir">
Formtop2
print "<INPUT TYPE=\"hidden\" NAME=\"",$input,"newdir\" VALUE=1>";
if ($targetparent_dir ne "") 
 {
  print "<TD><INPUT TYPE=\"submit\" NAME=\"target_dir\" VALUE=\"$targetparent_dir\"></TD>";
 }
else {print "<TD>$font2 Not Available</FONT></TD>"}
print "<TD>$font2 $target_dir</FONT></TD>";
if (scalar(@targetsub_dirs) > 0) {
 print "<TD>";
 foreach (@targetsub_dirs) {
  print "<INPUT TYPE=\"submit\" NAME=\"target_dir\" VALUE=\"$_\">";
  print "<BR>";
 }
 print "</TD>";
}
print "</TR></TABLE></FORM><HR>";
} ## End:if copy|move 

if ($input eq "Copy" || $input eq "Move" || $input eq "Delete" || $input eq "Edit" || $input eq "Rename") { ## Start:if copy|move|delete|edit|rename
if ($input eq "Edit") {$input_type="radio"} else {$input_type="checkbox"}

print <<Formbottom1;
$font2<b>Select Files to perform operation:$input</b></FONT>
<FORM METHOD="post" ACTION="http://$domain$cgipath">
<TABLE border=0 cellPadding=0 cellSpacing=0 WIDTH="100%"><TR BGCOLOR="494949"><TD>&nbsp;</TD><TD>$font_w Name of File</FONT></TD><TD>$font_w Type</FONT></TD><TD>$font_w Size(Kb)</FONT></TD><TD>$font_w <CENTER>Last Modified</CENTER></FONT></TD></FONT></TR>
<TR BGCOLOR=#FFFFFF>
Formbottom1
$number=0;$count=0;
foreach (@files)
{
  print "<TD BGCOLOR=#494949><INPUT TYPE=\"$input_type\" NAME=\"filename",$number,"\" VALUE=\"$_\"></TD><TD>$font2 $_</FONT></TD>";
  print "<TD>$font2 $type[$count]</FONT></TD><TD>$font2 <CENTER>$size[$count]</CENTER></FONT></TD><TD align=center>$font2 $date[$count]</FONT></TD></TR><TR BGCOLOR=#FFFFFF>";
  if ($input ne "Delete" && $input ne "Edit") {
  print "<TD BGCOLOR=#494949>&nbsp</TD><TD colspan=4 BGCOLOR=#CCFFFF>$font2 New Filename(optional)<INPUT TYPE=\"text\" NAME=\"newfilename",$number,"\" VALUE=\"\"></FONT></TD></TR><TR bgcolor=#FFFFFF>";
  }
  $count++;
  $number++ if ($input_type ne "radio");
 }

$number--;

print "</TR></TABLE>";
} # End:if copy|move|delete
 if ($input eq "Upload") { # Start:if upload
print "$font1<b>Allowed Uploadable file types:</b>";
foreach (@upload_files) {print ">$_<"}
print <<Uploadmiddle;
</FONT><BR>$font2 Select files for upload</FONT><BR>
<TABLE WIDTH="315" ALIGN="CENTER"><TR><TD><FORM METHOD="post" ACTION="http://$domain$cgipath" ENCTYPE="multipart/form-data">
Uploadmiddle
 foreach (1..10)
 {
   print '<INPUT TYPE="file" NAME="filename',$_,'"><BR>';
 }
} ## End:if upload
print "</TD></TR></TABLE>";
if ($input eq "Create directory") {  ## Start:if create dir
print "<FORM METHOD=\"post\" ACTION=\"http://$domain$cgipath\">";
print "<CENTER>New Directory:<INPUT TYPE=\"text\" NAME=\"dirname\" VALUE=\"\">";
} ## End:if create dir

if ($input eq "Remove directory") { ## Start: if remove dir
print <<Removedirbottom;
$font2<b>Select Directory you want to delete</b></FONT><BR>
$font1<b>WARNING! All Files and Sub-directories within selected directory will be deleted</b></FONT>
<FORM METHOD="post" ACTION="http://$domain$cgipath">
<INPUT TYPE="hidden" NAME="cur_dir" VALUE="$cur_dir">
<INPUT TYPE="hidden" NAME="user" VALUE="$user">
Removedirbottom

foreach (@sub_dirs) {print "<INPUT TYPE=\"radio\" NAME=\"dirname\" VALUE=\"$_\"><FONT face=\"helvetica, arial, verdana, sans serif\" size=2 color=#FF0000>$_</FONT><BR>"}

} ## End: if remove dir

if ($input eq "Show directory") { ## Start: if show dir
print "$font2<b>The Contents of Current Directory</b></FONT>";
print "<TABLE border=0 cellPadding=0 cellSpacing=0 WIDTH=\"100%\"><TR BGCOLOR=\"494949\"><TD>$font_w Name of File</FONT></TD><TD>$font_w Type</FONT></TD><TD>$font_w Size(Kb)</FONT></TD><TD>$font_w <CENTER>Last Modified</CENTER></FONT></TD></FONT></TR>";
print "<TR BGCOLOR=#FFFFFF>";

foreach (@files)
{
  print "<TD>$font2$_</FONT></TD>";
  print "<TD>$font2$type[$count]</FONT></TD><TD>$font2<CENTER>$size[$count]</CENTER></FONT></TD><TD align=center>$font2$date[$count]</FONT></TD></TR><TR BGCOLOR=#FFFFFF>";
  $count++;
}
print "</TR></TABLE>";  
print "<HR><FORM METHOD=\"post\" ACTION=\"http://$domain$cgipath\"><INPUT TYPE=\"hidden\" NAME=\"user\" VALUE=\"$user\"><INPUT TYPE=\"hidden\" NAME=\"cur_dir\" VALUE=\"$cur_dir\"><INPUT TYPE=\"submit\" NAME=\"showform\" VALUE=\"Main Menu\"></FORM>"; 

} ## End: if show dir

if ($input eq "Copy" || $input eq "Move" || $input eq "Rename" || $input eq "Delete" || $input eq "Edit" || $input eq "Upload" || $input eq "Create directory" || $input eq "Remove directory") {   ## Start:if copy|move|delete|edit|upload|create dir|remove dir

print <<Formbottom2;
<BR><CENTER>
<INPUT TYPE="submit" NAME="$input" VALUE="$input">
<INPUT TYPE="reset" NAME="reset" VALUE="Reset">
<INPUT TYPE="hidden" NAME="user" VALUE="$user">
<INPUT TYPE="hidden" NAME="cur_dir" VALUE="$cur_dir">
<INPUT TYPE="hidden" NAME="target_dir" VALUE="$target_dir">
<INPUT TYPE="hidden" NAME="high_number" VALUE=$number>
<INPUT TYPE="submit" NAME="showform" VALUE="Main Menu">
</CENTER></FORM>
Formbottom2

} ## End: if copy|move|delete|edit|upload|create dir|remove dir

&footer();
}

### Actually upload the files ###
sub uploadfile {

my ($user,$cur_dir,$count,$filename,$newfile,@newfiles,$type,$flag,$filetype); 
$user=param('user');
$cur_dir=param('cur_dir');

 print header;
 print "<HEAD><TITLE>Upload Files</TITLE></HEAD>";
 print "<BODY bgcolor=#9bff99>$font2<b>$title</b></FONT><HR>";
 print "<TABLE><TR BGCOLOR=\"494949\"><TD WIDTH=250><FONT COLOR=\"FFFFFF\">Current Directory</FONT></TD></TR>";
 print "<TR><TD>$cur_dir</TD></TR></TABLE><HR>";
 print "Initiating Uploading... <BR>";
 foreach $count (1..10)
  {

   $filename="filename".$count;
   $newfile=param($filename);
   @newfiles=split(/\//,$newfile);
   $newfile=$newfiles[scalar(@newfiles)-1];
   if (param($filename) ne "")
    {
      foreach $type (@upload_files)
       {
         if ($newfile=~/\w+\.$type/) {$filetype=$type;$flag=1;last;} else {$flag=0}
       }
   if ($flag==0) {next}
      $newfile=$cur_dir.$newfile;
      ($newfile)=$newfile=~/(\/.+)/;
      open (SAVE,">$newfile") or error("Can't upload:#!");
      
   if ($filetype eq "gif" || $filetype eq "jpg" || $filetype eq "jpeg") {binmode (SAVE)}
      print STDOUT "Now Uploading ",param($filename),"<BR>";
      while (read(param($filename),$buffer,1024)) {print SAVE $buffer}
      close (SAVE);
    }
  }
 print "<BR>Uploading complete";
 print "<FORM METHOD=\"post\" ACTION=\"http://$domain$cgipath\"><INPUT TYPE=\"hidden\" NAME=\"user\" VALUE=$user><INPUT TYPE=\"hidden\" NAME=\"cur_dir\" VALUE=\"$cur_dir\"><INPUT TYPE=\"submit\" NAME=\"showform\" VALUE=\"Main Menu\"><INPUT TYPE=\"submit\" NAME=\"choice\" VALUE=\"Upload Files\"></FORM>"; 
 &footer();
}


### Actually copies the files ###
sub copyfile {

my ($cur_dir,$target_dir,$user,$flag,$filename,$newfilename,$high_number,@temp,$source_file,$target_file);

$user=param('user');
$cur_dir=param('cur_dir');
$target_dir=param('target_dir');
$high_number=param('high_number');

print header;
print "<HEAD><TITLE>Copy Files</TITLE></HEAD>";
print "<BODY bgcolor=#9bff99>$font2 <b>$title</b></FONT><HR>";
print "<TABLE><TR BGCOLOR=\"494949\"><TD WIDTH=250>$font_w Source Directory</FONT></TD><TD WIDTH=250>$font_w Target Directory</FONT></TD></TR>";
print "<TR><TD>$cur_dir</TD><TD>$target_dir</TD></TR></TABLE><HR>";

foreach (0..$high_number)
{
  $filename="filename".$_;
  $newfilename="newfilename".$_;

  @temp=("");
  if (param($filename)) 
   {

     $source_file=param($filename);
     print "<BR>$source_file<BR>";
     $flag=0;
     foreach (@textmode_files) 
      {
       if ($source_file=~/($_)$/) {$flag=1;last;}
      }
     open (FILE,$source_file) or error("Can't open $source_file for reading");

     if ($flag != 1) {print "Using Binary Mode<BR>";binmode (FILE);}

     while (<FILE>) {push (@temp,$_)}
     close (FILE);

     ($target_file)=$source_file=~/.+\/(\w+\.*\w*)/;
     if (param($newfilename)) {$target_file=param($newfilename)}
     $target_file=$target_dir.$target_file;
     if ($source_file eq $target_file) {print "Source & Target file can't be the same.<BR>";last;}
     ($target_file)=$target_file=~/(\/.+)/;
     open (SAVE,">$target_file") or error("Can't create target file $target_file");
     if ($flag != 1) {binmode (SAVE);}
     print "Starting Copying: $source_file<BR>";
     foreach (@temp) {print SAVE}
     close(SAVE);
     print "Finished Copying: $target_file<BR>";  

   }
}
  print "<HR><FORM METHOD=\"post\" ACTION=\"http://$domain$cgipath\"><INPUT TYPE=\"hidden\" NAME=\"user\" VALUE=$user><INPUT TYPE=\"hidden\" NAME=\"cur_dir\" VALUE=\"$cur_dir\"><INPUT TYPE=\"submit\" NAME=\"showform\" VALUE=\"Main Menu\"><INPUT TYPE=\"submit\" NAME=\"choice\" VALUE=\"Copy Files\"></FORM>"; 

}

### Actually moves files ###
sub movefile {

my ($cur_dir,$target_dir,$flag,$user,$filename,$newfilename,$high_number,@temp,$source_file,$target_file);

$user=param('user');
$cur_dir=param('cur_dir');
$target_dir=param('target_dir');
$high_number=param('high_number');

print header;
print "<HEAD><TITLE>Move Files</TITLE></HEAD>";
print "<BODY bgcolor=#9bff99>$font2 <b>$title</b></FONT><HR>";
print "<TABLE><TR BGCOLOR=\"494949\"><TD WIDTH=250>$font_w Source Directory</FONT></TD><TD WIDTH=250>$font_w Target Directory</FONT></TD></TR>";
print "<TR><TD>$cur_dir</TD><TD>$target_dir</TD></TR></TABLE><HR>";

if ($cur_dir eq $target_dir) {print "Source dir can't be same as target dir<BR>"} 
foreach (0..$high_number)
{

  $filename="filename".$_;
  $newfilename="newfilename".$_;
  @temp=("");
  if (param($filename) && $cur_dir ne $target_dir) 
   {

     $source_file=param($filename);
     $flag=0;
     foreach (@textmode_files) 
      {
        if ($source_file=~/($_)$/) {$flag=1;last;}
      } 
     open (FILE,$source_file) or error("Can't open file for reading");

     if ($flag != 1) {print "Using Binary Mode<BR>";binmode (FILE);}
     while (<FILE>) {push (@temp,$_)}
     close (FILE);

     ($target_file)=$source_file=~/.+\\(\w+\.*\w*)/;

     if (param($newfilename)) {$target_file=param($newfilename)}
     $target_file=$target_dir.$target_file;
     ($target_file)=$target_file=~/(\/.+)/;
     open (FILE,">$target_file") or error("Can't create target file $target_file");
     if ($flag != 1) {binmode (FILE)}
     print "Starting Moving: $source_file<BR>";
     foreach (@temp) {print FILE}
     close (FILE);
     ($source_file)=$source_file=~/(\/.+)/;
     unlink $source_file;
     print "Finished Moving: $target_file<BR>";  
   }
}
  print "<HR><FORM METHOD=\"post\" ACTION=\"http://$domain$cgipath\"><INPUT TYPE=\"hidden\" NAME=\"user\" VALUE=$user><INPUT TYPE=\"hidden\" NAME=\"cur_dir\" VALUE=\"$cur_dir\"><INPUT TYPE=\"submit\" NAME=\"showform\" VALUE=\"Main Menu\"><INPUT TYPE=\"submit\" NAME=\"choice\" VALUE=\"Move Files\"></FORM>"; 

}

### Actually edit the file ###
sub editfile {

my ($user,$cur_dir,$filename,$tempdata,@temp,@data);
$user=param('user');
$cur_dir=param('cur_dir');
$filename=param('filename0');

if (open (FILE,$filename)) {
 while (<FILE>) {push (@temp,$_)}
 close (FILE);
 foreach $tempdata (@temp) {
    $tempdata=~s/</&lt;/gi;
    $tempdata=~s/>/&gt;/gi;
    push (@data,$tempdata);
  }
}

print header;

print <<Editfiletop;
<HEAD><TITLE>Edit Files</TITLE></HEAD>
<BODY bgcolor=#9bff99>$font2 <b>$title</b></FONT><HR>
<TABLE><TR BGCOLOR="494949"><TD WIDTH=250>$font_w Current Directory</FONT></TD><TD WIDTH=250>$font_w Current File</FONT></TD></TR>
<TR><TD>$cur_dir</TD><TD>$filename</TD></TR></TABLE><HR>
Editfiletop
if (!(param('filename0'))) {
	print "$font1 No file specified.<BR>Please go back and select a file</FONT>";
    goto END;
}
print <<Editfilebottom;
$font2 Contents of Current File are given in form of text field</FONT><BR>
$font1 <b>Please make changes and press save button</b></FONT>

<FORM METHOD="post" ACTION="http://$domain$cgipath"><CENTER>

$font2 Enter New Filename (optional) </FONT><INPUT TYPE="text" NAME="newfilename" VALUE=""><BR>
<TEXTAREA NAME="filedata" COLS=90 ROWS=10>@data</TEXTAREA>
<INPUT TYPE="submit" NAME="Save" VALUE="Save">
<INPUT TYPE="hidden" NAME="user" VALUE=$user>
<INPUT TYPE="hidden" NAME="cur_dir" VALUE="$cur_dir">
<INPUT TYPE="hidden" NAME="filename" VALUE="$filename">
<INPUT TYPE="reset" NAME="Reset" VALUE="Reset">
<INPUT TYPE="submit" NAME="showform" VALUE="Main Menu">
</FORM>
Editfilebottom
END:
}

### Saves edited content ###

sub savefile {

my ($user,$cur_dir,$filename,$newfilename,$filedata);

$user=param('user');
$cur_dir=param('cur_dir');
$filename=param('filename');
$filedata=param('filedata');
if (param('newfilename')) {$filename=$cur_dir.param('newfilename')}
$filedata=~s/&lt;/</gi;
$filedata=~s/&gt;/>/gi;
print header;
print "<HEAD><TITLE>Edit Files</TITLE></HEAD>";
print <<Savefiletop;
<H1>$title</H1><HR>
<TABLE><TR BGCOLOR="494949"><TD WIDTH=250><FONT COLOR="FFFFFF">Current Directory</FONT></TD><TD WIDTH=250><FONT COLOR="FFFFFF">Current File</FONT></TD></TR>
<TR><TD>$cur_dir</TD><TD>$filename</TD></TR></TABLE><HR>
<H4>Started saving file $filename</H4>
Savefiletop
($filename)=$filename=~/(\/.+)/;
open (FILE,">$filename") or error("Can't write file $filename");
print FILE $filedata;
close FILE;
print "<H4>Finished Saving File $filename</H4><HR>";
print "<FORM METHOD=\"post\" ACTION=\"http://$domain$cgipath\"><INPUT TYPE=\"hidden\" NAME=\"user\" VALUE=$user><INPUT TYPE=\"hidden\" NAME=\"cur_dir\" VALUE=\"$cur_dir\"><INPUT TYPE=\"submit\" NAME=\"showform\" VALUE=\"Main Menu\"><INPUT TYPE=\"submit\" NAME=\"choice\" VALUE=\"Edit Files\"></FORM>"; 

}

### Actually renames the files ###
sub renamefile {

my ($cur_dir,$user,$filename,$newfilename,$high_number,@temp,$source_file,$target_file);


$user=param('user');
$cur_dir=param('cur_dir');
$high_number=param('high_number');
print header;
print "<HEAD><TITLE>Rename Files</TITLE></HEAD><BODY bgcolor=#9bff99>";
print "$font2 <b>$title</b></FONT><HR>";
print "<TABLE><TR BGCOLOR=\"494949\"><TD WIDTH=250>$font_w Current Directory</FONT></TD></TR>";
print "<TR><TD>$cur_dir</TD></TR></TABLE><HR>";

foreach (0..$high_number)
{

  $filename="filename".$_;
  $newfilename="newfilename".$_;
  if (param($filename) && param($newfilename)) 
   {

     $source_file=param($filename);
     
$target_file=param($newfilename);
     $target_file=$cur_dir.$target_file;
     print "$font1 Starting Renaming: $source_file<BR>";
     ($source_file)=$source_file=~/(\/.+)/;
     ($target_file)=$target_file=~/(\/.+)/;
     rename ($source_file,$target_file);
     print "Finished Renaming: $target_file<BR></FONT>";  

   }
}
  print "<HR><FORM METHOD=\"post\" ACTION=\"http://$domain$cgipath\"><INPUT TYPE=\"hidden\" NAME=\"user\" VALUE=$user><INPUT TYPE=\"hidden\" NAME=\"cur_dir\" VALUE=\"$cur_dir\"><INPUT TYPE=\"submit\" NAME=\"showform\" VALUE=\"Main Menu\"><INPUT TYPE=\"submit\" NAME=\"choice\" VALUE=\"Rename Files\"></FORM>"; 

}

### Actually deletes the files ###
sub deletefile {

my ($cur_dir,$user,$filename,$high_number,@temp,$source_file,$target_file);
 $user=param('user');
$cur_dir=param('cur_dir');
$high_number=param('high_number');

print header;
print "<HEAD><TITLE>Delete Files</TITLE></HEAD><BODY bgcolor=#9bff99>";
print "$font2 <b>$title</b></FONT><HR>";
print "<TABLE><TR BGCOLOR=\"494949\"><TD WIDTH=250>$font_w Current Directory</FONT></TD></TR>";
print "<TR><TD>$cur_dir</TD></TR></TABLE><HR>";

foreach (0..$high_number)
{

  $filename="filename".$_;

  if (param($filename)) 
   {

     $source_file=param($filename);

     print "$font1 Starting Deleting: $source_file<BR>";
     ($source_file)=$source_file=~/(\/.+)/;
     unlink $source_file;
     print "Finished Deleting: $source_file<BR></FONT>";  

   }
}
  print "<HR><FORM METHOD=\"post\" ACTION=\"http://$domain$cgipath\"><INPUT TYPE=\"hidden\" NAME=\"user\" VALUE=$user><INPUT TYPE=\"hidden\" NAME=\"cur_dir\" VALUE=\"$cur_dir\"><INPUT TYPE=\"submit\" NAME=\"showform\" VALUE=\"Main Menu\"><INPUT TYPE=\"submit\" NAME=\"choice\" VALUE=\"Delete Files\"></FORM>"; 

}
 
### Actually creates new directory ###
sub createnewdir {
my ($user,$cur_dir,$dirname);
$user=param('user');
$cur_dir=param('cur_dir');
$dirname=param('dirname');
$dirname=$cur_dir.$dirname;

print header;
print "<HEAD><TITLE>Create Directory</TITLE></HEAD><BODY bgcolor=#9bff99>";
print "$font2 <b>$title</b></FONT><HR>";
print "<TABLE><TR BGCOLOR=\"494949\"><TD WIDTH=250>$font_w Current Directory</FONT></TD></TR>";
print "<TR><TD>$cur_dir</TD></TR></TABLE><HR>";
if (param('dirname')) {
print "Now Create directory $dirname<BR>";
($dirname)=$dirname=~/(\/.+)/;
mkdir($dirname,0777);
print "Finished Creating directory $dirname<BR><HR>";
}
print "<HR><FORM METHOD=\"post\" ACTION=\"http://$domain$cgipath\"><INPUT TYPE=\"hidden\" NAME=\"user\" VALUE=$user><INPUT TYPE=\"hidden\" NAME=\"cur_dir\" VALUE=\"$cur_dir\"><INPUT TYPE=\"submit\" NAME=\"showform\" VALUE=\"Main Menu\"><INPUT TYPE=\"submit\" NAME=\"choice\" VALUE=\"Create Directory\"></FORM>"; 
}

### Shows the results of dir remove ###
sub removedir {

my ($user,$cur_dir,$dirname);
$user=param('user');
$cur_dir=param('cur_dir');
$dirname=param('dirname');
print header;
print "<HEAD><TITLE>Remove Directory</TITLE></HEAD><BODY bgcolor=#9bff99>";
print "$font2 <b>$title</b></FONT><HR>";
print "<TABLE><TR BGCOLOR=\"494949\"><TD WIDTH=250>$font_w Current Directory</FONT></TD><TD WIDTH=250>$font_w Deleted Directory</FONT></TD></TR>";
print "<TR><TD>$cur_dir</TD><TD>$dirname</TD></TR></TABLE><HR>";
if (param('dirname')) {
print "Starting deleting $dirname<BR>";
&deletedir($dirname);
print "Finished deleting $dirname<BR>";
}

print "<HR><FORM METHOD=\"post\" ACTION=\"http://$domain$cgipath\"><INPUT TYPE=\"hidden\" NAME=\"user\" VALUE=$user><INPUT TYPE=\"hidden\" NAME=\"cur_dir\" VALUE=\"$cur_dir\"><INPUT TYPE=\"submit\" NAME=\"showform\" VALUE=\"Main Menu\"><INPUT TYPE=\"submit\" NAME=\"choice\" VALUE=\"Remove Directory\"></FORM>"; 
&footer();
}

### Actually removes dir and sub-dirs ###
sub deletedir {
 my ($dirname,$parent_dir,@files,@sub_dirs);
$dirname=join('',@_);
($parent_dir,@sub_dirs)=&givedir($dirname);
@files=&givefiles($dirname);
foreach (@files) {($_)=$_=~/(\/.+)/;unlink $_;}
foreach (@sub_dirs) {&deletedir($_)}
($dirname)=$dirname=~/(\/.+)/;
rmdir $dirname;
}

### Outputs parent and sub-dirs of current dir ###
sub givedir {
my ($cur_dir,$parent_dir,$dir,@files,@sub_dirs);
$cur_dir=join('',@_);
($parent_dir)=$cur_dir=~/(.+\/)\w+/;
$dir=substr($cur_dir,0,-1); 

opendir (DIR,$dir) or error("Can't open directory $dir");
@files=readdir(DIR);
closedir(DIR);
foreach (@files)
{
  if (-d ($cur_dir.$_) && !($_=~/\.+/) ) {push (@sub_dirs,($cur_dir.$_."\/"))}
}
return ($parent_dir,@sub_dirs);
}

### Output list of file in current directory ###
sub givefiles {
 my ($cur_dir,$dir,$size,$type,$date,@temp,@files,@size,@type,@date);
$cur_dir=join('',@_);
$dir=substr($cur_dir,0,-1);
opendir (DIR,$dir) or error("Cannot open dir $dir");
@temp=readdir(DIR);
closedir(DIR);
foreach (@temp)
{
 if (-f ($cur_dir.$_)) {
    push (@files,($cur_dir.$_));
    $size=int((stat($cur_dir.$_))[7]/1000);
    unless ($size) {$size=1}
	push (@size,$size);
    $date=localtime((stat($cur_dir.$_))[9]);
	push (@date,$date);
    ($type)=/.+\.(\w+)/;
    if ($type=~/gif|jpg|jpeg|png/i) {$type="IMAGE"}
	elsif ($type=~/htm|html|shtml/i) {$type="HTML"}
	elsif ($type=~/txt|doc/i) {$type="TEXT"}
	elsif ($type=~/zip|arj|tar/i) {$type="ZIP"}
	elsif ($type=~/swf|mov|mpg|avi|qtw/i) {$type="MOVIE"}
	elsif ($type=~/wav|mp3|ra/i) {$type="AUDIO"}
	elsif ($type=~/dbf|mdb|fpt/i) {$type="DATA"}

	else {$type="FILE"}
	push (@type,$type);
  }
}
return (\@files,\@type,\@size,\@date);
}

### Show footer ###
sub footer {
  print "<DIV ALIGN=CENTER><BR><HR><BR>$font2 This script has been developed by Dr.Nagender Sangra of <a href=http://www.nagender.com/>nagender.com</a>";
  print " and can be found at <a href=http://my_perl_cgi_scripts.tripod.com/>my_perl_cgi_scripts.tripod.com</a></FONT><BR>";
  print "$font1 © Copyright 2000 Dr.Nagender Sangra , All rights reserved . For any suggestions or comments, please email at <a href=mailto:nagender\@vsnl.com>nagender\@vsnl.com</a> . </FONT></DIV>";
}

### Error Subrotine ###
sub error {
$error=join ('',@_);
print header;
print "Error Message: $error";
exit;
}
