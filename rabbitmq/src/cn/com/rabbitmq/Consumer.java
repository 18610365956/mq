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
* @Description:  ������
* @author wanghaixiang  
* @date 2019��9��20�� ����9:35:32   
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
		//����Ҫ��ע�Ķ��У���RabbitMq�У������������ݵ��Եģ���һ���ݵȲ������ص�����������ִ����������Ӱ������һ��ִ�е�Ӱ����ͬ��Ҳ����˵��������ڣ��ʹ�����������ڣ�������Ѿ����ڵĶ��в����κ�Ӱ�졣��
		/*��������˵����
		 * queueDeclare(String queue, boolean durable, boolean exclusive,boolean autoDelete, Map<String, Object> arguments);
		 * queue: ��������
		 * durable: �Ƿ�־û�,���е�����Ĭ���Ǵ�ŵ��ڴ��еģ����rabbitmq�����ᶪʧ�����������֮�󻹴��ھ�Ҫʹ���г־û������浽Erlang�Դ���Mnesia���ݿ��У���rabbitmq��������ȡ�����ݿ�
		 * exclusive: �Ƿ�����ģ����������ã�һ�������ӹر�ʱ��connection.close(),�ö����Ƿ���Զ�ɾ������:�ö����Ƿ���˽�еģ������������ģ�����ʹ�����������߶�����ͬһ�����У�
		 * û���κ����⣬���������ģ���Ե�ǰ���м���������ͨ���ǲ��ܷ��ʵģ����ǿ�Ʒ��ʻᱨ�쳣��com.rabbitmq.client.ShutdownSignalException: channel error; protocol method:
		 *  #method<channel.close>(reply-code=405, reply-text=RESOURCE_LOCKED - cannot obtain exclusive access to locked queue 'queue_name' in vhost '/', 
		 *  class-id=50, method-id=20) һ�����true�Ļ�����һ������ֻ����һ�������������ѵĳ�����
		 * autoDelete: �Ƿ��Զ�ɾ���������һ�������߶Ͽ����Ӻ�����Ƿ��Զ���ɾ��������ͨ��RabbitMQ Management,�鿴ĳ�����е�����������,��consumers=0,���оͻᱻɾ����
		 * arguments: �����е���Ϣʲôʱ��ɾ����
		 * Message TTL(x-message-ttl):���ö����е�������Ϣ���������ڣ�(ͳһΪ�������е�������Ϣ������������)��Ҳ�����ڷ�����Ϣ��ʱ�򵥶�Ϊĳ����Ϣָ��ʣ������ʱ�䣬��λΪ���룬������redis��ttl,����ʱ�䵽�ˣ�
		 * ��Ϣ�ͻ�Ӷ�������ʧ������Features=TTL, ����Ϊĳ����Ϣ���ù���ʱ�䣺
		 * AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder().expiration(��6000��); 
		 * channel.basicPublish(EXCHANGE_NAME, ����, properties.build(), message.getBytes(��UTF-8��));
		 * Auto Expire(x-expires):��������ָ����ʱ��û�б����ʾͻᱻɾ����Features=Exp
		 * Max Length(x-max-length):�޶�������Ϣ�����ֵ���ȣ�����ָ�����Ƚ��������ļ���ɾ������
		 * Max Length Bytes(x-max-length-bytes): �޶��������Ŀռ��С��һ���������ڴ棬���̵Ĵ�С��
		 * Dead letter exchange(x-dead-letter-exchange):��������Ϣ���ȴ�����󳤶ȣ����߹��ڵȣ����Ӷ�����ɾ������Ϣ���͵�ָ���Ľ�������ȥ�����Ƕ�������
		 * Dead letter routing key(x-dead-letter-routing-key):��ɾ������Ϣ���͵�ָ����������ָ��·�ɼ��Ķ�����ȥ��
		 * Maximum priority(x-max-priority):���ȼ����У���������ʱ�ȶ���������ȼ�ֵ(��������ֵ��ò�Ҫ̫��)���ŷ�����Ϣ��ʱ��ָ������Ϣ�����ȼ������ȼ����ߵ�(��ֵ�����)��Ϣ���ȱ����ѡ�
		 * Lazy mode(x-queue-mode=lazy)�� Lazy Queues: �Ƚ���Ϣ���浽�����ϣ��������ڴ��У��������߿�ʼ���ѵ�ʱ��ż��ص��ڴ���
		 * Master locator(x-queue-master-locator)
		 * */
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		//��ʾ����brokerÿ��ֻ�����Ͷ��������һ����Ϣ�������ߣ�ֻ����ȷ��������Ϣ���ɹ����Ѻ󡱣��Ż���������͡�
		//channel.basicQos(0,1,false);
		System.out.println("confirmConsumer1 waiting for messages.");
		com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String message = new String(body,"utf-8");
				jedis.lpush("message", message);
				//��Ϣ����ȷ��
				//channel.basicAck(envelope.getDeliveryTag(), true);
				//�ֶ��ܾ�
				/*if(message.contains("is")) {
					//�ڶ�������requeue ���Ϊtrueʱ:��������У����Ϊfalse:������Ϣ�����߶��п���ɾ������Ϣ
//					Boolean requeue=true;
					channel.basicReject(envelope.getDeliveryTag(), false);
					logger.info(message+"���ܾ��ˣ�");
					//����Ͷ��
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
		//�Զ�ȷ��
		channel.basicConsume(QUEUE_NAME,true,consumer);
		//�ֶ�ȷ��
		//channel.basicConsume(QUEUE_NAME, false, consumer);
	}

}
