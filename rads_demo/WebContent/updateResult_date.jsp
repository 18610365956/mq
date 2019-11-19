<%@ page contentType="text/html;charset=gb2312"%>
<%@ page
	import="cn.com.infosec.netcert.framework.resource.*,cn.com.infosec.netcert.framework.*,java.util.*,java.text.*,cn.com.infosec.netcert.rads61.*"%>
<%@ page errorPage="error.jsp"%>
<%
    request.setCharacterEncoding("gbk");
	String certSN = request.getParameter("certSN");
	String template = request.getParameter("template");
	String subjectDN = request.getParameter("subjectDN");
	
	String NOTBEFORE = request.getParameter("NOTBEFORE");
  String NOTAFTER = request.getParameter("NOTAFTER");
  
  SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
  long st = df.parse(NOTBEFORE).getTime();
	long end = df.parse(NOTAFTER).getTime();
  
	Properties pro = new Properties();
	
	pro.put(PropertiesKeysRes.TP, "2");
	pro.put(PropertiesKeysRes.NOTBEFORE, String.valueOf(st));
	pro.put(PropertiesKeysRes.NOTAFTER, String.valueOf(end));
	
	pro.put(PropertiesKeysRes.CERTSN, certSN);
	pro.put(PropertiesKeysRes.TEMPLATENAME, template);
	pro.put(PropertiesKeysRes.SUBJECTDN, subjectDN);
	//pro.put(PropertiesKeysRes.VALIDITYLEN, validTime);
	

	CertManager manager = CertManager.getInstance();
	Properties p = manager.updateCert(pro);

	String ref = p.getProperty(PropertiesKeysRes.REFNO);
	String authCode = p.getProperty(PropertiesKeysRes.AUTHCODE);

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
<%
							out.print("<br>");
							out.println("�ο��� ��" + ref);
							out.print("<br>");
							out.println("��Ȩ�� ��" + authCode);
						
	%>			
				</div>
			</td>
		</tr>
	</table>
</body>
</html>


