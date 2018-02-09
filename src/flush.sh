#script to remove class files and recompile
rm -rf server/*.class
rm -rf client/*.class

javac server/*
javac client/*
