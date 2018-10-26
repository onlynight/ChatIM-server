set OUT=../../java
set def_external_java=(external)
set def_internal_java=(internal)
set def_protocol_java=(protocol_type)

for %%A in %def_protocol_java% do (
    echo generate protocol java code: %%A.proto
    protoc.exe --java_out=%OUT% ./protocol_type/%%A.proto
)

for %%A in %def_external_java% do (
    echo generate external java code: %%A.proto
    protoc.exe --java_out=%OUT% ./external/%%A.proto
)

for %%A in %def_internal_java% do (
    echo generate internal java code: %%A.proto
    protoc.exe --java_out=%OUT% ./internal/%%A.proto
)

pause