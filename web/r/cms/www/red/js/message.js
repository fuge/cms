function checkAll(){
	if($("#ids").attr("checked")){
		$("input[name='ids']").each(function(i){
			$(this).attr("checked","checked");
		 });
		}else{
			$("input[name='ids']").each(function(i){
				$(this).attr("checked","");
			 });
		}
}
//批量删除到垃圾箱
function toTrash(){
	var ids=new Array();
	$("input[name='ids']").each(function(i){
		if($(this).attr("checked")){
			ids.push($(this).val());
		}
	 });
	 if(ids.length>0){
		 if(!confirm("您确定要删除这些信息吗？")) {
				return;
			}
		 $.post("message_trash.jspx", {
				"ids" : ids
			}, function(data) {
				if(data.result){
					for(var i=0;i<ids.length;i++){
						$("#tr_"+ids[i]).remove();
						}
					 $("#msgDiv").html("您选择的站内信息已被移动到垃圾箱 ");
				}else{
					alert("请先登录");
				}
			}, "json");
		 }else{
			 $("#msgDiv").html("请先选择信息");
			 }
}
//单条信息到垃圾箱
function trash(id){
	 if(!confirm("您确定要删除该条信息吗？")) {
			return;
		}
	 $.post("message_trash.jspx", {
			"ids" : id
		}, function(data) {
			if(data.result){
				$("#jvForm").submit();
			}else{
				alert("请先登录");
			}
		}, "json");
}
function forward(){
	$("#jvForm").attr("action","message_forward.jspx");
	$("#jvForm").submit();
}
function empty(){
	var ids=new Array();
	$("input[name='ids']").each(function(i){
		if($(this).attr("checked")){
			ids.push($(this).val());
		}
	 });
	 if(ids.length>0){
		 if(!confirm("您确定要彻底删除这些信息吗？")) {
				return;
			}
		 $.post("message_empty.jspx", {
				"ids" : ids
			}, function(data) {
				if(data.result){
					for(var i=0;i<ids.length;i++){
						$("#tr_"+ids[i]).remove();
						}
					 $("#msgDiv").html("您选择的站内信息已被彻底删除 ");
				}else{
					alert("请先登录");
				}
			}, "json");
		 }else{
			 $("#msgDiv").html("请先选择信息");
			 }
}
function emptySingle(id){
	 if(!confirm("您确定要彻底删除该信息吗？")) {
			return;
		}
	 $.post("message_empty.jspx", {
			"ids" : id
		}, function(data) {
			if(data.result){
				$("#jvForm").submit();
			}else{
				alert("请先登录");
			}
		}, "json");
}
function revert(){
	var ids=new Array();
	$("input[name='ids']").each(function(i){
		if($(this).attr("checked")){
			ids.push($(this).val());
		}
	 });
	 if(ids.length>0){
		 if(!confirm("您确定要还原这些信息吗？")) {
				return;
			}
		 $.post("message_revert.jspx", {
				"ids" : ids
			}, function(data) {
				if(data.result){
					for(var i=0;i<ids.length;i++){
						$("#tr_"+ids[i]).remove();
						}
					 $("#msgDiv").html("您选择的站内信息已还原 ");
				}else{
					alert("请先登录");
				}
			}, "json");
		 }else{
			 $("#msgDiv").html("请先选择信息");
			 }
}
function toDraft(){
	$("#box").val(2);
	$("#nextUrl").val("message_mng.jspx?box=2");
	$("#jvForm").attr("action","message_save.jspx");
	$("#jvForm").submit();
}
function toSend(){
	$("#nextUrl").val("message_mng.jspx?box=1");
	$("#jvForm").attr("action","message_tosend.jspx");
	$("#jvForm").submit();
}
function reply(){
	$("#nextUrl").val("message_reply?box=1");
	$("#jvForm").attr("action","message_reply.jspx");
	$("#jvForm").submit();
}
function find_user(){
	var username=$("#username").val();
	$.post("message_findUser.jspx", {
		"username" : username
	}, function(data) {
		if(data.result){
			if(data.exist){
				$("#usernameMsg").html("没有此用户");
				$("#username").val("");
			}else{
				$("#usernameMsg").html("");
			}
		}else{
				alert("请先登录");
		}
	}, "json");
}