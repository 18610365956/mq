/**
 * 
 */
package cn.com.rabbitmq;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Queue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import cn.com.redis.JedisSentinelUtil;
import redis.clients.jedis.Jedis;

/**  
* @ClassName: Consumer3  
* @Description: 测试镜像队列消费(目前同步节点为master:rabbit和slave:rabbit_2)
* @author wanghaixiang  
* @date 2019年10月14日 下午3:39:49   
* 镜像队列三种策略模式：
* 1.队列镜像到集群中的所有节点上，当新节点添加到集群时，队列将镜像到该节点。
* eg: rabbitmqctl set_policy ha-all "^ha." '{"ha-mode":"all"}'
* 2.集群中镜像队列的实例个数。
* eg: rabbitmqctl set_policy ha-two "^two." '{"ha-mode":"exactly","ha-params":2,"ha-sync-mode":"automatic"}'
* 3.队列镜像到节点名称中列出的节点。节点名称是在出现在rabbitmqctl cluster_status中的Erlang节点名称; 他们通常有“ rabbit@主机名 ” 的形式。
* eg: rabbitmqctl set_policy ha-nodes "^nodes." '{"ha-mode":"nodes","ha-params":["rabbitA@localhost", "rabbitB@localhost"]}'
*     
*/
public class Consumer3 {
	private static Logger logger = LoggerFactory.getLogger(Consumer3.class);
	public static final String USERNAME = "admin";
	public static final String PASSWORD = "admin";
	public static final String HOST = "10.20.61.141";
	public static final String VIRTUALHOST = "/";
	public static final int PORT = 5672;
	//public static String queue ="info_error";
	public static String queue ="no_info";
	
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(USERNAME);
		factory.setPassword(PASSWORD);
		factory.setHost(HOST);
		factory.setPort(PORT);
		factory.setVirtualHost(VIRTUALHOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(queue, true, false, false, null);
		com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body,"utf-8");
				//logger.info("消费者3 消费镜像队列的消息： "+message);
				logger.info("消费者3 消费的消息： "+message);
				/*因为设置了镜像队列，在节点rabbit和rabbit2中队列同步了数据，消费者3去master节点的队列中取数据，
				消费者4去slave节点节点拿数据，消费者3先执行，且不确认消息。测试消费者4能不能拿到同一条消息。（如果能拿到说明这种情况会出现重复消费。）
				
				* 测试结果显示：当master节点队列中消息没有删除的情况下，会出现重复消费的情况。
				*/
				//channel.basicAck(envelope.getDeliveryTag(), true);
			}
			
		};
		//手动确认
		channel.basicConsume(queue,false,consumer);
	}

}
