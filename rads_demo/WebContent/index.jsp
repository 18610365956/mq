<%@page import="cn.com.infosec.jce.provider.InfosecProvider"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@ page
	import="cn.com.infosec.netcert.rads61.resource.*,cn.com.infosec.netcert.rads61.*,java.io.*,java.security.Security,java.security.cert.*"%>
<%//@ page errorPage="error.jsp"%>
<%
	//���û�������
	SysProperty sysProperty = new SysProperty();
	// CA������IP
	sysProperty.setTransIP("10.20.61.141");
	// CA������Port
	sysProperty.setTransPort(22343);
	
	// ͨ��Э��
	sysProperty.setProtocolName("XML");

	// ��Կ����
	sysProperty.setKeyIdx("d:/ra");
	// ���ܻ�
	sysProperty.setHsmName("");
	// ��Կ����
	sysProperty.setPwd("123456");
	// �����㷨
	Security.addProvider(new InfosecProvider());
    sysProperty.setSignAlgName("SHA256withRSA");
    //sysProperty.setSignAlgName("SM3withSM2");
    

	// ǩ��֤��
	Certificate cert = CertificateFactory.getInstance("X.509","INFOSEC").generateCertificate(new FileInputStream(new File("d:/ra.cer")));
	sysProperty.setSignCert(cert);
	
	// ͨ��ͨ������ ssl��plain
   	sysProperty.setChanelEncryptName("plain");
	// ������ʾ���Ե����ͣ�Ŀǰ֧��"CN"��"EN",���Բ����ã�Ĭ��Ϊ�������������,
	sysProperty.setCountry("CN");
	CertManager.setSysProperty(sysProperty); 
	
	
	
%>
<html>
<head>
<title>�Ű�����RA��ʾϵͳ</title>
</head>
<frameset rows="112,*,20" cols="*" border="0" framespacing="0">
	<frame src="top.jsp" marginwidth="0" marginheight="0" frameborder="NO"
		scrolling="NO" name="top">
	<frameset cols="160,*" rows="*">
		<frame src="left.jsp" name="left" target="right" scrolling="default"
			marginwidth="0" marginheight="0" frameborder="NO">
		<frame
			src="right.jsp"
			name="right" scrolling="default" frameborder="NO" marginwidth="0"
			marginheight="0">
	</frameset>
	<frame src="bottom.jsp" name="bottom" marginwidth="0" marginheight="0"
		scrolling="NO" frameborder="NO">
</frameset>
<noframes>
	<body bgcolor="#FFFFFF">

	</body>
</noframes>
</html>
