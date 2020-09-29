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
						delay(2000) 
						emit("prepare_button", "prepare_button(clicked)" ) 
						delay(500) 
						emit("stop_button", "stop_button(clicked)" ) 
						delay(500) 
						emit("reactivate_button", "reactivate_button(clicked)" ) 
						delay(5000) 
						emit("add_button", "add_button(pizza)" ) 
						delay(5000) 
						emit("add_button", "add_button(pasta)" ) 
						delay(5000) 
						emit("clear_button", "clear_button(clicked)" ) 
					}
				}	 
			}
		}
}
