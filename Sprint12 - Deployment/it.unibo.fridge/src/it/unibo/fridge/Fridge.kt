/* Generated by AN DISI Unibo */ 
package it.unibo.fridge

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Fridge ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

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
						solve("consult('fridgestate.pl')","") //set resVar	
						println("Fridge | Ready")
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
						updateResourceRep( "waitcmd"  
						)
					}
					 transition(edgeName="t00",targetState="check",cond=whenRequest("check_food"))
					transition(edgeName="t01",targetState="exposestate",cond=whenRequest("exposefridgestate"))
					transition(edgeName="t02",targetState="take",cond=whenDispatch("take_food"))
					transition(edgeName="t03",targetState="take",cond=whenDispatch("take_foodlist"))
				}	 
				state("check") { //this:State
					action { //it:State
						updateResourceRep( "checkfood"  
						)
						delay(500) 
						if( checkMsgContent( Term.createTerm("check_food(Foodcode,Quantity)"), Term.createTerm("check_food(Foodcode,Quantity)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Fridge | checking presence of food: ${payloadArg(0)} quantity: ${payloadArg(1)}")
								solve("check(${payloadArg(0)},${payloadArg(1)})","") //set resVar	
								if( currentSolution.isSuccess() ) {answer("check_food", "food_available", "food_available(${payloadArg(0)},${payloadArg(1)})"   )  
								}
								else
								{answer("check_food", "food_notavailable", "food_notavailable(${payloadArg(0)},${payloadArg(1)})"   )  
								}
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("take") { //this:State
					action { //it:State
						updateResourceRep( "takefood"  
						)
						if( checkMsgContent( Term.createTerm("take_food(Foodcode,Quantity)"), Term.createTerm("take_food(Foodcode,Quantity)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								solve("action(take,fridge,food,${payloadArg(0)},${payloadArg(1)})","") //set resVar	
								if( currentSolution.isSuccess() ) {println("Fridge | ${payloadArg(1)} ${payloadArg(0)} taken")
								}
								else
								{}
						}
						if( checkMsgContent( Term.createTerm("take_foodlist(List)"), Term.createTerm("take_foodlist(L)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 var Food: String = payloadArg(0);
												 var Foodstring = Food.replace(",(","(")  
								solve("text_term($Foodstring,E)","") //set resVar	
								if( currentSolution.isSuccess() ) {var Sol = getCurSol("E");
														var Solstring = Sol.toString() 
								solve("action(take,fridge,food,$Sol)","") //set resVar	
								if( currentSolution.isSuccess() ) {println("Fridge | $Food taken")
								}
								else
								{}
								}
								else
								{}
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("exposestate") { //this:State
					action { //it:State
						updateResourceRep( "exposestate"  
						)
						solve("state(fridge,List)","") //set resVar	
						if( currentSolution.isSuccess() ) {println("Fridge | state: ${getCurSol("List")}")
						answer("exposefridgestate", "fridgestate", "fridgestate(${getCurSol("List")})"   )  
						}
						else
						{}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
