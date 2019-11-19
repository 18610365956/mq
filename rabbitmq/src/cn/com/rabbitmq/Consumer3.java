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
* @Description: ���Ծ����������(Ŀǰͬ���ڵ�Ϊmaster:rabbit��slave:rabbit_2)
* @author wanghaixiang  
* @date 2019��10��14�� ����3:39:49   
* ����������ֲ���ģʽ��
* 1.���о��񵽼�Ⱥ�е����нڵ��ϣ����½ڵ���ӵ���Ⱥʱ�����н����񵽸ýڵ㡣
* eg: rabbitmqctl set_policy ha-all "^ha." '{"ha-mode":"all"}'
* 2.��Ⱥ�о�����е�ʵ��������
* eg: rabbitmqctl set_policy ha-two "^two." '{"ha-mode":"exactly","ha-params":2,"ha-sync-mode":"automatic"}'
* 3.���о��񵽽ڵ��������г��Ľڵ㡣�ڵ��������ڳ�����rabbitmqctl cluster_status�е�Erlang�ڵ�����; ����ͨ���С� rabbit@������ �� ����ʽ��
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
				//logger.info("������3 ���Ѿ�����е���Ϣ�� "+message);
				logger.info("������3 ���ѵ���Ϣ�� "+message);
				/*��Ϊ�����˾�����У��ڽڵ�rabbit��rabbit2�ж���ͬ�������ݣ�������3ȥmaster�ڵ�Ķ�����ȡ���ݣ�
				������4ȥslave�ڵ�ڵ������ݣ�������3��ִ�У��Ҳ�ȷ����Ϣ������������4�ܲ����õ�ͬһ����Ϣ����������õ�˵���������������ظ����ѡ���
				
				* ���Խ����ʾ����master�ڵ��������Ϣû��ɾ��������£�������ظ����ѵ������
				*/
				//channel.basicAck(envelope.getDeliveryTag(), true);
			}
			
		};
		//�ֶ�ȷ��
		channel.basicConsume(queue,false,consumer);
	}

}
