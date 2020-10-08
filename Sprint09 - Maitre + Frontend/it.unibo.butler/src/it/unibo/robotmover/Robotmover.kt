/* Generated by AN DISI Unibo */ 
package it.unibo.robotmover

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Robotmover ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				val mapname = "roommap"
				
				//goal name and coordinates
				var Dest = ""
				var X = ""
				var Y = ""
				var Direction = ""
				
				var CurrentPlannedMove = ""
				
				//VIRTUAL ROBOT
				var StepTime   = 320
				var PauseTime  = 500L
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('roomcoordinates.pl')","") //set resVar	
						itunibo.planner.plannerUtil.initAI(  )
						itunibo.planner.plannerUtil.loadRoomMap( mapname  )
						itunibo.planner.plannerUtil.showMap(  )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
						updateResourceRep( "notmoving"  
						)
					}
					 transition(edgeName="t022",targetState="plan",cond=whenRequest("goto"))
				}	 
				state("plan") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("goto(Destination)"), Term.createTerm("goto(Destination)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								updateResourceRep( "planning"  
								)
								println("Robot mover	| going to ${payloadArg(0)}")
								 Dest = payloadArg(0)  
								solve("position($Dest,X,Y,D)","") //set resVar	
								
												X = getCurSol("X").toString()
												Y = getCurSol("Y").toString()
												Direction = getCurSol("D").toString()
												val DestString =":" + Dest +" (" + X + "," +Y+")"
								emit("robotgoal", "robotgoal($DestString)" ) 
								itunibo.planner.plannerUtil.planForGoal( X, Y  )
						}
					}
					 transition( edgeName="goto",targetState="execplan", cond=doswitch() )
				}	 
				state("execplan") { //this:State
					action { //it:State
						  CurrentPlannedMove = itunibo.planner.plannerUtil.getNextPlannedMove()  
					}
					 transition( edgeName="goto",targetState="execmove", cond=doswitchGuarded({ CurrentPlannedMove.length > 0  
					}) )
					transition( edgeName="goto",targetState="changedirection", cond=doswitchGuarded({! ( CurrentPlannedMove.length > 0  
					) }) )
				}	 
				state("execmove") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="forwardmove", cond=doswitchGuarded({ CurrentPlannedMove == "w"  
					}) )
					transition( edgeName="goto",targetState="othermove", cond=doswitchGuarded({! ( CurrentPlannedMove == "w"  
					) }) )
				}	 
				state("forwardmove") { //this:State
					action { //it:State
						updateResourceRep( "moving"  
						)
						delay(PauseTime)
						request("step", "step($StepTime)" ,"basicrobot" )  
					}
					 transition(edgeName="t323",targetState="handleStepOk",cond=whenReply("stepdone"))
					transition(edgeName="t324",targetState="hadleStepFail",cond=whenReply("stepfail"))
				}	 
				state("othermove") { //this:State
					action { //it:State
						updateResourceRep( "moving"  
						)
						delay(PauseTime)
						forward("cmd", "cmd($CurrentPlannedMove)" ,"basicrobot" ) 
						itunibo.planner.plannerUtil.updateMap( CurrentPlannedMove, ""  )
					}
					 transition( edgeName="goto",targetState="execplan", cond=doswitch() )
				}	 
				state("handleStepOk") { //this:State
					action { //it:State
						updateResourceRep( "stepdone"  
						)
						itunibo.planner.plannerUtil.updateMap( CurrentPlannedMove, ""  )
						 val Pos = ":" + itunibo.utils.formatPosition()  
						emit("robotposition", "robotposition($Pos)" ) 
					}
					 transition( edgeName="goto",targetState="execplan", cond=doswitch() )
				}	 
				state("hadleStepFail") { //this:State
					action { //it:State
						updateResourceRep( "stepfail"  
						)
						forward("cmd", "cmd(s)" ,"basicrobot" ) 
						delay(50) 
						forward("cmd", "cmd(h)" ,"basicrobot" ) 
					}
					 transition( edgeName="goto",targetState="plan", cond=doswitch() )
				}	 
				state("changedirection") { //this:State
					action { //it:State
						updateResourceRep( "changedirection"  
						)
						 var CurrentDir = itunibo.planner.plannerUtil.getDirection()  
						if(  CurrentDir != Direction  
						 ){ var RotationDir = itunibo.direction.directionUtil.changeDirection(CurrentDir, Direction)  
						if(  RotationDir == "180" 
						 ){delay(PauseTime)
						forward("cmd", "cmd(r)" ,"basicrobot" ) 
						itunibo.planner.plannerUtil.updateMap( "r", ""  )
						delay(PauseTime)
						forward("cmd", "cmd(r)" ,"basicrobot" ) 
						itunibo.planner.plannerUtil.updateMap( "r", ""  )
						}
						else
						 {delay(PauseTime)
						 forward("cmd", "cmd($RotationDir)" ,"basicrobot" ) 
						 itunibo.planner.plannerUtil.updateMap( RotationDir, ""  )
						 }
						 val Pos = itunibo.utils.formatPosition()  
						emit("robotposition", "robotposition($Pos)" ) 
						}
					}
					 transition( edgeName="goto",targetState="goalreached", cond=doswitch() )
				}	 
				state("goalreached") { //this:State
					action { //it:State
						updateResourceRep( "goalreached"  
						)
						itunibo.planner.plannerUtil.showMap(  )
						itunibo.planner.plannerUtil.showCurrentRobotState(  )
						answer("goto", "arrivedat", "arrivedat($Dest)"   )  
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
