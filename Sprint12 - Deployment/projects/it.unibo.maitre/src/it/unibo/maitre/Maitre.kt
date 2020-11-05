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
						println("Maitre | Ready")
					}
					 transition( edgeName="goto",targetState="waitCmdPrepare", cond=doswitch() )
				}	 
				state("waitCmdPrepare") { //this:State
					action { //it:State
					}
					 transition(edgeName="t00",targetState="exposeroomstate",cond=whenEvent("exposeroomstate_button"))
					transition(edgeName="t01",targetState="prepare",cond=whenEvent("prepare_button"))
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t12",targetState="add",cond=whenEvent("add_button"))
					transition(edgeName="t13",targetState="clear",cond=whenEvent("clear_button"))
					transition(edgeName="t14",targetState="stoprobot",cond=whenEvent("stop_button"))
					transition(edgeName="t15",targetState="waitCmdPrepare",cond=whenEvent("endSession"))
				}	 
				state("exposeroomstate") { //this:State
					action { //it:State
						forward("expose", "expose(Cmd)" ,"robot" ) 
						request("exposefridgestate", "exposefridgestate(fridge)" ,"fridge" )  
					}
					 transition(edgeName="t16",targetState="updateFrontend",cond=whenReply("fridgestateReplay"))
				}	 
				state("updateFrontend") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("fridgestateReplay(List)"), Term.createTerm("fridgestateReplay(List)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Maitre | FRIDGE STATE RECIVED")
								 var Food: String = payloadArg(0);
											   var Foodstring = Food.replace(",(","(")  
								solve("text_term($Foodstring,Foods)","") //set resVar	
								emit("fridgestate", "fridgestate(${getCurSol("Foods")})" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmdPrepare", cond=doswitch() )
				}	 
				state("prepare") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("prepare_button(Cmd)"), Term.createTerm("prepare_button(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Maitre | PREPARE")
								updateResourceRep( "prepare"  
								)
								forward("prepare", "prepare(Cmd)" ,"robot" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("add") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("add_button(Foodcode,Quantity)"), Term.createTerm("add_button(Foodcode,Quantity)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Maitre | ADD")
								updateResourceRep( "add"  
								)
								forward("add", "add(${payloadArg(0)},${payloadArg(1)})" ,"robot" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("clear") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("clear_button(Cmd)"), Term.createTerm("clear_button(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Maitre | CLEAR")
								updateResourceRep( "clear"  
								)
								forward("clear", "clear(Cmd)" ,"robot" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("stoprobot") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("stop_button(Cmd)"), Term.createTerm("stop_button(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Maitre |  STOP")
								updateResourceRep( "stoprobot"  
								)
								forward("stop", "stop(Action)" ,"robot" ) 
						}
					}
					 transition(edgeName="t17",targetState="reactivaterobot",cond=whenEvent("reactivate_button"))
				}	 
				state("reactivaterobot") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("reactivate_button(Cmd)"), Term.createTerm("reactivate_button(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Maitre |  REACTIVATE")
								updateResourceRep( "reactivaterobot"  
								)
								forward("reactivate", "reactivate(Action)" ,"robot" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
