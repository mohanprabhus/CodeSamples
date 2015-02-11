
grammar UUIDs;

uuidlist : (uuidline)+;
uuidline : uuid NEWLINE
         | uuid;

uuid : HEXNUM8'-'HEXNUM4'-'HEXNUM4'-'HEXNUM4'-'HEXNUM12;

HEXDIGIT : [a-f0-9];
HEXNUM4  : HEXDIGIT HEXDIGIT HEXDIGIT HEXDIGIT;  
HEXNUM8  : HEXNUM4 HEXNUM4;
HEXNUM12 : HEXNUM4 HEXNUM4 HEXNUM4;

NEWLINE: '\r'? '\n' ;