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
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t030",targetState="handleprepare",cond=whenDispatch("prepare"))
					transition(edgeName="t031",targetState="handleadd",cond=whenDispatch("add"))
					transition(edgeName="t032",targetState="handleclear",cond=whenDispatch("clear"))
					transition(edgeName="t033",targetState="handlestop",cond=whenDispatch("stop"))
					transition(edgeName="t034",targetState="handlestart",cond=whenDispatch("start"))
				}	 
				state("handleprepare") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("prepare(Cmd)"), Term.createTerm("prepare(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("prepare", "prepare(${payloadArg(0)})" ,"robotexecutor" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("handleadd") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("add(Foodcode,Quantity)"), Term.createTerm("add(Foodcode,Quantity)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("add", "add(${payloadArg(0)},${payloadArg(1)})" ,"robotexecutor" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("handleclear") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("clear(Cmd)"), Term.createTerm("clear(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("clear", "clear(${payloadArg(0)})" ,"robotexecutor" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("handlestart") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("start(Cmd)"), Term.createTerm("start(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("start", "start(${payloadArg(0)})" ,"robotmover" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("handlestop") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("stop(Cmd)"), Term.createTerm("stop(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("stop", "stop(${payloadArg(0)})" ,"robotmover" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
