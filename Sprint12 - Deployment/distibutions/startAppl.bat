cd virtualRobot/node/WEnv/

start cmd /k startServer.bat

cd ../../../

cd basicrobot/bin/
start cmd /k it.unibo.qak20.basicrobot.bat

cd ../../
cd butler/bin/
start it.unibo.robot.bat

cd ../../
cd fridge/bin/
start it.unibo.fridge.bat

cd ../../
cd maitre/bin/
start it.unibo.maitre.bat

cd ../../
cd frontend/nodeCode/frontend
start startFrontEnd.bat
