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

/**  
* @ClassName: Consumer  
* @Description:  ������
* @author wanghaixiang  
* @date 2019��9��20�� ����9:35:32   
*    
*/
public class Consumer2 {
	private static Logger logger = LoggerFactory.getLogger(Consumer2.class);
	public static final String QUEUE_NAME = "confirm";
	public static final String USERNAME = "admin";
	public static final String PASSWORD = "admin";
	public static final String HOST = "10.20.61.141";
	public static final String VIRTUALHOST = "/";
	public static final int PORT = 5672;

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
		//����һ�����С���ҪΪ�˷�ֹ��Ϣ�����������д˳��򣬶��л�������ʱ�������С�
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println("confirmConsumer2 waiting for messages.");
		com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String message = new String(body,"utf-8");
				logger.info("ConfirmReceiver2: "+message);
				logger.info("ConfirmReceiver2: Done ! at"+simpleDateFormat.format(new Date()));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		};
		//�Զ�ȷ��
		channel.basicConsume(QUEUE_NAME,true,consumer);
	}

}
