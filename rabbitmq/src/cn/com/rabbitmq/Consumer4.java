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
* @date 2019年10月14日 下午3:54:28   
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
	* ttl队列
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
		 * 此处声明ttl队列
		 */
		//Map<String, Object> arguments = new HashMap<String, Object>();
		//channel.queueDeclare(queue_ttl, true, false, false, arguments);
		com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body,"utf-8");
				//logger.info("消费者4 消费镜像队列的消息： "+message);
				logger.info("消费者4 消费的消息： "+message+" 时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				
				//channel.basicAck(envelope.getDeliveryTag(), true);
				/**
				 * 测试结果发现，只要队列中的消息还在，无论是镜像队列或者是普通队列，在消费者未确认消息的情况下，就会出现重复消费的问题。
				 */
			}
			
		};
		//自动确认
		channel.basicConsume("mirror_queue",true,consumer);
		logger.info("消费端开始消费时间： "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

}
