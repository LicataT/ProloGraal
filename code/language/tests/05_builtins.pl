% This test checks the following things :
%  - builtins usage
consultstring(~isVar(X) :-
    var(X).

linkTest(X) :-
    test(X),
    var(X).

linkTest2(X) :-
    var(X),
    test(X).

test(3).

testWrite :-
    t(X),
    write(X),
    end(X).

t(a).
t(b).
t(c).
end(c).~),
useinterpreter.