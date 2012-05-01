function toggleTo(base,img){
	if(img==0){
		 document.getElementById("framex").src=base+"/member/message_list.jspx?box=0";
	}else if(img==1){
		 document.getElementById("framex").src=base+"/member/message_list.jspx?box=1";	
	}else if(img==2){
		document.getElementById("framex").src=base+"/member/message_list.jspx?box=2";
	}else{
		 document.getElementById("framex").src=base+"/member/message_list.jspx?box=3";
	}
//  var ts=document.getElementById("tab").getElementsByTagName("div");
//  alert(ts);
  for(var i=0;i<4;i++){
	  if(img==i){
		 document.getElementById("msg"+i).className="keyupImg";
	  }else{
		 document.getElementById("msg"+i).className="beginImg";
	  }
   }
}
