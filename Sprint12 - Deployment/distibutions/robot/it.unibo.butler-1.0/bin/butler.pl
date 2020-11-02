%====================================================================================
% butler description   
%====================================================================================
mqttBroker("localhost", "1883", "unibo/qak/events").
context(ctxrobot, "localhost",  "TCP", "8038").
context(ctxbasicrobot, "::1",  "TCP", "8020").
context(ctxfridge, "127.0.0.1",  "TCP", "8039").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( fridge, ctxfridge, "external").
  qactor( robot, ctxrobot, "it.unibo.robot.Robot").
  qactor( robotexecutor, ctxrobot, "it.unibo.robotexecutor.Robotexecutor").
  qactor( robotmover, ctxrobot, "it.unibo.robotmover.Robotmover").
  qactor( roomstate, ctxrobot, "it.unibo.roomstate.Roomstate").
