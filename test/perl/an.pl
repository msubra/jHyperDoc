#!/usr/bin/perl

 ###If you use this on a local server like Indigo, change  ###
 ### the path to perl by removing the /usr/bin/            ###

	#####################################################
	#			Alpha-Numerical Generator   #
	#						    #
	#	Written by Scott Stolpmann 2-19-2000	    #
	#	This scripts is a patch for another program #
	#	which generates Alpha-numerical passwords for	
	#	user accounts at www.geomagnet.com. 	    #
	#						    #
	#	You can set the length of the 		    #
	#	password in the "for" statement.	    #
	#						    #
	#    	No explicit license or Rights apply.	    #
	#		Do as you please with this script   #
	#####################################################
	
	
 

# Change length of password $i<6; make it six characters long.

for($i=0; $i<6; $i++){

#  First random sequence determines if it will be 
#  Upper case, Low case, or Numbers.

$list =int(rand 3) +1;
if ($list ==1){

#  Char set 49 - 57 are numbers but I have omitted the 
#  number 0 to avoid user confusion with the letter O.
#  to remove the number 1 change line to $char =int(rand 7)+50;

$char =int(rand 7)+50;
}
if ($list ==2){

# Char set 65 - 90 are capital letters.

$char =int(rand 25)+65;
}
if ($list ==3) {

# Char set 97 - 122 are lower case letters.

$char =int(rand 25)+97;
}

# Converts number to ASCII equivelent.

$char =chr($char);
$password .= $char;
}
print "Content-type: text/html\n\n";
print "Human Password = $password<p>";

#  Converts human password to encrytped password.
#  Check out http://www.wolfy007.net/free-stuff/cgi/wAuthorize/
#  To see an excellent script which handles encpytion 
#  verification for password protection.


$encrypted = crypt($password, substr($password,0,2)); 

print "Encrypted Password = $encrypted<p>";


#  Just for fun you can use the extended ASCII characters with 
#  the below patch instead.


for($i=0; $i<8; $i++){
$char =int(rand 92)+33;
$char =chr($char);
$crazy .= "$char";
}
print "Crazy Password = <format>$crazy</format><p>";
print "Press refresh to get another set of passwords<br> You may want to remove the number 1 looks to much like the letter\" I or l \"";

#  That's it, have fun. I know I did.

