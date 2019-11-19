/**
 * 
 */
package cn.com.rabbitmq;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
/**
 * ���Ŷ��У�
 * ��Ϣ������ŵļ��������
 * 1.��Ϣ���ܾ�(basicNack����basicReject����requeue=false)
 * 2.��ϢTTL����
 * 3.���дﵽ��󳤶�
 * 
 * ����Ϣ��һ�������б������֮�󣬻ᱻ����publish������һ��������Exchange�����Exchange�������Ŷ���(DLX)
 * DLXҲ��������Exchange����һ���exchangeû��ʲô��ͬ�� �����Ա��κεĶ���ָ����ʵ���Ͼ������ö��е�����
 * �����������������ʱ��rabbitmq���Զ��Ľ������Ϣ���·��͵����õ�Exchange��ȥ��������·�ɵ���һ�����С�
 * 
 * �������̣�
 * 1.��Ϣ���͵���ͨ������normal_exchange,����·�ɼ�ת������֮�󶨵���������normal_queue,(�������������������Ŷ��в���)
 * 2.�����͵��ö��е���Ϣ������ź󣬸���Ϣ���������������õ����Ŷ�����ʵ�������Ž�����dlx_exchangeת�������Ž������ϡ�
 * 3.���Ž�����������֮�󶨵����Ŷ���·�ɼ�����������Ϣת�������Ŷ����ϡ�
 */
/**  
* @ClassName: Producer3  
* @Description:   
* @author wanghaixiang  
* @date 2019��10��14�� ����2:24:40   
*    
*/
public class Producer3 {
	private static Logger logger = LoggerFactory.getLogger(Producer.class);
	public  static String queue1 = "info_error";
	public  static String queue2 = "info_warning";
	public  static String queue3 = "info_debug";
	public  static String queue4 = "no_info";
	public  static String queue5 = "queue_ttl";
	public  static String queue6 = "queue_ttl_message";
	public 	static String test_queue = "test_queue";
	public 	static String queue_nottl = "queue_nottl";
	public 	static String mirror_queue = "mirror_queue";
	/**  
	* ���Ŷ���
	*/  
	public static String queue_dlx = "queue_dlx";
	public static String exchange_dlx = "exchange_dlx";
	public static String routingKey_dlx = "routingKey_dlx.*";
	public static String exchangeType ="topic";
	
	public static final String USERNAME = "admin";
	public static final String PASSWORD = "admin";
	public static final String HOST = "10.20.61.141";
	public static final String VIRTUALHOST = "/";
	public static final int PORT = 5673;

	/**
	*
	* @Description:  
	* @param args  
	* @return void    
	* @throws  
	*/
	public static void main(String[] args) throws Exception{
		/*ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(USERNAME);
		factory.setPassword(PASSWORD);
		factory.setHost(HOST);
		factory.setVirtualHost(VIRTUALHOST);
		factory.setPort(PORT);
		// ������
		Connection connection = factory.newConnection();
		// �����ŵ�
		Channel channel = connection.createChannel();
		Map<String, Object> map = new HashMap<>();
		map.put("x-message-ttl", 30000);
		map.put("x-dead-letter-exchange", exchange_dlx);
		map.put("x-dead-letter-routing-key", "routingKey_dlx.add");
		channel.queueDeclare(queue_nottl, true, false, false, null);
		channel.queueBind(queue_nottl, "direct_exchange_2", "direct_2");*/
//		channel.queueDeclare(test_queue, true, false,false,map);
//		channel.queueBind(test_queue, "direct_exchange", "direct");
//		channel.exchangeDeclare(exchange_dlx, exchangeType, true, false, false, null);
//		Map<String, Object> arguments = new HashMap<String, Object>();
//		//���ö��е����Ŷ���(��ָ��������)
//		arguments.put("x-dead-letter-exchange", exchange_dlx);
//		channel.queueDeclare(queue_dlx, true, false, false, arguments);
//		channel.queueBind(queue_dlx, exchange_dlx, routingKey_dlx);
		
		//channel.queueDelete(queue4);
		//channel.queueDeclare(queue1, true, false, false, null);
		//channel.queueDeclare(queue2, true, false, false, null);
		//channel.queueDeclare(queue3, true, false, false, null);
		//channel.queueDeclare(queue4, true, false, false, null);
		//����ttl����
		//Map<String, Object> arguments = new HashMap<String, Object>();
		/**
		 * ��Ϣ�����ʱ��ʼ��ʱ��ֻҪ�������еĳ�ʱʱ������,��Ϣ�Զ����
		 */
		//arguments.put("x-message-ttl", 10000);//����Ϣ�ڶ����ܴ��ڵ�ʱ��
		//ͨ����������������Ϣttl�ķ�������queue.declare�����м���x-message-ttl��������λΪms
		//channel.queueDeclare(queue5, true, false, false, arguments);
		/*String exchangeName = "direct_exchange";
		String exchangeName2 = "direct_exchange_2";
		String message1 = "This is a error message��";
		String message2 = "This is a warning message��";
		String message3 = "This is a debug message��";
		String message4 = "This is a no_info message��";
		String message5 = "This is a ttl message��";
		String message6 = "This is a ttl_message message��";
		String message7 = "This is a ttl_tlx message��";
		String message8 = "This is a no_ttl message��";*/
		//channel.queueBind(queue1, exchangeName, "errorBig");
//		channel.queueBind(queue2, exchangeName2, "warning");
//		channel.queueBind(queue3, exchangeName2, "debug");
//		channel.queueBind(queue4, exchangeName2, "no_info");
		//channel.queueBind(queue5, exchangeName, "ttl");
		//channel.queueBind(queue6, exchangeName2, "ttl_message");
		//����������ȷ��ģʽ
		/*channel.confirmSelect();
		*//**
		 * ���Ծ������
		 *//*
		for (int i = 0; i < 1; i++) {
			AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
			builder.deliveryMode(2);
			builder.expiration("15000");
			BasicProperties properties = builder.build();
			channel.basicPublish("direct_exchange_2", "direct_2", properties, message8.getBytes());
			// channel.basicPublish(exchangeName, "error", MessageProperties.PERSISTENT_BASIC, (" ����ģʽ�� ��" + (i + 1) + "����Ϣ"+"test error message").getBytes());
			//channel.basicPublish(exchangeName2, "no_info", MessageProperties.PERSISTENT_BASIC, message4.getBytes());
			//channel.basicPublish(exchangeName, "ttl", MessageProperties.PERSISTENT_BASIC, message5.getBytes());
			*//**
			 * ������Ϣ����
			 *//*
			AMQP.BasicProperties.Builder builder =new AMQP.BasicProperties.Builder();
			builder.deliveryMode(2);//�־û���Ϣ
			//��Ϣ�Ĺ���ʱ��
			builder.expiration("30000");
			BasicProperties properties = builder.build();
			
			//channel.basicPublish(exchangeName2, "ttl_message", properties, message2.getBytes());
			if(channel.waitForConfirms()) {
					logger.info("no_ttl������Ϣ���ͳɹ���"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}
			 //channel.basicPublish(exchangeName2, "warning", MessageProperties.PERSISTENT_BASIC, (" Confirmģʽ�� ��" + (i + 1) + "����Ϣ"+message2).getBytes());
			 //channel.basicPublish(exchangeName2, "debug", MessageProperties.PERSISTENT_BASIC, (" Confirmģʽ�� ��" + (i + 1) + "����Ϣ"+message3).getBytes());
		}*/
		testMirrorQueue();
	}
	
	/**  
	*
	* @Description:RabbitMQ����ģʽ��Ⱥ�����Բ���
	* 1.�ڼ�Ⱥ���������һ���򼸸��ڵ�崻��᲻�ᵼ�¶������ݵĶ�ʧ��
	* ���Խ��:��������еĽڵ�ֻҪ��һ�������ݾͲ��ᶪʧ��
	* 
	* 
	* 
	* 
	* @return void    
	* @throws  
	*/ 
	public static void testMirrorQueue() {
		Connection connection = getConnection(USERNAME, PASSWORD, HOST, VIRTUALHOST, PORT);
		try {
			Channel channel = connection.createChannel();
			//��������
//			channel.queueDelete("mirror_queue");
//			channel.exchangeDelete("mirror_exchange");
			//����������
//			channel.exchangeDeclare("mirror_exchange", "topic", true,false,false,null);
//			channel.queueDeclare(mirror_queue,true,false,false,null);
//			channel.queueBind(mirror_queue, "mirror_exchange", "mirror.*");
			// ����������ȷ��ģʽ
			channel.confirmSelect();
			
			for (int i = 0; i < 50; i++) {
				channel.basicPublish("mirror_exchange", "mirror.add", MessageProperties.PERSISTENT_BASIC, (" ����ģʽ�� ��" + (i + 1) + "����Ϣ"+"test mirror����new message").getBytes());
			}
			try {
				if(channel.waitForConfirms()) {
					logger.info("������ȷ�Ϸ��ͳɹ���");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * @throws TimeoutException 
	 * @throws IOException   
	*
	* @Description: ��ȡ����
	* @return  
	* @return Connection    
	* @throws  
	*/ 
	public static Connection getConnection(String username,String password,String host,String vHost,int port) {
		Connection connection = null;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(username);
		factory.setPassword(password);
		factory.setHost(host);
		factory.setVirtualHost(vHost);
		factory.setPort(port);
		try {
			connection = factory.newConnection();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return connection;
	}

}
