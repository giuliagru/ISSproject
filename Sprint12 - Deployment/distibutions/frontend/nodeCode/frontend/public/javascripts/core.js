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
    document.getElementById('requestButton').disabled = true;
    document.getElementById('clearButton').disabled = true;
    if(controlFoodCodeFormat(foodcode)){
    	document.getElementById('foodcodeform').submit();    	
    }
    else
    	{
    	alert("Foodcode has to start with 'f' and contain 3 numbers");
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
    document.getElementById('requestButton').style.visibility = 'visible';
}

//Gestione dell'evento onClick del bottone clear
function onClickClear(){
    document.getElementById('addfoodbutton').disabled = true;
    document.getElementById('clearButton').disabled = true;
    document.getElementById('requestButton').disabled = true;
    document.getElementById('clearform').submit();
    
}

function onClickRequest(foodcode){
	 if(controlFoodCodeFormat(foodcode)){
		 document.getElementById('addfoodbutton').disabled = true;
		 document.getElementById('clearButton').disabled = true;	
	    }
	    else
	    	{
	    	alert("Foodcode has to start with 'f' and contain 3 numbers");
	    	}
	   // document.getElementById('foodcode-dropdown').value = "";  
}

function onClickStart(){
	document.getElementById('startform').submit();
	document.getElementById('startButton').disabled = true;
	document.getElementById('stopButton').disabled = false;
}

function onClickStop(){
	document.getElementById('stopform').submit();
	document.getElementById('stopButton').disabled = true;
	document.getElementById('startButton').disabled = false;
}
//Controlla il formato del food code
function controlFoodCodeFormat(str){
    var patternFoodCode = new RegExp("f[0-9]{3}$");
    if(!patternFoodCode.test(str)){
        console.log("Foodcode has to start with 'f' and contain 3 numbers");
        abilitaButton();
        return false;
    }
    return true;
}

//Gestione dei messaggi in arrivo dal backend
socket.on('message', function(v){ 
    console.log("RECEIVED " + v);
 if(v.indexOf("robotPosition:") >= 0){
     var sep = v.indexOf("(");
     var msgStr = v.substr(sep+1);
 	 var res = msgStr.replace('(',"").replace(",","");
     document.getElementById('robotPosition').innerHTML = res;
 }
 else if(v.indexOf("task:") >= 0){
     //currentTask: task(Executing the command PREPARE
     var sep = v.indexOf(":");
     var msgStr = v.substr(sep+1);
     if(msgStr === " Waiting"){
         document.getElementById('foodcode-dropdown').selectedIndex = 0;
         abilitaButton();
         document.getElementById('currentTask').innerHTML = "Waiting for the next command";
     }
     else if(msgStr === " EndSession"){
    	 EndClear();
    	 document.getElementById('currentTask').innerHTML = "Session ended";
     }
     else{
     document.getElementById('currentTask').innerHTML = msgStr;
     }
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
     } else if(msgStr === "endPrepare" || msgStr === "warning" || msgStr === "foodAdded"|| msgStr==="endAction"|| msgStr === "food_notavailable"){
         if(msgStr === "warning" || msgStr === "food_notavailable"){
        	 {
        	  alert("Fridge 𝗗𝗢𝗘𝗦 𝗡𝗢𝗧 𝗖𝗢𝗡𝗧𝗔𝗜𝗡 selected foodcode");
        	}
         }
         document.getElementById('foodcode-dropdown').selectedIndex = 0;
         abilitaButton();
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


//Canale 'dishwasherSocket'
socket.on('dishwasherSocket', function(v){
	 var sep = v.indexOf("*");
     var msgStr = v.substr(sep+1);
	 document.getElementById('textareaOutputDishWasher').innerHTML =  formatterStringObjects(msgStr);
});

//Canale 'fridgeSocket'
socket.on('fridgeSocket', function(v){
	 var sep = v.indexOf("*");
     var msgStr = v.substr(sep+1);
	 document.getElementById('textareaOutputFridge').innerHTML =  formatterStringObjects(msgStr);
});


//Canale 'request'
socket.on('request', function(v){
    if(v==="yes"){
    	alert("Fridge 𝗖𝗢𝗡𝗧𝗔𝗜𝗡𝗦 selected foodcode");
    }
    else{
    	alert("Fridge 𝗗𝗢𝗘𝗦 𝗡𝗢𝗧 𝗖𝗢𝗡𝗧𝗔𝗜𝗡 selected foodcode");
    }
    document.getElementById('foodcode-dropdown').selectedIndex = 0;
    abilitaButton();
});


function formatterStringObjects(str){
    return str.replace(")", "").replaceAll(", ","").replaceAll("-", "\n").replaceAll(",","\t\t")
}

function abilitaButton(){
    document.getElementById('addfoodbutton').disabled = false;
    document.getElementById('clearButton').disabled = false;
    document.getElementById('stopButton').disabled = false;		
    document.getElementById('requestButton').disabled = false;
   
}

function  EndClear(){
	 document.getElementById('stopButton').disabled = true;		
}

