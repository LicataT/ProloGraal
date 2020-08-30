# ProloGraal
ProloGraal is a Truffle language for GraalVM that reproduce Prolog. It can be used by other GraalVM languages, but can also call other GraalVM languages by its unique predicate "polyglot_eval/3".

You can compile ProloGraal as a Maven Java project to get the language as a .jar file into "code/language/target". You can then put the .jar file into your GraalVM "languages" folder.

Use the "code/generate_parser" bash script to install an antlr file that you may also put into your GraalVM "languages" folder.

# Mentions
ProloGraal has been implemented during semester projects at the [School of Engineering and Architecture of Fribourg in Switzerland](https://www.heia-fr.ch/en/) by two students, Martin Spoto and Tony Licata, being myself. The projects have been supervised by the Professor Frédéric Bapst.
