% This test checks the following things :
%  - redo command
%  - redo command with side-effects builtins
consultstring(~
multifacts(a).
multifacts(b).
multifacts(c).

% --- listPrefix(+L, ?P) : P is a prefix of L
listPrefix([X|Xs],[X|Ys]) :- listPrefix(Xs,Ys).
listPrefix(_, []).

testWrite :-
    t(X),
    write(X),
    end(X).

t(a).
t(b).
t(c).
t(d).
end(c).
end(d).

go :-
    write(10),
    a.

a.
a.
a.~),
useinterpreter.