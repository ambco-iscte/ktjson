grammar JSON;

json: object EOF;

object: '{' NEWLINE? (property ((',' NEWLINE?) property)*)? NEWLINE? '}';

property: STRING ':' + element;

element: STRING | NUMBER | BOOLEAN | NULL | array | object;

array: '[' NEWLINE? (element ((',' NEWLINE?) element)*)? NEWLINE? ']';

STRING: '"' (.*?) '"';

NUMBER: [+-]?([0-9]+)('.'?[0-9]+)?;

BOOLEAN: 'true' | 'false';

NULL: 'null';

NEWLINE: '\n' | '\r' | '\r\n';

SPACE: ' '+ -> skip;