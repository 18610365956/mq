<%@ page contentType="text/html;charset=gb2312"%>
<%@ page
	import="cn.com.infosec.netcert.framework.resource.*,cn.com.infosec.netcert.framework.*,java.util.*,java.text.*,cn.com.infosec.netcert.rads61.*"%>
<%@ page errorPage="error.jsp"%>
<%
    request.setCharacterEncoding("gbk");
	String certSN = request.getParameter("certSN");
	String template = request.getParameter("template");
	String subjectDN = request.getParameter("subjectDN");

	Properties pro = new Properties();
	pro.put(PropertiesKeysRes.CERTSN, certSN);
	pro.put(PropertiesKeysRes.TEMPLATENAME, template);
	pro.put(PropertiesKeysRes.SUBJECTDN, subjectDN);

	CertManager manager = CertManager.getInstance();
	boolean flag = manager.lockCert(pro);

	String result = "����ɹ�";

	if (!flag) {
		result = "����ʧ��";
	}
%>
<html>
<head>
<title>�Ű�����RA��ʾϵͳ</title>

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
	<table width="500" border="0" cellpadding="2" cellspacing="1"
		class="top" align="center" bgcolor="#00CCCC">
		<tr bgcolor="#CAEEFF">
			<td colspan="18">
				<div align="left">
					<%=result%>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>

