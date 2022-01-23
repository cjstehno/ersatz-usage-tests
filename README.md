# Usage Testing Projects

Each of the projects under this directory is a stand-alone test project using one of the versions of the library to 
ensure that it is properly resolved with all the expected dependencies.

Before building any of these test projects, be sure that you have built and published the core and groovy libraries to 
your local maven repository so that these projects pick up your most current code.

You can run 

    ./gradlew clean build publishToMavenLocal

from the main project root directory if you are unsure.

> TBD... should I provide both maven and gradle versions?