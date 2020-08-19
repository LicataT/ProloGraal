% This test checks the following things :
%  - variables parsing
%  - fact resolving, with variables
%  - display of the resulting variables
consultstring(~
fact(X).

f(A, B).

e(A, A).

multifacts(a).
multifacts(b).
multifacts(c).

multiargs(a, b).
multiargs(c, d).

hello(world, X).

multiple(X, Y, X).

nested(X, one(Y, two(three(X)))).

occurs(A, f(A)).

indirection(X, X, X, X).~),
useinterpreter.