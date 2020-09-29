/* Generated by AN DISI Unibo */ 
package it.unibo.maitre

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Maitre ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Maitre is ready")
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t035",targetState="prepare",cond=whenEvent("prepare_button"))
					transition(edgeName="t036",targetState="add",cond=whenEvent("add_button"))
					transition(edgeName="t037",targetState="clear",cond=whenEvent("clear_button"))
					transition(edgeName="t038",targetState="exposefridge",cond=whenEvent("exposefridgestate_button"))
					transition(edgeName="t039",targetState="exposeroomstate",cond=whenEvent("exposeroomstate_button"))
					transition(edgeName="t040",targetState="handlerobotmsg",cond=whenDispatch("food_notavailable"))
					transition(edgeName="t041",targetState="stop",cond=whenEvent("stop_button"))
					transition(edgeName="t042",targetState="start",cond=whenEvent("start_button"))
				}	 
				state("prepare") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("prepare_button(Cmd)"), Term.createTerm("prepare_button(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("maitre preparing")
								forward("prepare", "prepare(ok)" ,"robot" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("add") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("add_button(Foodcode,Quantity)"), Term.createTerm("add_button(X,Y)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("maitre adding: ${payloadArg(0)}")
								forward("add", "add(${payloadArg(0)},${payloadArg(1)})" ,"robot" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("clear") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("clear_button(Cmd)"), Term.createTerm("clear_button(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("maitre clearing")
								forward("clear", "clear(ok)" ,"robot" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("handlerobotmsg") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("food_notavailable(Foodcode,Quantity)"), Term.createTerm("food_notavailable(Foodcode,Quantity)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("maitre knows ${payloadArg(0)} isn't available")
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("exposefridge") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("exposefridgestate_button(Cmd)"), Term.createTerm("exposefridgestate_button(Cmd)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								request("exposefridgestate", "exposefridgestate(Cmd)" ,"fridge" )  
						}
					}
					 transition(edgeName="t143",targetState="waitfridge",cond=whenReply("fridgestate"))
				}	 
				state("waitfridge") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("fridgestate(List)"), Term.createTerm("fridgestate(List)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("fridge state received: ${payloadArg(0)}")
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("exposeroomstate") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("exposeroomstate_button(Cmd)"), Term.createTerm("exposeroomstate_button(Cmd)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								request("exposeroomstate", "exposeroomstate(Cmd)" ,"roomstate" )  
						}
					}
					 transition(edgeName="t244",targetState="waitroom",cond=whenReply("roomstate"))
				}	 
				state("waitroom") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("roomstate(List)"), Term.createTerm("roomstate(List)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("room state received: ${payloadArg(0)}")
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("stop") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("stop_button(Cmd)"), Term.createTerm("stop_button(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("robot stopping")
								forward("stop", "stop(ok)" ,"robot" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("start") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("start_button(Cmd)"), Term.createTerm("start_button(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("robot reactivate")
								forward("start", "start(ok)" ,"robot" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
