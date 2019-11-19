/**
 * 
 */
package cn.com.rabbitmq;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import cn.com.redis.JedisSentinelUtil;
import redis.clients.jedis.Jedis;

/**  
* @ClassName: Consumer  
* @Description:  消费者
* @author wanghaixiang  
* @date 2019年9月20日 上午9:35:32   
*    
*/
public class Consumer {
	private static Logger logger = LoggerFactory.getLogger(Consumer.class);
	public static final String QUEUE_NAME = "direct_queue";
	public static final String USERNAME = "admin";
	public static final String PASSWORD = "admin";
	public static final String HOST = "10.20.61.141";
	public static final String VIRTUALHOST = "/";
	public static final int PORT = 5674;
	public static Jedis jedis = JedisSentinelUtil.getInstance();

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
		factory.setPort(PORT);
		factory.setVirtualHost(VIRTUALHOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		//申明要关注的队列，在RabbitMq中，队列申明是幂等性的，（一个幂等操作的特点是其任意多次执行所产生的影响均与第一次执行的影响相同，也就是说如果不存在，就创建，如果存在，不会对已经存在的队列产生任何影响。）
		/*方法参数说明：
		 * queueDeclare(String queue, boolean durable, boolean exclusive,boolean autoDelete, Map<String, Object> arguments);
		 * queue: 队列名称
		 * durable: 是否持久化,队列的声明默认是存放到内存中的，如果rabbitmq重启会丢失，如果想重启之后还存在就要使队列持久化，保存到Erlang自带的Mnesia数据库中，当rabbitmq重启后会读取该数据库
		 * exclusive: 是否排外的，有两个作用，一：当连接关闭时，connection.close(),该队列是否会自动删除。二:该队列是否是私有的，如果不是排外的，可以使用两个消费者都访问同一个队列，
		 * 没有任何问题，如果是排外的，会对当前队列加锁，其他通道是不能访问的，如果强制访问会报异常，com.rabbitmq.client.ShutdownSignalException: channel error; protocol method:
		 *  #method<channel.close>(reply-code=405, reply-text=RESOURCE_LOCKED - cannot obtain exclusive access to locked queue 'queue_name' in vhost '/', 
		 *  class-id=50, method-id=20) 一般等于true的话用于一个队列只能由一个消费者来消费的场景。
		 * autoDelete: 是否自动删除，当最后一个消费者断开连接后队列是否自动被删除，可以通过RabbitMQ Management,查看某个队列的消费者数量,当consumers=0,队列就会被删除。
		 * arguments: 队列中的消息什么时候被删除？
		 * Message TTL(x-message-ttl):设置队列中的所有消息的生存周期，(统一为整个队列的所有消息设置生命周期)，也可以在发布消息的时候单独为某个消息指定剩余生存时间，单位为毫秒，类似于redis的ttl,生存时间到了，
		 * 消息就会从队列中消失，特性Features=TTL, 单独为某条消息设置过期时间：
		 * AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder().expiration(“6000”); 
		 * channel.basicPublish(EXCHANGE_NAME, “”, properties.build(), message.getBytes(“UTF-8”));
		 * Auto Expire(x-expires):当队列在指定的时间没有被访问就会被删除，Features=Exp
		 * Max Length(x-max-length):限定队列消息的最大值长度，超过指定长度将会把最早的几条删除掉。
		 * Max Length Bytes(x-max-length-bytes): 限定队列最大的空间大小，一般受限于内存，磁盘的大小。
		 * Dead letter exchange(x-dead-letter-exchange):当队列消息长度大于最大长度，或者过期等，将从队列中删除的消息推送到指定的交换机中去而不是丢弃掉。
		 * Dead letter routing key(x-dead-letter-routing-key):将删除的消息推送到指定交换机得指定路由键的队列中去。
		 * Maximum priority(x-max-priority):优先级队列，声明队列时先定义最大优先级值(定义优先值最好不要太大)，才发布消息的时候指定该消息的优先级，优先级更高的(数值更大的)消息优先被消费。
		 * Lazy mode(x-queue-mode=lazy)： Lazy Queues: 先将消息保存到磁盘上，不放在内存中，当消费者开始消费的时候才加载到内存中
		 * Master locator(x-queue-master-locator)
		 * */
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		//表示设置broker每次只能推送队列里面的一条消息到消费者，只有在确认这条消息“成功消费后”，才会继续被推送。
		//channel.basicQos(0,1,false);
		System.out.println("confirmConsumer1 waiting for messages.");
		com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String message = new String(body,"utf-8");
				jedis.lpush("message", message);
				//消息正向确认
				//channel.basicAck(envelope.getDeliveryTag(), true);
				//手动拒绝
				/*if(message.contains("is")) {
					//第二个参数requeue 如果为true时:重新入队列，如果为false:丢弃消息，告诉队列可以删除该消息
//					Boolean requeue=true;
					channel.basicReject(envelope.getDeliveryTag(), false);
					logger.info(message+"被拒绝了！");
					//重新投递
					//channel.basicRecover(true);
				}else {
					channel.basicAck(envelope.getDeliveryTag(), true);
				}*/
				logger.info("ConfirmReceiver1: "+message);
				logger.info("ConfirmReceiver1: Done ! at"+simpleDateFormat.format(new Date()));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		};
		//自动确认
		channel.basicConsume(QUEUE_NAME,true,consumer);
		//手动确认
		//channel.basicConsume(QUEUE_NAME, false, consumer);
	}

}
