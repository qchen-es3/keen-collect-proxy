#!/usr/bin/env bash


mv build.sbt build.sbt.bak
build=$(sed -n 's/\(version[ ]*:=[ ]*\)"\([0-9]*\)\.\([0-9]*\)\.\([0-9]*\).*"/\4/ p' build.sbt.bak)
build=$((build+1))
sed 's/\(version[ ]*:=[ ]*\)"\([0-9]*\)\.\([0-9]*\)\.\([0-9]*\).*"/\1"\2.\3.'$build'"/' build.sbt.bak > build.sbt
rm build.sbt.bak

activator universal:packageZipTarball