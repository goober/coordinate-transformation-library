# CoordinateTransformationLibrary
 
RT90, SWEREF99 and WGS84 coordinate transformation library
 
This library is a java port of the .NET library by Björn Sållarp. calculations are based entirely on the excellent javscript library by Arnold Andreassons.
 
Source: http://www.lantmateriet.se/geodesi/

Source: Arnold Andreasson, 2007. http://mellifica.se/konsult

Source: Björn Sållarp. 2009. http://blog.sallarp.com

Author: Mathias Åhsberg, 2009. http://github.com/goober/
 
License: http://www.apache.org/licenses/LICENSE-2.0

## Usage

The coordinate-transformation library is available from [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.goober%22%20a%3A%20%22coordinate-transformation-library%22).

```xml
<dependency>
    <groupId>com.github.goober</groupId>
    <artifactId>coordinate-transformation-library</artifactId>
    <version>1.1</version>
</dependency>
```
 
## Developer instructions

Generate Eclipse project:
```
./gradlew eclipse
```

To do a release you need to:

 * `./gradlew release`
 * Release the artifact from [staging](https://oss.sonatype.org/#stagingRepositories).

More information [here](http://central.sonatype.org/pages/releasing-the-deployment.html).
