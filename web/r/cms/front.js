Cms = {};
/**
 * 浏览次数
 */
Cms.viewCount = function(base, contentId, viewId, commentId, downloadId, upId,
		downId) {
	viewId = viewId || "views";
	commentId = commentId || "comments";
	downloadId = downloadId || "downloads";
	upId = upId || "ups";
	downId = downId || "downs";
	$.getJSON(base + "/content_view.jspx", {
		contentId : contentId
	}, function(data) {
		if (data.length > 0) {
			$("#" + viewId).text(data[0]);
			$("#" + commentId).text(data[1]);
			$("#" + downloadId).text(data[2]);
			$("#" + upId).text(data[3]);
			$("#" + downId).text(data[4]);
		}
	});
}
/**
 * 站点流量统计
 */
Cms.siteFlow = function(base, page, referer) {
	$.getJSON(base + "/flow_statistic.jspx", {
		page : page,
		referer : referer
	});
}
/**
 * 成功返回true，失败返回false。
 */
Cms.up = function(base, contentId, origValue, upId) {
	upId = upId || "ups";
	var updown = $.cookie("_cms_updown_" + contentId);
	if (updown) {
		return false;
	}
	$.cookie("_cms_updown_" + contentId, "1");
	$.get(base + "/content_up.jspx", {
		"contentId" : contentId
	}, function(data) {
		$("#" + upId).text(origValue + 1);
	});
	return true;
}
/**
 * 成功返回true，失败返回false。
 */
Cms.down = function(base, contentId, origValue, downId) {
	downId = downId || "downs";
	var updown = $.cookie("_cms_updown_" + contentId);
	if (updown) {
		return false;
	}
	$.cookie("_cms_updown_" + contentId, "1");
	$.get(base + "/content_down.jspx", {
		contentId : contentId
	}, function(data) {
		$("#" + downId).text(origValue + 1);
	});
	return true;
}
/**
 * 获取附件地址
 */
Cms.attachment = function(base, contentId, n, prefix) {
	$.get(base + "/attachment_url.jspx", {
		"cid" : contentId,
		"n" : n
	}, function(data) {
		var url;
		for (var i = 0;i < n; i++) {
			url = base + "/attachment.jspx?cid=" + contentId + "&i=" + i
					+ data[i];
			$("#" + prefix + i).attr("href", url);
		}
	}, "json");
}
/**
 * 提交评论
 */
Cms.comment = function(callback, form) {
	form = form || "commentForm";
	$("#" + form).validate( {
		submitHandler : function(form) {
			$(form).ajaxSubmit( {
				"success" : callback,
				"dataType" : "json"
			});
		}
	});
}
/**
 * 获取评论列表
 * 
 * @param siteId
 * @param contentId
 * @param greatTo
 * @param recommend
 * @param orderBy
 * @param count
 */
Cms.commentList = function(base, c, options) {
	c = c || "commentListDiv";
	$("#" + c).load(base + "/comment_list.jspx", options);
}
/**
 * 客户端包含登录
 */
Cms.loginCsi = function(base, c, options) {
	c = c || "loginCsiDiv";
	$("#" + c).load(base + "/login_csi.jspx", options);
}
/**
 * 向上滚动js类
 */
Cms.UpRoller = function(rid, speed, isSleep, sleepTime, rollRows, rollSpan,
		unitHight) {
	this.speed = speed;
	this.rid = rid;
	this.isSleep = isSleep;
	this.sleepTime = sleepTime;
	this.rollRows = rollRows;
	this.rollSpan = rollSpan;
	this.unitHight = unitHight;
	this.proll = $('#roll-' + rid);
	this.prollOrig = $('#roll-orig-' + rid);
	this.prollCopy = $('#roll-copy-' + rid);
	// this.prollLine = $('#p-roll-line-'+rid);
	this.sleepCount = 0;
	this.prollCopy[0].innerHTML = this.prollOrig[0].innerHTML;
	var o = this;
	this.pevent = setInterval(function() {
		o.roll.call(o)
	}, this.speed);
}
Cms.UpRoller.prototype.roll = function() {
	if (this.proll[0].scrollTop > this.prollCopy[0].offsetHeight) {
		this.proll[0].scrollTop = this.rollSpan + 1;
	} else {
		if (this.proll[0].scrollTop % (this.unitHight * this.rollRows) == 0
				&& this.sleepCount <= this.sleepTime && this.isSleep) {
			this.sleepCount++;
			if (this.sleepCount >= this.sleepTime) {
				this.sleepCount = 0;
				this.proll[0].scrollTop += this.rollSpan;
			}
		} else {
			var modCount = (this.proll[0].scrollTop + this.rollSpan)
					% (this.unitHight * this.rollRows);
			if (modCount < this.rollSpan) {
				this.proll[0].scrollTop += this.rollSpan - modCount;
			} else {
				this.proll[0].scrollTop += this.rollSpan;
			}
		}
	}
}
Cms.LeftRoller = function(rid, speed, rollSpan) {
	this.rid = rid;
	this.speed = speed;
	this.rollSpan = rollSpan;
	this.proll = $('#roll-' + rid);
	this.prollOrig = $('#roll-orig-' + rid);
	this.prollCopy = $('#roll-copy-' + rid);
	this.prollCopy[0].innerHTML = this.prollOrig[0].innerHTML;
	var o = this;
	this.pevent = setInterval(function() {
		o.roll.call(o)
	}, this.speed);
}
Cms.LeftRoller.prototype.roll = function() {
	if (this.proll[0].scrollLeft > this.prollCopy[0].offsetWidth) {
		this.proll[0].scrollLeft = this.rollSpan + 1;
	} else {
		this.proll[0].scrollLeft += this.rollSpan;
	}
}
/**
 * 收藏信息
 */
Cms.collect = function(base, cId, operate,showSpanId,hideSpanId) {
	$.post(base + "/member/collect.jspx", {
		"cId" : cId,
		"operate" : operate
	}, function(data) {
		if(data.result){
			if(operate==1){
				alert("收藏成功！");
				$("#"+showSpanId).show();
				$("#"+hideSpanId).hide();
			}else{
				alert("取消收藏成功！");
				$("#"+showSpanId).hide();
				$("#"+hideSpanId).show();
			}
		}else{
			alert("请先登录");
		}
	}, "json");
}
/**
 * 列表取消收藏信息
 */
Cms.cmsCollect = function(base, cId, operate) {
	$.post(base + "/member/collect.jspx", {
		"cId" : cId,
		"operate" : operate
	}, function(data) {
		if(data.result){
			if(operate==1){
				alert("收藏成功！");
			}else{
				alert("取消收藏成功！");
				$("#tr_"+cId).remove();
			}
		}else{
			alert("请先登录");
		}
	}, "json");
}
/**
 * 检测是否已经收藏信息
 */
Cms.collectexist = function(base, cId,showSpanId,hideSpanId) {
	$.post(base + "/member/collect_exist.jspx", {
		"cId" : cId
	}, function(data) {
		if(data.result){
			$("#"+showSpanId).show();
			$("#"+hideSpanId).hide();
		}else{
			$("#"+showSpanId).hide();
			$("#"+hideSpanId).show();
		}
	}, "json");
}