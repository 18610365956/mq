<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="cn.com.infosec.netcert.rads61.exception.*"%>
<%@ page isErrorPage="true" %>
<%
	
	String errorNum = "";
	String errorMsg = "";
	if( (exception instanceof RAException)  ){
		RAException ex = (RAException)exception;
		errorNum = ex.getErrorNum();
		errorMsg = ex.getErrorMsg();
	} else if(exception instanceof CAException){
		CAException ex = (CAException)exception;
		errorNum = ex.getErrorNum();
		errorMsg = ex.getErrorMsg();
	}
	String message = exception.getMessage();
	exception.printStackTrace();
	
%>
<html>
<head>
<title>�Ű�����RA��ʾϵͳ</title>

<link rel="stylesheet" >

</head>

<body background="qy_back.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="#FFFFFF" >
<table width="100%" border="0" cellspacing="4" cellpadding="2">
  <tr bgcolor="#336699">
    <td>
      <div align="center" class="hei14"><b><font color="#FFFFFF">��ӭʹ���Ű�����RA��ʾϵͳ</font></b></div>
    </td>
  </tr>
</table>

<p>&nbsp;</p>
<table width="500" border="0" cellpadding="2" cellspacing="1" class="top" align="center" bgcolor="#00CCCC">
  <tr bgcolor="#CAEEFF">
    <td colspan="18">
      <div align="left"><% 
      if(!errorNum.equals(0)){
      		out.println("�������  --- " + errorNum );
      		out.print("<br>");
      		out.println("������Ϣ  --- " + errorMsg);
      		out.print("<br>");
      }else{
    	  out.print(message);
      } 
      %></div>
    </td>
  </tr>
</table>
</body>
</html>
