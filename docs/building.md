# Building Soter

This is the section of the document you should read if you're looking to develop Soter.

## Dependencies

Building Soter right now, relies on some external dependencies to properly generate the Protocol Buffer and Thrift definitions for the Soter server process.

* [Google Protocol Buffers 3.9](https://github.com/protocolbuffers/protobuf/releases/tag/v3.9.2)
* [Apache Thrift 0.13.0](https://thrift.apache.org/download)

## Compiling

On OS X, you need to setup the path to your protoc compiler, like so:
(in Amazon Linux, this is not necessary because it's automatically put into PATH by the version set.)

This path will be wherever you compiled and installed protoc using the link above.

```
export PROTOC=/Users/meiklejc/workplace/new-soter/protobuf-3.9.2/src/protoc
```

You can build this package with either IntelliJ or brazil, but before you commit you should use
brazil to build it, which will run the test suite and ensure your changes are free from regressions.

```
brazil-build
```

A few things to note: if you run into an issue where the code is unable to find package -- which seems to occur after 
doing a Brazil -> Sync Workspace, or Brazil -> Sync Dependencies, you may need to reset the source and test directories.  

To do this, simply right click `src` and `tst` and follow Mark Directory As -> Sources Root, Test Sources Root, respectively.

## Regenerating Thrift Files

If you need to regenerate the Thrift files, use the following:

```
thrift -out src --gen java configuration/thrift/SoterService.thrift 
```