/* Generated by AN DISI Unibo */ 
package it.unibo.robot

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Robot ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Butler | Ready")
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="to0",targetState="prepare",cond=whenDispatch("prepare"))
					transition(edgeName="to1",targetState="add",cond=whenDispatch("add"))
					transition(edgeName="to2",targetState="clear",cond=whenDispatch("clear"))
					transition(edgeName="to3",targetState="exposestate",cond=whenDispatch("robotstate"))
				}	 
				state("prepare") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("prepare(Cmd)"), Term.createTerm("prepare(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Butler | Requirement: PREPARE started")
								updateResourceRep( "preparestarted"  
								)
						}
						stateTimer = TimerActor("timer_prepare", 
							scope, context!!, "local_tout_robot_prepare", 2000.toLong() )
					}
					 transition(edgeName="t04",targetState="endAction",cond=whenTimeout("local_tout_robot_prepare"))   
					transition(edgeName="t05",targetState="stopped",cond=whenDispatch("stop"))
				}	 
				state("add") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("add(Foodcode,Quantity)"), Term.createTerm("add(Foodcode,Quantity)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Butler | Requirement: ADD started")
								updateResourceRep( "addstarted"  
								)
								println("Butler | Requirement: ADD check food")
								request("check_food", "check_food(${payloadArg(0)},${payloadArg(1)})" ,"fridge" )  
						}
					}
					 transition(edgeName="t06",targetState="reactivated",cond=whenReply("food_available"))
					transition(edgeName="t07",targetState="alertMaitre",cond=whenReply("food_notavailable"))
				}	 
				state("alertMaitre") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("food_notavailable(Foodcode,Quantity)"), Term.createTerm("food_notavailable(Foodcode,Quantity)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Butler | Requirement: ALERT maitre that food isn't available")
								forward("food_notavailable", "food_not_available(${payloadArg(0)},${payloadArg(1)})" ,"maitre" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("clear") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("clear(Cmd)"), Term.createTerm("clear(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Butler | Requirement: CLEAR started")
								updateResourceRep( "clearstarted"  
								)
						}
						stateTimer = TimerActor("timer_clear", 
							scope, context!!, "local_tout_robot_clear", 2000.toLong() )
					}
					 transition(edgeName="t08",targetState="endAction",cond=whenTimeout("local_tout_robot_clear"))   
					transition(edgeName="t09",targetState="stopped",cond=whenDispatch("stop"))
				}	 
				state("stopped") { //this:State
					action { //it:State
						updateResourceRep( "stopped"  
						)
						println("Butler | Requirement: STOP action")
					}
					 transition(edgeName="t010",targetState="reactivated",cond=whenDispatch("reactivate"))
				}	 
				state("reactivated") { //this:State
					action { //it:State
						updateResourceRep( "reactivated"  
						)
						println("Butler | Requirement: CONTINUE action")
						stateTimer = TimerActor("timer_reactivated", 
							scope, context!!, "local_tout_robot_reactivated", 2000.toLong() )
					}
					 transition(edgeName="t011",targetState="endAction",cond=whenTimeout("local_tout_robot_reactivated"))   
					transition(edgeName="t012",targetState="stopped",cond=whenDispatch("stop"))
				}	 
				state("endAction") { //this:State
					action { //it:State
						updateResourceRep( "ended"  
						)
						println("Butler | Requirement: END action")
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("exposestate") { //this:State
					action { //it:State
						updateResourceRep( "exposestate"  
						)
						println("Butler | Requirement: EXPOSE robot state")
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
