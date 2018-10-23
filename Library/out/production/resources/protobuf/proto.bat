set OUT=../../java
set def_cli_java=(login chat)
set def_internal_java=(internal)

for %%A in %def_internal_java% do (
    echo generate internal java code: %%A.proto
    protoc.exe --java_out=%OUT% ./internal/%%A.proto
)

pause