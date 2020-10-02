/* Generated by AN DISI Unibo */ 
package it.unibo.user

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class User ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						stateTimer = TimerActor("timer_s0", 
							scope, context!!, "local_tout_user_s0", 3000.toLong() )
					}
					 transition(edgeName="t035",targetState="emitCmd",cond=whenTimeout("local_tout_user_s0"))   
					transition(edgeName="t036",targetState="end",cond=whenDispatch("test"))
				}	 
				state("emitCmd") { //this:State
					action { //it:State
						delay(2000) 
						emit("prepare_button", "prepare_button(clicked)" ) 
						delay(10000) 
						emit("add_button", "add_button(pizza,1)" ) 
						delay(10000) 
						emit("add_button", "add_button(cheese,1)" ) 
						delay(10000) 
						emit("clear_button", "clear_button(clicked)" ) 
					}
				}	 
				state("end") { //this:State
					action { //it:State
						terminate(0)
					}
				}	 
			}
		}
}
