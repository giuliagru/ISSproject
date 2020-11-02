%====================================================================================
% fridge description   
%====================================================================================
mqttBroker("localhost", "1883", "unibo/qak/events").
context(ctxfridge, "localhost",  "TCP", "8039").
 qactor( fridge, ctxfridge, "it.unibo.fridge.Fridge").
