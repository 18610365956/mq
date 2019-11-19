/**
 * 
 */
package cn.com.rabbitmq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**  
* @ClassName: Producer  
* @Description:  ������
* @author wanghaixiang  
* @date 2019��9��19�� ����4:54:01   
*    
*/
public class Producer {
	private static Logger logger = LoggerFactory.getLogger(Producer.class);
	public static final String QUEUE_NAME = "confirm";
	public static final String USERNAME = "admin";
	public static final String PASSWORD = "admin";
	public static final String HOST = "10.20.61.141";
	public static final String VIRTUALHOST = "/";
	public static final int PORT = 5672;
	
	/**
	 * @throws TimeoutException 
	 * @throws IOException   
	*
	* @Description:  
	* @param args  
	* @return void    
	* @throws  
	*/
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(USERNAME);
		factory.setPassword(PASSWORD);
		factory.setHost(HOST);
		factory.setVirtualHost(VIRTUALHOST);
		factory.setPort(PORT);
		// ������
		Connection connection = factory.newConnection();
		// �����ŵ�
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = "This is a confirm message��";
		channel.confirmSelect();
		//��ÿ����Ϣ���м���������channel����Ӽ�����
		/*channel.addConfirmListener(new ConfirmListener() {
			
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				//���յ�broker���͹����� nack��Ϣ�ͻ����handleNack()������
				logger.info("nack: deliveryTag = " + deliveryTag + " multiple: " + multiple);
			}
			
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				// ���յ�broker���͹����� ack��Ϣ�ͻ����handleAck()������
				logger.info("nack: deliveryTag = " + deliveryTag + " multiple: " + multiple);
			}
		});*/
		//�첽ȷ��
		/**
		 * Channel�����ṩ��ConfirmListener()�ص�����ֻ����deliveryTag(��ǰChannel��������Ϣ���к�),��Ҫ�Լ�Ϊÿһ��Channelά��һ��unconfirm��
		 * ��Ϣ���кż��ϣ�ÿ��pubish���ݣ�������Ԫ��+1���ص�һ��handleAck()������unconfirm����ɾ����Ӧ��һ��(multiple=false) ���߶���(multiple=true)��¼��
		 */
		//δȷ�ϱ�ʶ
		/*SortedSet<Long> unconfirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
		channel.addConfirmListener(new ConfirmListener() {
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				logger.info("Nack, SeqNo: " + deliveryTag + ", multiple: " + multiple);
				if(multiple) {
					logger.info("> handleNack multiple!");//����
					unconfirmSet.headSet(deliveryTag + 1L).clear();
					
				}else {
					logger.info("> handleNack multiple false!");//����
					unconfirmSet.remove(deliveryTag);
				}
				
			}
			
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				if(multiple) {
					logger.info("> handleAck multiple!");//����
					unconfirmSet.headSet(deliveryTag + 1L).clear();
				}else {
					logger.info("> handleAck multiple false!");//����
					unconfirmSet.remove(deliveryTag);
				}
				
				
			}
		});*/
		final long start = System.currentTimeMillis();
		 //���ͳ־û���Ϣ
        for (int i = 0; i < 50; i++) {
            //��һ��������exchangeName(Ĭ������´�����������Ǵ���һ��""���ֵ�exchange��,
            //������������exchange�Ļ����ǿ���ֱ�ӽ��ò������ó�"",���������exchange�Ļ�
            //������Ҫ���ò������óɴ�����exchange������),�ڶ���������·�ɼ�
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_BASIC, (" Confirmģʽ�� ��" + (i + 1) + "����Ϣ"+message).getBytes());
            long nextSeqNo = channel.getNextPublishSeqNo();
//            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_BASIC, (" Confirmģʽ�� ��" + (i + 1) + "����Ϣ").getBytes());
//            unconfirmSet.add(nextSeqNo);
            
            //��ͨconfirm
            if (channel.waitForConfirms()) {
            	logger.info("���ͳɹ�!��"+(i+1)+" ����Ϣ"+message);
            }else{
                // ������Ϣ�ط�
            }
        }
        //����confirm
       /* if (channel.waitForConfirms()) {
        	logger.info("���ͳɹ�!");
        }else{
            // ������Ϣ�ط�
        }*/
        //logger.info("ִ��waitForConfirms�ķ�ʱ�䣺 "+(System.currentTimeMillis() -start) +"ms");
		channel.close();
		connection.close();

	}

}
