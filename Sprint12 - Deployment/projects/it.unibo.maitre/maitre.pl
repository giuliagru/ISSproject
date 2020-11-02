%====================================================================================
% maitre description   
%====================================================================================
mqttBroker("localhost", "1883", "unibo/qak/events").
context(ctxmaitre, "localhost",  "TCP", "8037").
context(ctxrobot, "127.0.0.1",  "TCP", "8038").
 qactor( robot, ctxrobot, "external").
  qactor( maitre, ctxmaitre, "it.unibo.maitre.Maitre").
  qactor( console, ctxmaitre, "it.unibo.console.Console").
