grammar JSON;

document: composite EOF;

composite: '{' NEWLINE? (property ((',' NEWLINE?) property)*)? NEWLINE? '}';

property: STRING ':' + element;

element:
    value=primitive #literal
    | array=collection #array
    | object=composite #object;

primitive:
    string=STRING #string
    | number=NUMBER #number
    | boolean=BOOLEAN #boolean
    | NULL #null;

collection: '[' NEWLINE? (element ((',' NEWLINE?) element)*)? NEWLINE? ']';

STRING: '"' (.*?) '"';

NUMBER: [+-]?([0-9]+)('.'?[0-9]+)?;

BOOLEAN: 'true' | 'false';

NULL: 'null';

NEWLINE: '\n' | '\r' | '\r\n';

WHITESPACE: (' ' | '\t')+ -> skip;