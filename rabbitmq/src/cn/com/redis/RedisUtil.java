/**
 * 
 */
package cn.com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**  
* @ClassName: RedisUtil  
* @Description:  redis工具类
* @author wanghaixiang  
* @date 2019年9月27日 下午3:21:32   
*    
*/
public class RedisUtil {

	/**  
	* redis服务ip
	*/  
	private static String host = "10.20.61.141";
	/**  
	* 服务端口
	*/  
	private static int port = 6379;
	/**  
	* 密码
	*/  
	private static String auth_pass = "redis";
	/**  
	* 最大连接数
	*/  
	private static int max_active = 100;
	/**  
	* 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8
	*/  
	private static int max_idel = 200;
	/**  
	* 等待可用连接的最大的时间，单位为毫秒，默认值为-1，表示永不超时，如果超过等待时间，则直接抛出异常。
	*/  
	private static int max_wait = 10000;
	/**  
	* 连接超时时间
	*/  
	private static int time_out = 10000;
	
	/**  
	* 在borrow一个jedis实例时，是否提前进行validate操作，如果为true,则得到的jedis实例均是可用的。
	*/  
	private static boolean test_on_borrow = true;
	/**  
	* redis连接池
	*/  
	private static JedisPool jedisPool = null;
	//初始化连接池
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
