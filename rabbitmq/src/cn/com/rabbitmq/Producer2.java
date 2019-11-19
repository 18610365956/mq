/**
 * 
 */
package cn.com.rabbitmq;

import java.io.IOException;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP.Basic.Publish;
import com.rabbitmq.client.AMQP.Exchange;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**  
* @ClassName: Producer2  
* @Description: ������
* @author wanghaixiang  
* @date 2019��9��25�� ����3:04:16   
*    
*/
public class Producer2 {
	/**  
	* ��־��ӡ
	*/  
	private static Logger logger = LoggerFactory.getLogger(Producer2.class);
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";
	private static final String HOST = "10.20.61.141";
	private static final String VIRTUALHOST = "/";
	private static final int PORT = 5672;
	
	
	//�������ֽ�����ģʽ
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
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchangeName = "direct_exchange";
		String exchangeName2 = "direct_exchange_2";
		String exchangeType = "direct";
		String queueName = "direct_queue";
		String queueName2 ="direct_queue_2";
		String routingKey = "direct";
		String routingKey2 = "direct_2";
		channel.confirmSelect();
		//����������
		/**
		 * exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, boolean internal,
         * Map<String, Object> arguments);����˵����
         * exchange: ����������
         * type: ���������� DIRECT("direct"), FANOUT("fanout"), TOPIC("topic"), HEADERS("headers");
         * durable:�Ƿ�־û���������Ϊtrueʱ������־û�����֮�Ƿǳ־û����־û��Ŀ��Խ����������̣��ڷ�����������ʱ�򲻻ᶪʧ��Ϣ��
         * autoDelete:�Ƿ��Զ�ɾ��������Ϊtrueʱ�����ʾ�Զ�ɾ������ɾ����ǰ����������һ��������֮�󶨣�֮������������������󶨵Ķ��ж���֮���һ�㶼����Ϊfalse��
         * internal:�Ƿ����ã��������Ϊtrue,���ʾ�������õĽ��������ͻ��˳����޷�ֱ�ӷ�����Ϣ������������У�ֻ��ͨ��������·�ɵ��������ķ�ʽ��
         * arguments:����һЩ�ṹ����������:alternate-exchange��
		 */
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
		channel.exchangeDeclare(exchangeName2, exchangeType, true, false, false, null);
		//��������
		channel.queueDeclare(queueName, false, false, false, null);
		channel.queueDeclare(queueName2, false, false, false, null);
		//����������а�
		channel.queueBind(queueName, exchangeName, routingKey);
		channel.queueBind(queueName2, exchangeName, routingKey2);
		String message = "hello ,this is direct_message!";
		String message2 = "hello ,this is direct2_message!";
		for (int i = 0; i < 50; i++) {
			channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_BASIC, message.getBytes());
			if(channel.waitForConfirms()) {
				logger.info("�� "+(i+1)+" ����Ϣ���ͳɹ���");
			}
		}
//		for (int i = 0; i < 6; i++) {
//			channel.basicPublish(exchangeName, routingKey2, MessageProperties.PERSISTENT_BASIC, message2.getBytes());
//		}
		
		
		
		
		
		
	}

}
