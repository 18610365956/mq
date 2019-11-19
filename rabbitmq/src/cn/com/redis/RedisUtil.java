/**
 * 
 */
package cn.com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**  
* @ClassName: RedisUtil  
* @Description:  redis������
* @author wanghaixiang  
* @date 2019��9��27�� ����3:21:32   
*    
*/
public class RedisUtil {

	/**  
	* redis����ip
	*/  
	private static String host = "10.20.61.141";
	/**  
	* ����˿�
	*/  
	private static int port = 6379;
	/**  
	* ����
	*/  
	private static String auth_pass = "redis";
	/**  
	* ���������
	*/  
	private static int max_active = 100;
	/**  
	* ����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ����Ĭ��ֵҲ��8
	*/  
	private static int max_idel = 200;
	/**  
	* �ȴ��������ӵ�����ʱ�䣬��λΪ���룬Ĭ��ֵΪ-1����ʾ������ʱ����������ȴ�ʱ�䣬��ֱ���׳��쳣��
	*/  
	private static int max_wait = 10000;
	/**  
	* ���ӳ�ʱʱ��
	*/  
	private static int time_out = 10000;
	
	/**  
	* ��borrowһ��jedisʵ��ʱ���Ƿ���ǰ����validate���������Ϊtrue,��õ���jedisʵ�����ǿ��õġ�
	*/  
	private static boolean test_on_borrow = true;
	/**  
	* redis���ӳ�
	*/  
	private static JedisPool jedisPool = null;
	//��ʼ�����ӳ�
	static {
		JedisPoolConfig jPoolConfig = new JedisPoolConfig();
		jPoolConfig.setMaxTotal(max_active);
		jPoolConfig.setMaxIdle(max_idel);
		jPoolConfig.setMaxWaitMillis(max_wait);
		jPoolConfig.setTestOnBorrow(test_on_borrow);
		jedisPool = new JedisPool(jPoolConfig,host,port,time_out,auth_pass);
		
	}
	public synchronized static Jedis getJedis(){
		if(jedisPool != null) {
			Jedis jedis = jedisPool.getResource();
			return jedis;
		}
		else {
			return null;
		}
	}
	
}
