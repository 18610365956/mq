<%@ page contentType="text/html;charset=gb2312"%>
<%@ page
	import="cn.com.infosec.netcert.framework.resource.*,cn.com.infosec.netcert.framework.*,java.util.*,java.text.*,cn.com.infosec.netcert.rads61.*"%>
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
	<form name="f" action="downCert_result.jsp" method="post">
		<table width="60%" border="0" cellpadding="2" class="top" cellspacing="1" align="center" bgcolor="#00CCCC">
			<tr bgcolor="#CAEEFF">
				<td align=center>
					<table width="90%">
						<tr>
							<td>�ο��ţ�</td>
							<td><input type="text" name="refno" /></td>
						</tr>
						<tr>
							<td>��Ȩ�룺</td>
							<td><input type="text" name="authcode" /></td>
						</tr>
						<tr>
							<td>P10��</td>
							<td><textarea id="p10" name="publicKey" cols=80 rows=10></textarea></td>
						</tr>
						<tr>
							<td>RSA��ʱ��Կ��</td>
							<td><textarea id="p10" name="tmpPubKey" cols=80 rows=10></textarea></td>
						</tr>
						<tr>
							<td colspan=2 align=center><input type=submit value="��  ��"></td>
						</tr>
					</table>

				</td>
			</tr>
		</table>
	</form>

<p>�����˫֤��RSAʹ��RC4���ԳƼ��ܣ�����ʹ��SM4���ԳƼ���</p>

</body>


</html>