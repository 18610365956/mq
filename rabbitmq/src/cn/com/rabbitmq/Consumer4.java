/**
 * 
 */
package cn.com.rabbitmq;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

/**  
* @ClassName: Consumer4  
* @Description:   
* @author wanghaixiang  
* @date 2019��10��14�� ����3:54:28   
*    
*/
public class Consumer4 {
	private static Logger logger = LoggerFactory.getLogger(Consumer4.class);
	public static final String USERNAME = "admin";
	public static final String PASSWORD = "admin";
	public static final String HOST = "10.20.61.141";
	public static final String VIRTUALHOST = "/";
	public static final int PORT = 5674;
	//public static String queue ="info_error";
	public static String queue ="no_info";
	/**  
	* ttl����
	*/  
	public static String queue_ttl = "queue_ttl";
	
	public static String queue_ttl_message = "queue_ttl_message";

	/**  
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
		factory.setPort(PORT);
		factory.setVirtualHost(VIRTUALHOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		//channel.queueDeclare(queue, true, false, false, null);
		/**
		 * �˴�����ttl����
		 */
		//Map<String, Object> arguments = new HashMap<String, Object>();
		//channel.queueDeclare(queue_ttl, true, false, false, arguments);
		com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body,"utf-8");
				//logger.info("������4 ���Ѿ�����е���Ϣ�� "+message);
				logger.info("������4 ���ѵ���Ϣ�� "+message+" ʱ�䣺"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				
				//channel.basicAck(envelope.getDeliveryTag(), true);
				/**
				 * ���Խ�����֣�ֻҪ�����е���Ϣ���ڣ������Ǿ�����л�������ͨ���У���������δȷ����Ϣ������£��ͻ�����ظ����ѵ����⡣
				 */
			}
			
		};
		//�Զ�ȷ��
		channel.basicConsume("mirror_queue",true,consumer);
		logger.info("���Ѷ˿�ʼ����ʱ�䣺 "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

}
