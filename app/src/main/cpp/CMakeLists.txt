
cmake_minimum_required(VERSION 3.4.1)

add_library( # Specifies the name of the library.
        pole

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        pole.cpp )

include_directories(src/main/cpp/)