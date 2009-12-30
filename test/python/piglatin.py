#	piglatin.py				8/16/96  JJS
#
#	piglatin(s) -- returns piglatin of plaintext string s
#	depiglatin(s) -- returns plaintext of piglatin string s
#
#	WARNING: Export of this code to foreign countries may
#	         constitute a violation of national security.
#---------------------------------------------------------------

from string import splitfields, uppercase, lowercase, upper, lower

def append(w,suffix):
	if not w: return suffix
	elif w[-1] in uppercase: return w + upper(suffix)
	else: return w + lower(suffix)

def piglatin(s):
	out = ''
	for word in splitfields(s,' '):
		if word:
			# check for punctuation
			p = 0
			while word[p-1] not in uppercase+lowercase:
				p = p-1
			if p:
				punc = word[p:]
				word = word[:p]
			else: punc = ''

			# and pre-punc (e.g., parentheses)
			p = 0
			while word[p] not in uppercase+lowercase:
				p = p+1
			prepunc = word[:p]
			word = word[p:]

			# note capitalization
			if word[0] in uppercase: caps = 1
			else: caps = 0

			# remove up to the first vowel to make suffix
			p = 0
			while p < len(word) and word[p] not in "aoeuiyAOEUIY":
				p = p+1
			
			if not p:
				word = append( word, "yay" )
			else:
				word = append( word[p:], word[:p]+"ay")

			# recapitalize, as appropriate
			if caps: word = upper(word[0]) + word[1:]

			# restore any punctuation
			word = prepunc + word + punc

		out = out + ' ' + word
	return out[1:]

def depiglatin(s):
	out = ''
	for word in splitfields(s,' '):
		if word:
			# check for punctuation
			p = 0
			while word[p-1] not in uppercase+lowercase:
				p = p-1
			if p:
				punc = word[p:]
				word = word[:p]
			else: punc = ''

			# and pre-punc (e.g., parentheses)
			p = 0
			while word[p] not in uppercase+lowercase:
				p = p+1
			prepunc = word[:p]
			word = word[p:]

			# note capitalization
			if word[0] in uppercase: caps = 1
			else: caps = 0

			# find the suffix
			if lower(word[-3:]) == "yay":
				word = word[:-3]

			else:
				if lower(word[-4:]) in ['tray', 'stay', 
				  'shay', 'play', 'quay', 'thay', 'whay']:
					suflen = 4
				else: suflen = 3
				if word[1] in lowercase:
					word = lower(word[0]) + word[1:]
				word = word[-suflen:-2] + word[:-suflen]
				if caps: word = upper(word[0]) + word[1:]

			# restore any punctuation
			word = prepunc + word + punc

		out = out + ' ' + word
	return out[1:]

if __name__ == "__main__":
	print "PigLatin demo (enter 'quit' to quit)."
	s = ''
	while lower(s) != 'quit':
		s = raw_input(">")
		print "-->", piglatin(s)
		print "<--", depiglatin(piglatin(s))
 	print piglatin("Thank you, have a nice day!")
