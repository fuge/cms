//导航切换
function jeeNav(o,n){
	 o.className="selected";
	 var t;
	 var id;
	 var s;
	 for(var i=1;i<=n;i++){
	   id ="nav"+i;
	   t = document.getElementById(id);
	   s = document.getElementById("sub"+i);
	   if(id != o.id){
	   	 t.className="hide";
	   	 s.style.display = "none";
	   }
	   else{
			s.style.display = "block";
	   }
	 }
}

//投票验证
function check_votes(allowCount) {
var voteItems=document.getElementsByName('itemIds');
  var count = 0;
  for(var i=0;i<voteItems.length;i++)
  {
   if(voteItems[i].checked){
     count++;
   }
  }
  if(count==allowCount&&allowCount>1){
   for(var i=0;i<voteItems.length;i++){
     if(!voteItems[i].checked){
       voteItems[i].disabled = true;
     }
   }
   return true;
  }
  else{
    for(var i=0;i<voteItems.length;i++){
       voteItems[i].disabled = false;
    }
  }
  if(count==0){
	  alert("对不起，请至少选择一个投票项！");
	  return false;0
  }
  return true;
}