# CSV streaming Data Challenge
Solution proposal project for a data challenge proposed during an interview.

### Original track
Anonymized version of the challenge track can be found in CHALLENGE_TRACK.md file

#### Built with
 * [Scala](https://www.scala-lang.org/) - General purpose programming language
 * [SBT](https://www.scala-sbt.org/) - Dependency management
 * [Akka](https://akka.io/) - Toolkit for building highly concurrent, distributed, and resilient message-driven applications for Java and Scala
 (Streams and Alpakka)

## Usage Instructions
The application has been tested in a UNIX environment using JRE 10.

Needed artifacts to run:
- Applcation JAR
- Configuration file

### Building the application JAR
Once located in the project root dir, run
``` sbt assembly ``` .

The application jar will be generated in *target/scala-2.12/csv-streaming-data-challenge-assembly-0.1.jar*

### Compile your configuration file
Please use the file *src/main/resources/application.conf* as a reference.
Comments on the required fields are specified in the template.

### Run
Run of the application could be done by the following command
```
    java -jar </full/path/to/assembly.jar> --config-path </full/path/to/config/file>
```

Example:
```
java -jar csv-streaming-data-challenge-assembly-0.1.jar --config-path csv-streaming-data-challenge/src/main/resources/application.conf
 ```

An example of UDP producer can be found in *producer* folder and it could be run with
```
java -jar producer.jar --udp
```

## Known issues
There are some known issues or nice-to-have, not addressed because out of scope in the challenge.
- Automatic creation of the output directory
- Cleaning of the output directory before the run (or other way to manage existing files in output directory)
- ... many others :) ...

