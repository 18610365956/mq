<%@page contentType="text/html;charset=gb2312"%>
<%@ page errorPage="error.jsp"%>
<html>
<head>
<title>�Ű�����RA��ʾϵͳ</title>
<script LANGUAGE="javaScript" src=function.js></script>
<link rel="stylesheet">
</head>

<body background="qy_back.gif" leftmargin="0" topmargin="0"
	marginwidth="0" marginheight="0" bgcolor="#FFFFFF">
	<table width="100%" border="0" cellspacing="4" cellpadding="2">
		<tr bgcolor="#336699">
			<td>
				<div align="center" class="hei14">
					<b><font color="#FFFFFF">��ӭʹ���Ű�����RA��ʾϵͳ</font></b>
				</div>
			</td>
		</tr>
	</table>


	<p>&nbsp;</p>
	<form name="free" action="updateResult_date.jsp" method="post">
		<table width="60%" border="0" cellpadding="2" class="top"
			cellspacing="1" align="center" bgcolor="#00CCCC">
			<tr bgcolor="#CAEEFF">
				<td align=center>
					<table width="90%">
						<tr>
							<td>֤�����к�(16����)</td>
							<td><input type="text" name="certSN" size="30"/></td>
						</tr>
						<tr>
							<td>֤��ģ��</td>
							<td>
								<script language="javascript">
									template();
								</script>
							</td>
						</tr>
						<tr>
							<td>Subject DN</td>
							<td><input type="text" name="subjectDN" size="80"/></td>
						</tr>
						<tr>
							<td>֤����Ч�ڿ�ʼʱ��</td>
							<td><input type="text" name="NOTBEFORE" size="30"/>(yyyymmddhhmmss)</td>
						</tr>
						<tr>
							<td>֤����Ч�ڽ�ֹʱ��</td>
							<td><input type="text" name="NOTAFTER" size="30"/>(yyyymmddhhmmss)</td>
						</tr>
						<tr>
							<td colspan=2 align=center><input type=submit value="��  ��"></td>
						</tr>
					</table>

				</td>
			</tr>
		</table>
	</form>



</body>
</html>