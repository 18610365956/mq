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
 * 死信队列：
 * 消息变成死信的几种情况：
 * 1.消息被拒绝(basicNack或者basicReject并且requeue=false)
 * 2.消息TTL过期
 * 3.队列达到最大长度
 * 
 * 当消息在一个队列中变成死信之后，会被重新publish到另外一个交换机Exchange，这个Exchange就是死信队列(DLX)
 * DLX也是正常的Exchange，和一般的exchange没有什么不同， 他可以被任何的队列指定，实际上就是设置队列的属性
 * 当这个队列中有死信时，rabbitmq会自动的将这个消息重新发送到设置的Exchange上去，进而被路由到另一个队列。
 * 
 * 死信流程：
 * 1.消息发送到普通交换机normal_exchange,根据路由键转发到与之绑定的正常队列normal_queue,(该正常队列设置了死信队列参数)
 * 2.当发送到该队列的消息变成死信后，该消息根据正常队列设置的死信队列其实就是死信交换机dlx_exchange转发到死信交换机上。
 * 3.死信交换机根据与之绑定的死信队列路由键，将死信消息转发到死信队列上。
 */
/**  
* @ClassName: Producer3  
* @Description:   
* @author wanghaixiang  
* @date 2019年10月14日 下午2:24:40   
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
	* 死信队列
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
		// 打开连接
		Connection connection = factory.newConnection();
		// 创建信道
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
//		//设置队列的死信队列(即指定交换机)
//		arguments.put("x-dead-letter-exchange", exchange_dlx);
//		channel.queueDeclare(queue_dlx, true, false, false, arguments);
//		channel.queueBind(queue_dlx, exchange_dlx, routingKey_dlx);
		
		//channel.queueDelete(queue4);
		//channel.queueDeclare(queue1, true, false, false, null);
		//channel.queueDeclare(queue2, true, false, false, null);
		//channel.queueDeclare(queue3, true, false, false, null);
		//channel.queueDeclare(queue4, true, false, false, null);
		//声明ttl队列
		//Map<String, Object> arguments = new HashMap<String, Object>();
		/**
		 * 消息入队列时开始计时，只要超过队列的超时时间设置,消息自动清除
		 */
		//arguments.put("x-message-ttl", 10000);//即消息在队列能存在的时长
		//通过队列属性设置消息ttl的方法是在queue.declare方法中加入x-message-ttl参数，单位为ms
		//channel.queueDeclare(queue5, true, false, false, arguments);
		/*String exchangeName = "direct_exchange";
		String exchangeName2 = "direct_exchange_2";
		String message1 = "This is a error message！";
		String message2 = "This is a warning message！";
		String message3 = "This is a debug message！";
		String message4 = "This is a no_info message！";
		String message5 = "This is a ttl message！";
		String message6 = "This is a ttl_message message！";
		String message7 = "This is a ttl_tlx message！";
		String message8 = "This is a no_ttl message！";*/
		//channel.queueBind(queue1, exchangeName, "errorBig");
//		channel.queueBind(queue2, exchangeName2, "warning");
//		channel.queueBind(queue3, exchangeName2, "debug");
//		channel.queueBind(queue4, exchangeName2, "no_info");
		//channel.queueBind(queue5, exchangeName, "ttl");
		//channel.queueBind(queue6, exchangeName2, "ttl_message");
		//开启生产者确认模式
		/*channel.confirmSelect();
		*//**
		 * 测试镜像队列
		 *//*
		for (int i = 0; i < 1; i++) {
			AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
			builder.deliveryMode(2);
			builder.expiration("15000");
			BasicProperties properties = builder.build();
			channel.basicPublish("direct_exchange_2", "direct_2", properties, message8.getBytes());
			// channel.basicPublish(exchangeName, "error", MessageProperties.PERSISTENT_BASIC, (" 镜像模式， 第" + (i + 1) + "条消息"+"test error message").getBytes());
			//channel.basicPublish(exchangeName2, "no_info", MessageProperties.PERSISTENT_BASIC, message4.getBytes());
			//channel.basicPublish(exchangeName, "ttl", MessageProperties.PERSISTENT_BASIC, message5.getBytes());
			*//**
			 * 设置消息属性
			 *//*
			AMQP.BasicProperties.Builder builder =new AMQP.BasicProperties.Builder();
			builder.deliveryMode(2);//持久化消息
			//消息的过期时间
			builder.expiration("30000");
			BasicProperties properties = builder.build();
			
			//channel.basicPublish(exchangeName2, "ttl_message", properties, message2.getBytes());
			if(channel.waitForConfirms()) {
					logger.info("no_ttl队列消息发送成功！"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}
			 //channel.basicPublish(exchangeName2, "warning", MessageProperties.PERSISTENT_BASIC, (" Confirm模式， 第" + (i + 1) + "条消息"+message2).getBytes());
			 //channel.basicPublish(exchangeName2, "debug", MessageProperties.PERSISTENT_BASIC, (" Confirm模式， 第" + (i + 1) + "条消息"+message3).getBytes());
		}*/
		testMirrorQueue();
	}
	
	/**  
	*
	* @Description:RabbitMQ镜像模式集群可用性测试
	* 1.在集群工作中如果一个或几个节点宕机会不会导致队列数据的丢失？
	* 测试结果:镜像队列中的节点只要有一个存活，数据就不会丢失。
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
			//声明队列
//			channel.queueDelete("mirror_queue");
//			channel.exchangeDelete("mirror_exchange");
			//申明交换机
//			channel.exchangeDeclare("mirror_exchange", "topic", true,false,false,null);
//			channel.queueDeclare(mirror_queue,true,false,false,null);
//			channel.queueBind(mirror_queue, "mirror_exchange", "mirror.*");
			// 开启生产者确认模式
			channel.confirmSelect();
			
			for (int i = 0; i < 50; i++) {
				channel.basicPublish("mirror_exchange", "mirror.add", MessageProperties.PERSISTENT_BASIC, (" 镜像模式， 第" + (i + 1) + "条消息"+"test mirror——new message").getBytes());
			}
			try {
				if(channel.waitForConfirms()) {
					logger.info("生产者确认发送成功！");
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
	* @Description: 获取连接
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
