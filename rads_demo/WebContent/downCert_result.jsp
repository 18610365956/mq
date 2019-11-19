<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page
	import="cn.com.infosec.netcert.framework.resource.*,cn.com.infosec.netcert.framework.*,java.util.*,java.text.*,cn.com.infosec.netcert.rads61.*"%>
<%@ page errorPage="error.jsp"%>

<%

	String refno = request.getParameter("refno");
	String authcode = request.getParameter("authcode");
	String publicKey = request.getParameter("publicKey");
	String tmpPubKey = request.getParameter("tmpPubKey");
	
	Properties p = new Properties();
	p.setProperty(PropertiesKeysRes.REFNO, refno);
	p.setProperty(PropertiesKeysRes.AUTHCODE, authcode);
	p.setProperty(PropertiesKeysRes.PUBLICKEY, publicKey);

	if(tmpPubKey!=null && tmpPubKey.length()>0){
		p.setProperty(PropertiesKeysRes.RSA_TMP_PUB_KEY, tmpPubKey);      //rsa��ʹ����������
    	p.setProperty(PropertiesKeysRes.KMC_KEYLEN, "1024");
    	p.setProperty(PropertiesKeysRes.RETSYMALG, "RC4");
    	p.setProperty(PropertiesKeysRes.RETURNTYPE, "P7CERT");
	}else{
    	p.setProperty(PropertiesKeysRes.KMC_KEYLEN, "256");
    	p.setProperty(PropertiesKeysRes.RETSYMALG, "SM4");
    	p.setProperty(PropertiesKeysRes.RETURNTYPE, "CERT");
	}

	CertManager manager = CertManager.getInstance();
	Properties pro = manager.downCert(p);
	String p7 = pro.getProperty(PropertiesKeysRes.P7DATA, "");
    String encCer = pro.getProperty(PropertiesKeysRes.P7DATA_ENC, "");  //����֤�鷵������
    String encPri = pro.getProperty(PropertiesKeysRes.ENCPRIVATEKEY, "");    //����֤��˽Կ
    String ukek = pro.getProperty(PropertiesKeysRes.TEMPUKEK, "");      //rsa��ʹ����������
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
					
					<textarea rows="30" cols="80" readonly="readonly">
----- ǩ��֤�� ��ʼ -----
<%=p7%>
----- ǩ��֤�� ���� -----
<%if(encCer.length()>0){%>
----- ����֤�� ��ʼ -----
<%=encCer%>
----- ����֤�� ���� -----
<%}%>
<%if(encPri.length()>0){%>
----- ����˽Կ ��ʼ -----
<%=encPri%>
----- ����˽Կ ���� -----
<%}%>
<%if(ukek.length()>0){%>
----- ������Կ ��ʼ -----
<%=ukek%>
----- ������Կ ���� -----
<%}%>
					</textarea>
				
				</div>
			</td>
		</tr>
	</table>
</body>
</html>