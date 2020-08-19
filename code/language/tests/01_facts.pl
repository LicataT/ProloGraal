% This test checks the following things :
%  - fact parsing
%  - fact resolving
consultstring(~fact.

hello(world).

hello(world2).

test(antlr).

multiples(arg1, arg2).

multiLine(arg3,
          arg4).

'this is also'('a fact').

numbers(test, 42, 21.21).

this(is(getting, a(bit(out, of, hand)))).~),
useinterpreter.