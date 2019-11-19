<%@ page contentType="text/html;charset=gb2312"%>
<%@ page
	import="cn.com.infosec.netcert.rads61.resource.*,cn.com.infosec.netcert.rads61.*,java.io.*,java.security.Security,java.security.cert.*"%>
<%@ page errorPage="error.jsp"%>
<%
	//���û�������
	cn.com.infosec.netcert.rads61.SysProperty sysProperty = new cn.com.infosec.netcert.rads61.SysProperty();
	// CA������IP
	sysProperty.setTransIP("10.20.88.125");
	// CA������Port
	sysProperty.setTransPort(60001);
	// ���ܻ�
	sysProperty.setHsmName("");
	// ͨ��Э��
	sysProperty.setProtocolName("XML");
	// ��Կ����
	sysProperty.setKeyIdx("./cert/sm2/rads");
	// ��Կ����
	sysProperty.setPwd("11111111");
	// �����㷨
	Security.addProvider(new cn.com.infosec.jce.provider.InfosecProvider());
	
	sysProperty.setSignAlgName("SM3withSM2");
	//���䳬ʱ ��λ�� 
   	sysProperty.setTransTimeout(60);
    //���ӳ�ʱ ��λ��
  	sysProperty.setConnectTimeout(20);
	// ǩ��֤��
	Certificate cert = CertificateFactory.getInstance("X.509","INFOSEC")
	.generateCertificate(new FileInputStream("./cert/sm2/rads.cer"));
	sysProperty.setSignCert(cert);
	sysProperty.setSSL_TrustStore("./cert/sm2/ca.cer");
	/***
		//���ϴ˶ο��Լ�¼���͵�RADS����־
	***/
	Writer debugWriter  = new OutputStreamWriter(new FileOutputStream("./RADSDebug.log"));
	sysProperty.setDebugWriter(debugWriter);

	// ͨ��ͨ������ ssl��plain
	sysProperty.setChanelEncryptName("SSL");

	CertManager.setSysProperty(sysProperty); 
	
%>
<html>
<head>
<title>�Ű�����RA��ʾϵͳ</title>
<%@include file="header.inc"%>
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
