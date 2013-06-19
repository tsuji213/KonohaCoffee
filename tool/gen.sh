#!/bin/sh


if [ $# -eq 2 ];then
    echo "usage: $0 PEGFILE"
    exit 1
fi

filename=$1
konoha syntax/parser.k ${filename}
#mv ${filename}.action.java src/org/KonohaScript/Parser/
#mv *PegSyntax.java src/org/KonohaScript/Parser/
