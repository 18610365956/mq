<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page errorPage="error.jsp"%>
<html>
<head>
<title>�Ű�����RA��ʾϵͳ</title>
<link rel="stylesheet">
<script LANGUAGE="javaScript" src=function.js></script>
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
	<form name="free" action="queryResult.jsp" method="post">
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
							<td><input type="text" name="subjectDN" size="60"/></td>
						</tr>
						<!-- <tr>
							<td>��ҳ��ѯ-ÿҳ��¼��(Ĭ��Ϊ30�����Ϊ100)</td>
							<td><input type="text" name="pageSize" /></td>
						</tr>
						<tr>
							<td>��ҳ��ѯ-ҳ��</td>
							<td><input type="text" name="pageNum" /></td>
						</tr> -->
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