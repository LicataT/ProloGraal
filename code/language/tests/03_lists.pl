% This test checks the following things :
%  - list parsing
%  - list unifications, with tails
consultstring(~
test([a, b, c, d]).

unify(A, A).~),
useinterpreter.