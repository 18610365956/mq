/**
 * 
 */
package cn.com.rabbitmq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**  
* @ClassName: Producer  
* @Description:  生产者
* @author wanghaixiang  
* @date 2019年9月19日 下午4:54:01   
*    
*/
public class Producer {
	private static Logger logger = LoggerFactory.getLogger(Producer.class);
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
		factory.setVirtualHost(VIRTUALHOST);
		factory.setPort(PORT);
		// 打开连接
		Connection connection = factory.newConnection();
		// 创建信道
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = "This is a confirm message！";
		channel.confirmSelect();
		//对每条消息进行监听处理，在channel中添加监听器
		/*channel.addConfirmListener(new ConfirmListener() {
			
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				//当收到broker发送过来的 nack消息就会调用handleNack()方法，
				logger.info("nack: deliveryTag = " + deliveryTag + " multiple: " + multiple);
			}
			
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				// 当收到broker发送过来的 ack消息就会调用handleAck()方法，
				logger.info("nack: deliveryTag = " + deliveryTag + " multiple: " + multiple);
			}
		});*/
		//异步确认
		/**
		 * Channel对象提供的ConfirmListener()回调方法只包含deliveryTag(当前Channel发出的消息序列号),需要自己为每一个Channel维护一个unconfirm的
		 * 消息序列号集合，每个pubish数据，集合中元素+1，回调一次handleAck()方法，unconfirm集合删除相应的一条(multiple=false) 或者多条(multiple=true)记录，
		 */
		//未确认标识
		/*SortedSet<Long> unconfirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
		channel.addConfirmListener(new ConfirmListener() {
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				logger.info("Nack, SeqNo: " + deliveryTag + ", multiple: " + multiple);
				if(multiple) {
					logger.info("> handleNack multiple!");//多条
					unconfirmSet.headSet(deliveryTag + 1L).clear();
					
				}else {
					logger.info("> handleNack multiple false!");//单条
					unconfirmSet.remove(deliveryTag);
				}
				
			}
			
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				if(multiple) {
					logger.info("> handleAck multiple!");//多条
					unconfirmSet.headSet(deliveryTag + 1L).clear();
				}else {
					logger.info("> handleAck multiple false!");//单条
					unconfirmSet.remove(deliveryTag);
				}
				
				
			}
		});*/
		final long start = System.currentTimeMillis();
		 //发送持久化消息
        for (int i = 0; i < 50; i++) {
            //第一个参数是exchangeName(默认情况下代理服务器端是存在一个""名字的exchange的,
            //因此如果不创建exchange的话我们可以直接将该参数设置成"",如果创建了exchange的话
            //我们需要将该参数设置成创建的exchange的名字),第二个参数是路由键
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_BASIC, (" Confirm模式， 第" + (i + 1) + "条消息"+message).getBytes());
            long nextSeqNo = channel.getNextPublishSeqNo();
//            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_BASIC, (" Confirm模式， 第" + (i + 1) + "条消息").getBytes());
//            unconfirmSet.add(nextSeqNo);
            
            //普通confirm
            if (channel.waitForConfirms()) {
            	logger.info("发送成功!第"+(i+1)+" 条消息"+message);
            }else{
                // 进行消息重发
            }
        }
        //批量confirm
       /* if (channel.waitForConfirms()) {
        	logger.info("发送成功!");
        }else{
            // 进行消息重发
        }*/
        //logger.info("执行waitForConfirms耗费时间： "+(System.currentTimeMillis() -start) +"ms");
		channel.close();
		connection.close();

	}

}
