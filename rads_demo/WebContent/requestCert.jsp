<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page errorPage="error.jsp"%>
<%
	String userid = request.getParameter("userid");
%>
<html>
	<head>
		<title>�Ű�����RA��ʾϵͳ</title>
		<script LANGUAGE="javaScript" src=function.js></script>
		<script language="javascript" type="text/javascript">
			function findObj(theObj, theDoc) {
				var p, i, foundObj;
				if (!theDoc)
					theDoc = document;
				if ((p = theObj.indexOf("?")) > 0 && parent.frames.length) {
					theDoc = parent.frames[theObj.substring(p + 1)].document;
					theObj = theObj.substring(0, p);
				}
				if (!(foundObj = theDoc[theObj]) && theDoc.all)
					foundObj = theDoc.all[theObj];
				for (i = 0; !foundObj && i < theDoc.forms.length; i++)
					foundObj = theDoc.forms[i][theObj];
				for (i = 0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++)
					foundObj = findObj(theObj, theDoc.layers[i].document);
				if (!foundObj && document.getElementById)
					foundObj = document.getElementById(theObj);
				return foundObj;
			}
			//���һ����������д��
			function AddSignRow() { //��ȡ���һ�е��кţ������txtTRLastIndex�ı����� 
				var txtTRLastIndex = findObj("txtTRLastIndex", document);
				var rowID = parseInt(txtTRLastIndex.value);
				var signFrame = findObj("SignFrame", document);
				//�����
				var newTR = signFrame.insertRow(signFrame.rows.length);
				newTR.id = "SignItem" + rowID; //�����:��һ��
				var newNameTD = newTR.insertCell(0);
				
				//���������
				newNameTD.innerHTML =  "<input name='oid" + rowID + "' id='oid" + rowID + "' type='text' />"; //�����:�ڶ���
				var newNameTD = newTR.insertCell(1);
				
				//���������
				newNameTD.innerHTML = "<input name='extType" + rowID + "' id='extType" + rowID + "' type='text' />"; //�����:������
				var newEmailTD = newTR.insertCell(2);
				
				//���������
				newEmailTD.innerHTML =  "<input name='extValue" + rowID + "' id='extValue" + rowID + "' type='text'/>"; //�����:ɾ����ť
				var newDeleteTD = newTR.insertCell(3);

				txtTRLastIndex.value = (rowID + 1).toString();
			}
		</script>

		<link rel="stylesheet">

	</head>

	<body background="qy_back.gif" leftmargin="0" topmargin="0"
		marginwidth="0" marginheight="0" bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="4" cellpadding="2">
			<tr bgcolor="#336699">
				<td>
					<div align="center" class="hei14">
						<b><font color="#FFFFFF">��ӭʹ���Ű�����RA��ʾϵͳ</font>
						</b>
					</div>
				</td>
			</tr>
		</table>


		<p>
			&nbsp;
		</p>
		<form name="free" action="requestResult.jsp" method="post">
			<input type="hidden" name="userid" value=<%=userid %> />
			<table border="0" cellpadding="2" class="top" cellspacing="1"
				align="center" bgcolor="#00CCCC">
				<tr bgcolor="#CAEEFF">
					<td align=center>
						<table>
							<tr>
								<td>
									֤����Ч��:
								</td>
								<td>
									<input type="text" name="validTime" />��
								</td>
							</tr>
							<tr>
								<td>
									֤��ģ��:
								</td>
								<td>
									<script language="javascript">
										template();
									</script>
								</td>
							</tr>
							<tr>
								<td>
									Subject DN:
								</td>
								<td>
									<input type="text" name="subjectDN" />
								</td>
							</tr>
							<tr>
								<td>
									�Զ�����չ:
								</td>
								<td>

									<table id="SignFrame">
										<tr id="trHeader">
											<td>
												��չOID
												<br>
												<input type="text" name="oid0" />
											</td>
											<td>
												��չ����
												<br>
												<input type="text" name="extType0" />
											</td>
											<td>
												��չֵ
												<br>
												<input type="text" name="extValue0" />
											</td>
										</tr>
									</table>

								</td>
								<td>
									<input type="button" name="Submit" value="�����չ" onClick=javascript:AddSignRow();>
								</td>

								<td>
									<input name='txtTRLastIndex' type='hidden' id='txtTRLastIndex' value="1" />
								</td>
							</tr>
							<tr>
								<td colspan=2 align=center>
									<input type=submit value="��  ��">
								</td>
							</tr>
						</table>

					</td>
				</tr>
			</table>
		</form>
	</body>
</html>