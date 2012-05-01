<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统参数设置--JEECMS安装向导</title>
<link href="img/style.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
	function formSubmit() {
		if (document.getElementById('dbPassword').value == '') {
			if (!confirm("您没有填写数据库密码，您确定数据库密码为空吗？")) {
				return false;
			}
		}
		document.getElementById('beforeSubmit').style.display = "none";
		document.getElementById('afterSubmit').style.display = "";
	}
</script>
</head>

<body>
<div class="regist-header box">
  <div class="rgheader box">
         <div class="brand fl">
            <h1 style="margin:0px;"><a href="${base}/"> ${site.name}</a></h1>
         </div>
     </div>
</div>
<form action="install_setup.svl" method="post"
	onsubmit="return formSubmit();">
	
	<div class="main">
  <table width="980" border="0" align="center" cellpadding="0" cellspacing="0" class="rg-tbg">
    <tr>
      <td height="76" align="center"><h2>2、系统参数设置（环境要求：jdk1.5或以上、tomcat5.5或以上、mysql5.0或以上）</h2>
	  <span style="color:#016dd0;">请设置系统相关参数</span></td>
    </tr>
    <tr>
      <td>
	  <table width="600" border="0" align="center" cellpadding="0" cellspacing="0"
	>
	
	<tr>
		<td align="center" valign="top">
		  <table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" style="border:1px solid #b5b5b5;">
			<tr>
				<td width="30%" height="30" align="right">数据库主机：</td>
				<td width="22%" align="left"><input name="dbHost" type="text"
					class="input" id="dbHost" value="127.0.0.1" /></td>
				<td align="left">数据库的ip地址，如果是本机无需改动</td>
			</tr>
			<tr>
				<td width="30%" height="30" align="right">数据库端口号：</td>
				<td width="22%" align="left"><input name="dbPort" type="text"
					class="input" id="dbPort" value="3306" /></td>
				<td align="left">数据库的端口号，一般无需改动</td>
			</tr>
			<tr>
				<td height="30" align="right">数据库名称：</td>
				<td align="left"><input name="dbName" type="text" class="input"
					id="dbName" value="jeecms_2012" /></td>
				<td align="left">&nbsp;</td>
			</tr>
			<tr>
				<td height="30" align="right">数据库用户：</td>
				<td align="left"><input name="dbUser" type="text" class="input"
					id="dbUser" value="root" /></td>
				<td align="left">&nbsp;</td>
			</tr>
			<tr>
				<td height="30" align="right">数据库密码：</td>
				<td align="left"><input name="dbPassword" type="text"
					class="input" id="dbPassword" /></td>
				<td align="left">安装数据库时输入的密码</td>
			</tr>
			<tr>
				<td height="30" align="right">是否创建数据库：</td>
				<td align="left"><input type="radio" name="isCreateDb"
					value="true" checked="checked" />是 <input type="radio"
					name="isCreateDb" value="false" />否</td>
				<td align="left">如果您自己手工创建了数据库，请选否</td>
			</tr>
			<tr>
				<td height="30" align="right">是否创建表：</td>
				<td align="left"><input type="radio" name="isCreateTable"
					value="true" checked="checked" />是 <input type="radio"
					name="isCreateTable" value="false" />否</td>
				<td align="left">如果您自己手工创建了表，请选否</td>
			</tr>
			<tr>
				<td height="30" align="right">是否初始化数据：</td>
				<td align="left"><input type="radio" name="isInitData"
					value="true" checked="checked" />是 <input type="radio"
					name="isInitData" value="false" />否</td>
				<td align="left">如果您自己手工初始化了数据，请选否</td>
			</tr>
			<tr>
				<td height="30" align="right">域名：</td>
				<td align="left"><input name="domain" type="text" class="input"
					value="<%=request.getServerName()%>" /></td>
				<td align="left">系统已经检测出您的域名，请勿改动</td>
			</tr>
			<tr>
				<td height="30" align="right">部署路径：</td>
				<td align="left"><input name="cxtPath" type="text"
					class="input" value="<%=request.getContextPath()%>" /></td>
				<td align="left">系统已经检测出您的部署路径，请勿改动</td>
			</tr>
			<tr>
				<td height="30" align="right">端口号：</td>
				<td align="left"><input name="port" type="text" class="input"
					value="<%=request.getServerPort()%>" /></td>
				<td align="left">系统已经检测出您的端口号，请勿改动</td>
			</tr>
		</table>	  </td>
	</tr>
	<tr>
		<td height="30" align="center"><span
			id="beforeSubmit">
		  <input type="submit" class="regist-submit"
			value=" 提 交 " />
</span> <span id="afterSubmit"
			style="display: none; color: red;">安装需要十几秒的时间，请您耐心等待...</span></td>
	</tr>
</table>
	  </td>
    </tr>
  </table>
</div>

<input type="hidden" name="dbFileName"
	value="/install/db/jeecms-db-2012.sql" /> <input type="hidden"
	name="initFileName" value="/install/db/jeecms-init-2012.sql" />
</form>
</body>
</html>
