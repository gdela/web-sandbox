Web Sandbox
===========

Sandbox to explore some web libraries and frameworks.

Attempt to do full duplex HTTP without WebSockets
-------------------------------------------------

Run one of the servers:

    mvn exec:java -Dexec.mainClass="pl.gdela.sandbox.web.server.JettyMain"
    mvn exec:java -Dexec.mainClass="pl.gdela.sandbox.web.server.GrizzlyMain"

Run one of the clients:

    mvn exec:java -Dexec.mainClass="pl.gdela.sandbox.web.client.TypicalClient"
    mvn exec:java -Dexec.mainClass="pl.gdela.sandbox.web.client.FullDuplexClient"

Observe which combinations works and which not.