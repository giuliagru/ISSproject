/* Generated by AN DISI Unibo */ 
package it.unibo.console

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Console ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="waitUpdate", cond=doswitch() )
				}	 
				state("waitUpdate") { //this:State
					action { //it:State
					}
					 transition(edgeName="t036",targetState="updatefridge",cond=whenEvent("fridgestate"))
					transition(edgeName="t037",targetState="updateroom",cond=whenEvent("roomstate"))
					transition(edgeName="t038",targetState="updaterobotpos",cond=whenEvent("robotposition"))
					transition(edgeName="t039",targetState="updaterobotaction",cond=whenEvent("robotaction"))
					transition(edgeName="t040",targetState="updaterobotgoal",cond=whenEvent("robotgoal"))
					transition(edgeName="t041",targetState="handlerobotmsg",cond=whenEvent("food_notavailable"))
				}	 
				state("updatefridge") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("fridgestate(List)"), Term.createTerm("fridgestate(List)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
												
													val L = payloadArg(0)
													println(L)
													val my = myself							
													val t = itunibo.utils.prepareToSend(L)	
													val OnFridge = t["fridge"].toString()				
								utils.utilsFrontend.updateFrontend( my, OnFridge  )
						}
					}
					 transition( edgeName="goto",targetState="waitUpdate", cond=doswitch() )
				}	 
				state("updateroom") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("roomstate(List)"), Term.createTerm("roomstate(St)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
												
												val L = payloadArg(0)
												println(L)
												val my = myself							
												val t = itunibo.utils.prepareToSend(L)	
												val OnRobot = t["robot"].toString()
												val OnPantry = t["pantry"].toString()
												val OnDish = t["dishwasher"].toString()
												val OnTable = t["table"].toString()
								utils.utilsFrontend.updateFrontend( my, OnRobot  )
								utils.utilsFrontend.updateFrontend( my, OnPantry  )
								utils.utilsFrontend.updateFrontend( my, OnDish  )
								utils.utilsFrontend.updateFrontend( my, OnTable  )
						}
					}
					 transition( edgeName="goto",targetState="waitUpdate", cond=doswitch() )
				}	 
				state("updaterobotpos") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("robotposition(X)"), Term.createTerm("robotposition(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val my = myself	 
								utils.utilsFrontend.updatePositionFrontend( my, payloadArg(0)  )
						}
					}
					 transition( edgeName="goto",targetState="waitUpdate", cond=doswitch() )
				}	 
				state("updaterobotaction") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("robotaction(X)"), Term.createTerm("robotaction(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val my = myself	 
								utils.utilsFrontend.updateTaskFrontend( my, payloadArg(0)  )
						}
					}
					 transition( edgeName="goto",targetState="waitUpdate", cond=doswitch() )
				}	 
				state("updaterobotgoal") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("robotgoal(X)"), Term.createTerm("robotgoal(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val my = myself	 
								utils.utilsFrontend.updateGoalToFrontend( my, payloadArg(0)  )
						}
					}
					 transition( edgeName="goto",targetState="waitUpdate", cond=doswitch() )
				}	 
				state("handlerobotmsg") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("food_notavailable(Foodcode,Quantity)"), Term.createTerm("food_notavailable(Foodcode,Quantity)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Maitre | knows ${payloadArg(0)} ${payloadArg(1)} isn't available")
								 val my = myself	 
								utils.utilsFrontend.updateFrontend( my, "warning"  )
						}
					}
				}	 
			}
		}
}
