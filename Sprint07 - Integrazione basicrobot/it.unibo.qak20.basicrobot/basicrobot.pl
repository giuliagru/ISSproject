%====================================================================================
% basicrobot description   
%====================================================================================
mqttBroker("localhost", "1883", "unibo/polar").
context(ctxbasicrobot, "localhost",  "TCP", "8020").
 qactor( datacleaner, ctxbasicrobot, "rx.dataCleaner").
  qactor( distancefilter, ctxbasicrobot, "rx.distanceFilter").
  qactor( basicrobot, ctxbasicrobot, "it.unibo.basicrobot.Basicrobot").
  qactor( envsonarhandler, ctxbasicrobot, "it.unibo.envsonarhandler.Envsonarhandler").
  qactor( perceiver, ctxbasicrobot, "it.unibo.perceiver.Perceiver").
msglogging.
