/**
 objSel ��Ҫ��ʾ����key���̵������б�
 */
function sm2_listProvider(objSel){
	objSel.options.length = 0;
	var ss = document.getElementById('Enroll').sm_skf_listProvider().toArray();
	if(ss!=''){
		var arr = ss;
		for(var i=0; i<arr.length; i++){
			var op = document.createElement("OPTION");
			op.text = arr[i];
			objSel.add(op);
		}
	}
}

/**
 objSel ��Ҫ��ʾ����key���кŵ������б�
 dll �ǹ���key���̵�������
 */
function sm2_listKeys(objSel, dll){
	objSel.options.length = 0;
	var ss = document.getElementById('Enroll').sm_skf_listDevice(dll).toArray();
	if(ss!=''){
		for(var i=0; i<ss.length; i++){
			var DevInfo = Enroll.sm_skf_getDeviceInfo(dll, ss[i]).toArray()[0];
			var op = document.createElement("OPTION");
			op.text = DevInfo;
			objSel.add(op);
		}
	}
}

/**
 ��ҳ���onload�¼����á�������key�����б��͵�һ�����̵ĵ�ǰ�����豸��SN�б�
 objProviderSel: key���̵������б�
 objSnSel����һ�������µ�key�����л������б�
 */
function findSM2provider(objProviderSel, objSnSel){
	sm2_listProvider(objProviderSel);
	if(objProviderSel.options.length>0){
		var dll = objProviderSel.options[0].text
		sm2_listKeys(objSnSel, dll);
	}
}

/**
 ͨ��key���к�ָ��һ���豸���ڶ�һ��key�����κ�ҵ�����ǰ�������ȵ��ô˷�����
 provider��key����
 sn��key�����к�
 ����ֵ��0-�ɹ���1-ʧ�ܣ����ò���Ϊ�գ�
 */
function setDevice(provider, sn){
	if(provider==''){
		alert('��ָ��USB-Key����������������б�Ϊ�գ����������Ƿ���ȷ��װ��');
		return 1;
	}else if(sn==''){
		alert('��ָ��Ҫ������USB-Key�����USB-Key���к��б�Ϊ�գ���ȷ��USB-Key�Ƿ����ӣ��������ˢ�¡���ť��');
		return 1;
	}else{
		document.getElementById('Enroll').sm_skf_useDevice(provider,sn,true);
		return 0;
	}
}

/**
 ��������֤������
 dn����֤�������е����⣬�ַ����������ָ�����⣬����մ� ''
 */
function genCsr(dn){
	var p10 = document.getElementById('Enroll').sm_skf_genContainerP10(dn,'',true).toArray();
	return p10[1];
}

/**
 �������ǩ��֤�顣Ӧ�ڲ���֤���������ã������Ӧ��ǩ��֤�顣
 cerBase64��֤���base64��������
 */
function importSignCert(cerBase64){
	if(cerBase64==''){
		alert('֤�����ݲ���Ϊ��');
		return;
	}
	document.getElementById('Enroll').sm_skf_importSignX509Cert('',cerBase64);
	alert('����ǩ��֤��ɹ�');
}

/**
 ������ܼ���֤�顣Ӧ�ڲ���֤���������ã�����˫֤�еļ���֤�顣
 cerBase64��֤���base64��������
 priKey: GMT-0009 ��װ�ļ�����Կ��base64��������
 */
function importEncCert(cerBase64, priKey){
	if(cerBase64==''){
		alert('֤�����ݲ���Ϊ��');
		return;
	}else if(priKey==''){
		alert('��Կ���ݲ���Ϊ��');
		return;	
	}
	document.getElementById('Enroll').sm_skf_importEncX509Cert('',cerBase64, priKey);
	alert('�������֤��ɹ�');
}

/**
 ʹ��key�е�˽Կ�� SM3withSM2ǩ��������ǩ�������base64���ݡ�
 */
function signData(plain){
	if(plain==''){
		alert('ǩ��ԭ�Ĳ���Ϊ��');
		return '';	
	}
	return document.getElementById('Enroll').signData(plain);
}

/**
 ��ȡ�ض��ı�����
 */
function readData(){
	return document.getElementById('Enroll').readData();
}

/**
 д���ض��ı�����
 data��Ҫд�������
 */
function writeData(data){
	document.getElementById('Enroll').writeData(data);
	alert('USB-Keyд�����ݳɹ�');
}

/**
 ������ȫ����Ȩ��
 refNo���ο���
 authCode����Ȩ��
 p10��ǩ��֤���֤������
 */
function genSecureAuthCode(refNo, authCode, p10){
	return document.getElementById('Enroll').genSecureAuthCode(refNo, authCode, p10);
}