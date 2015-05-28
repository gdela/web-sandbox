Web Sandbox
===========

Sandbox to explore some web libraries and frameworks.

Servlet streaming input and output issue
----------------------------------------

Run server:

    mvn exec:java -Dexec.mainClass="pl.gdela.sandbox.web.server.JettyMain"

Run client:

    mvn exec:java -Dexec.mainClass="pl.gdela.sandbox.web.client.Client"

Observe that the client hangs.