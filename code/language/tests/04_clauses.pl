% This test checks the following things :
%  - clause parsing
%  - clause resolving
%  - recursive clauses resolving
%  - list concatenation
consultstring(~e(A, A).

test(A, R) :-
    test2(A, R),
    test3(A, 4).

test2(_, R) :-
    test3(R, 5).

test3(a, 4).
test3(b, 5).

concatDL('-'(Xs,Ys), '-'(Ys, DYs), '-'(Xs,DYs)).
% concatDL('-'([a, b, c | Xs], Xs), '-'([d, e, f | Ys], Ys), R).


fact([a, b, c, d]).
nested([a, [b, c, d], e, f]).
ending([a, b | [c, d, e | [f, g | h]]]).
variables([a, b, X], d, X).

% copied from 

% --- listConcat(?Xs, ?Ys, ?XsYs) : XsYs is the concatenation of Xs and Ys
listConcat([], A, A).
listConcat([X | Xs], Ys, [X | Rs]) :-
	listConcat(Xs, Ys, Rs).


% --- listSuffix(+L, ?S) : S is a suffix of L
listSuffix(L, L).
listSuffix([L | Ls], S) :-
	listSuffix(Ls, S).


% --- listRightTrim(+L, ?T) : T is L whithout its last elt
listRightTrim([_], []).
listRightTrim([X|Xs],[X|Ys]) :- listRightTrim(Xs,Ys).

% --- listPrefix(+L, ?P) : P is a prefix of L
listPrefix([X|Xs],[X|Ys]) :- listPrefix(Xs,Ys).
listPrefix(_, []).

%----------- S02 - Ex 4) ---------------------

%---    owns(+L, ?E) : E est un élément de la liste L
owns([X|_Xs], X).
owns([_Y|Ys], X) :- owns(Ys, X).

%----------- S02 - Ex 5) ---------------------

% --- listSublist(+L, ?S) : S is a sublist of L

% ----------------------
listSublist1(Ls, Ss) :- 
    listSuffix(Ps, Ss),
    listPrefix(Ls, Ps).

depthTest(A):-
    depthTest2(A).

depthTest2(A):-
    depthTest3(A).

depthTest3(a).

varAssign(A):-
    varAssign2(A).

varAssign2(B).
%----------- S02 - Ex 6) ---------------------

% --- unifyMany(?As, ?Bs) : As and Bs are list of terms (of same length),
%                           and each Aj is unified with Bj

unifyMany(A, A).~),
useinterpreter.