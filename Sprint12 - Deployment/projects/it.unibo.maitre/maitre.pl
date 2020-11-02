%====================================================================================
% maitre description   
%====================================================================================
context(ctxmaitre, "localhost",  "TCP", "8039").
context(ctxrobot, "localhost",  "TCP", "8040").
 qactor( robot, ctxrobot, "external").
  qactor( maitre, ctxmaitre, "it.unibo.maitre.Maitre").
  qactor( console, ctxmaitre, "it.unibo.console.Console").
