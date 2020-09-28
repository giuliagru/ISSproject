var socket = io.connect();

//Attendo che il frontend si sia caricato completamente prima di popolare la select 
$(document).ready(function(){

	var data = '{"f000" :"pasta","f001":"pizza","f002":"fanta","f003":"chips","f004":"cola","f005":"birra","f006":"snack","f007":"popcorn","f008":"burger","f009":"pesce","f010":"riso","f011":"pane","f012":"cibo non contenuto","g008":"errore"}';

    var jsonArray = JSON.parse(data);
    var select = document.getElementById('foodcode-dropdown');
    var option = document.createElement('option');
    option.value = "resetSelect";
    option.text = "";
    select.add(option);

    Object.keys(jsonArray).forEach(function(key) {
        option = document.createElement('option');
        option.value = key;
        option.text = key + " - " + jsonArray[key];
        select.add(option);
    });
});

//Gestione dell'evento onClick del bottone addfood
function onClickAddFood(foodcode){
	document.getElementById('addfoodbutton').disabled = true;
    if(controlFoodCodeFormat(foodcode)){
    	document.getElementById('foodcodeform').submit();    	
    }
    else
    	{
    	alert("Il codice del cibo deve iniziare con 'f' e contenere 3 numeri");
    	}
    document.getElementById('foodcode-dropdown').value = "";
}

//Gestione dell'evento onClick del bottone prepare
function onClickPrepare(){
    document.getElementById('foodcodeString').style.visibility = 'visible';
    document.getElementById('stopButton').style.visibility = 'visible';
    document.getElementById('clearButton').style.visibility = 'visible';
    document.getElementById('startButton').style.visibility = 'visible';
    document.getElementById('addfoodbutton').style.visibility = 'visible';
    document.getElementById('foodcode-dropdown').style.visibility = 'visible';
    document.getElementById('prepareButton').style.visibility = 'hidden';
    document.getElementById('addfoodbutton').disabled = true;
}

//Gestione dell'evento onClick del bottone clear
function onClickClear(){
    document.getElementById('addfoodbutton').disabled = true;
    document.getElementById('clearButton').disabled = true;
    document.getElementById('clearform').submit();
    
}
//Controlla il formato del food code
function controlFoodCodeFormat(str){
    var patternFoodCode = new RegExp("f[0-9]{3}$");
    if(!patternFoodCode.test(str)){
        console.log("Il codice del cibo deve iniziare con 'f' e contenere 3 numeri");
        abilitaButton();
        return false;
    }
    return true;
}

//Gestione dei messaggi in arrivo dal backend
socket.on('message', function(v){ 
    console.log("RECEIVED " + v);
 if(v.indexOf("robotPosition:") >= 0){
     //robotPosition: pos([${getPosX()},${getPosY()}],${getDirection()}]
     var sep = v.indexOf("(");
     var msgStr = v.substr(sep+1);
     document.getElementById('robotPosition').innerHTML = msgStr;
 }
 else if(v.indexOf("task:") >= 0){
     //currentTask: task(Executing the command PREPARE
     var sep = v.indexOf(":");
     var msgStr = v.substr(sep+1);
     document.getElementById('currentTask').innerHTML = msgStr;
 }
 else if(v.indexOf("goal:") >= 0){
     //goal: goal(PANTRY | (5, 0)
     var sep = v.indexOf(":");
     var msgStr = v.substr(sep+1);
     document.getElementById('currentGoal').innerHTML = msgStr; 
 }
 

 else if(v.indexOf("taskCompleted") >= 0){
     //systemStarted: taskCompleted(systemStarted)
     var sep = v.indexOf(":");
     var msgStr = v.substr(0, sep);

     if(msgStr === "systemStarted"){ 
       //  systemStartedReceived();
     } else if(msgStr === "endPrepare" || msgStr === "warning" || msgStr === "foodAdded"){
         if(msgStr === "warning"){
        	 {
        	  alert("Il frigo non contiene il codice selezionato.");
        	}
         }
         document.getElementById('foodcode-dropdown').selectedIndex = 0;
         abilitaButton();
     } else if(msgStr === "endClear"){
    	 EndClear();
     }
     
     document.getElementById('currentTask').innerHTML = "Waiting for the next command";
     document.getElementById('currentGoal').innerHTML = "";
     document.getElementById('robotPosition').innerHTML = "";
 }
     

});

//Canale 'robotSocket'
socket.on('robotSocket', function(v){
	var sep = v.indexOf("*");
    var msgStr = v.substr(sep+1);
	 document.getElementById('textareaOutputRobot').innerHTML =  formatterStringObjects(msgStr);
});

//Canale 'tableSocket'
socket.on('tableSocket', function(v){
	 var sep = v.indexOf("*");
     var msgStr = v.substr(sep+1);
	 document.getElementById('textareaOutputTable').innerHTML =  formatterStringObjects(msgStr);
});

//Canale 'pantrySocket'
socket.on('pantrySocket', function(v){
	var sep = v.indexOf("*");
    var msgStr = v.substr(sep+1);
	 document.getElementById('textareaOutputPantry').innerHTML =  formatterStringObjects(msgStr);
});

//Canale 'fridgeSocket'
socket.on('fridgeSocket', function(v){
    document.getElementById('textareaOutputFridge').value = formatterStringExpose(v);
});

//Canale 'dishwasherSocket'
socket.on('dishwasherSocket', function(v){
	 var sep = v.indexOf("*");
     var msgStr = v.substr(sep+1);
	 document.getElementById('textareaOutputDishWasher').innerHTML =  formatterStringObjects(msgStr);
});



function formatterStringObjects(str){
    return str.replace(")", "").replaceAll(", ","").replaceAll("-", "\n").replaceAll(",","\t\t")
}

function abilitaButton(){
    document.getElementById('addfoodbutton').disabled = false;
    document.getElementById('clearButton').disabled = false;
    document.getElementById('stopButton').disabled = false;		
   
}

function  EndClear(){
	 document.getElementById('stopButton').disabled = true;		
}