/* Generated by AN DISI Unibo */ 
package it.unibo.ctxrobot
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "robot.pl", "sysRules.pl"
	)
}

