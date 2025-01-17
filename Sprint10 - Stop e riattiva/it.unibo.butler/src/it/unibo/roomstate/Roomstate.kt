/* Generated by AN DISI Unibo */ 
package it.unibo.roomstate

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Roomstate ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('roomstate.pl')","") //set resVar	
						solve("consult('preparerequirements.pl')","") //set resVar	
					}
					 transition( edgeName="goto",targetState="waitStateChange", cond=doswitch() )
				}	 
				state("waitStateChange") { //this:State
					action { //it:State
					}
					 transition(edgeName="t131",targetState="exposeroomstate",cond=whenRequest("exposeroomstate"))
					transition(edgeName="t132",targetState="expose",cond=whenDispatch("expose"))
					transition(edgeName="t133",targetState="update",cond=whenDispatch("updateState"))
				}	 
				state("expose") { //this:State
					action { //it:State
						updateResourceRep( "exposeroomstate"  
						)
						solve("roomstate(L)","") //set resVar	
						 val List = getCurSol("L").toString()  
						emit("roomstate", "roomstate($List)" ) 
					}
					 transition( edgeName="goto",targetState="waitStateChange", cond=doswitch() )
				}	 
				state("exposeroomstate") { //this:State
					action { //it:State
						updateResourceRep( "exposeroomstate"  
						)
						if( checkMsgContent( Term.createTerm("exposeroomstate(Cmd)"), Term.createTerm("exposeroomstate(Cmd)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								solve("roomstate(St)","") //set resVar	
								if( currentSolution.isSuccess() ) {answer("exposeroomstate", "roomstate", "roomstate(${getCurSol("St")})"   )  
								}
								else
								{}
						}
					}
					 transition( edgeName="goto",targetState="waitStateChange", cond=doswitch() )
				}	 
				state("update") { //this:State
					action { //it:State
						updateResourceRep( "updateroomstate"  
						)
						if( checkMsgContent( Term.createTerm("updateState(Action)"), Term.createTerm("updateState(preparedishes)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								solve("dishes(D)","") //set resVar	
								
												var Sol = getCurSol("D")
								solve("action(take,pantry,dishes,$Sol)","") //set resVar	
						}
						if( checkMsgContent( Term.createTerm("updateState(Action)"), Term.createTerm("updateState(preparefood)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								solve("food(F)","") //set resVar	
								
												var Sol = getCurSol("F")
								solve("action(take,fridge,food,$Sol)","") //set resVar	
						}
						if( checkMsgContent( Term.createTerm("updateState(Action)"), Term.createTerm("updateState(action(A,B,C))"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								solve("${payloadArg(0)}","") //set resVar	
						}
						if( checkMsgContent( Term.createTerm("updateState(Action)"), Term.createTerm("updateState(action(A,B,C,D))"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								solve("${payloadArg(0)}","") //set resVar	
						}
						if( checkMsgContent( Term.createTerm("updateState(Action)"), Term.createTerm("updateState(action(A,B,C,D,E))"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								solve("${payloadArg(0)}","") //set resVar	
						}
						solve("roomstate(L)","") //set resVar	
						 val List = getCurSol("L").toString()  
						println("bella $List")
						emit("roomstate", "roomstate($List)" ) 
					}
					 transition( edgeName="goto",targetState="waitStateChange", cond=doswitch() )
				}	 
			}
		}
}
