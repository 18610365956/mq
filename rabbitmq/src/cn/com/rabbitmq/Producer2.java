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
* @Description: 生产者
* @author wanghaixiang  
* @date 2019年9月25日 下午3:04:16   
*    
*/
public class Producer2 {
	/**  
	* 日志打印
	*/  
	private static Logger logger = LoggerFactory.getLogger(Producer2.class);
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";
	private static final String HOST = "10.20.61.141";
	private static final String VIRTUALHOST = "/";
	private static final int PORT = 5672;
	
	
	//测试四种交换机模式
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
		//声明交换机
		/**
		 * exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, boolean internal,
         * Map<String, Object> arguments);参数说明：
         * exchange: 交换机名称
         * type: 交换机类型 DIRECT("direct"), FANOUT("fanout"), TOPIC("topic"), HEADERS("headers");
         * durable:是否持久化，当设置为true时，代表持久化，反之是非持久化，持久化的可以将交换机存盘，在服务器重启的时候不会丢失信息。
         * autoDelete:是否自动删除，设置为true时，则表示自动删除，自删除的前提是至少有一个队列与之绑定，之后所有与这个交换器绑定的队列都与之解绑，一般都设置为false。
         * internal:是否内置，如果设置为true,则表示的是内置的交换机，客户端程序无法直接发送消息到这个交换机中，只能通过交换机路由到交换机的方式。
         * arguments:其它一些结构化参数比如:alternate-exchange。
		 */
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
		channel.exchangeDeclare(exchangeName2, exchangeType, true, false, false, null);
		//声明队列
		channel.queueDeclare(queueName, false, false, false, null);
		channel.queueDeclare(queueName2, false, false, false, null);
		//交换机与队列绑定
		channel.queueBind(queueName, exchangeName, routingKey);
		channel.queueBind(queueName2, exchangeName, routingKey2);
		String message = "hello ,this is direct_message!";
		String message2 = "hello ,this is direct2_message!";
		for (int i = 0; i < 50; i++) {
			channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_BASIC, message.getBytes());
			if(channel.waitForConfirms()) {
				logger.info("第 "+(i+1)+" 条消息发送成功！");
			}
		}
//		for (int i = 0; i < 6; i++) {
//			channel.basicPublish(exchangeName, routingKey2, MessageProperties.PERSISTENT_BASIC, message2.getBytes());
//		}
		
		
		
		
		
		
	}

}
