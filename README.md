# InterpretersForLoxLanguage
#### Project Interpreters by book https://www.amazon.com/Crafting-Interpreters-Robert-Nystrom/dp/0990582930/ref=sr_1_1?crid=1NNUN92WSEDKV&keywords=crafting+interpreters&qid=1639167026&sprefix=crafting+inte%2Caps%2C315&sr=8-1

<img src="https://user-images.githubusercontent.com/76943234/147370210-4cf5cc33-2db3-4d66-9eac-9d61b1f91cb5.png"  width="336" align="right" hspace="20">

## Scanning
 A scanner (or lexer) takes in the linear stream of characters and chunks them
together into a series of something more akin to “words”. In programming
languages, each of these words is called a token. Some tokens are single
characters, like ( and ,. Others may be several characters long, like numbers
(123), string literals ("hi!"), and identifiers (min)
Some characters in a source file don’t actually mean anything. Whitespace is
often insignificant and comments, by definition, are ignored by the language.
The scanner usually discards these, leaving a clean sequence of meaningful tokens


<img src= https://user-images.githubusercontent.com/76943234/147370557-78bde369-0f8d-4ebc-b031-13234296415f.png width="560">
