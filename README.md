dk.ekot.memeater.MemEater
========

Quick hack for allocating memory and keeping it active.


For testing purposes
====================

When testing search performance, or any other kind of systems where the amount of free memory
left for disk caching is important, this tool makes ii simple to limit the amount of free
memory.

dk.ekot.memeater.MemEater allocates memory until the heap is full, then switches to active mode where it changes
random parts of the allocated memory continuously. This guards against the allocated memory
being swapped out by the operating system.

Build & Usage
=====

`mvn package`

`java -cp Mem



- Toke Eskildsen, / te@ekot.dk
