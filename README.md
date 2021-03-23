# Requirements
- Java 11
- Install telnet via powershell: dism /online /Enable-Feature /FeatureName:TelnetClient

# how to connect to a server
- telnet localhost 27015

# how to run a server
## from IDE 
- ServerRunner.java
## from jar
- command line command: `java -jar filename.jar`

# Build runnable jar
- command line command: `mvn assembly:assembly`