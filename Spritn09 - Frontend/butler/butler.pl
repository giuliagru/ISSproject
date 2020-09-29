%====================================================================================
% butler description   
%====================================================================================
mqttBroker("localhost", "1883", "unibo/qak/events").
context(ctxbasicrobot, "localhost",  "TCP", "8020").
context(ctxfridge, "127.0.0.1",  "TCP", "8092").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( fridge, ctxfridge, "it.unibo.fridge.Fridge").
  qactor( robotmover, ctxfridge, "it.unibo.robotmover.Robotmover").
  qactor( roomstate, ctxfridge, "it.unibo.roomstate.Roomstate").
  qactor( robotexecutor, ctxfridge, "it.unibo.robotexecutor.Robotexecutor").
  qactor( robot, ctxfridge, "it.unibo.robot.Robot").
  qactor( maitre, ctxfridge, "it.unibo.maitre.Maitre").
  qactor( user, ctxfridge, "it.unibo.user.User").
msglogging.
