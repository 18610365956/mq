/**
 * 
 */
package cn.com.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**  
* @ClassName: JedisSentinelUtil  
* @Description: redis哨兵集群工具类
* @author wanghaixiang  
* @date 2019年10月11日 下午3:00:12   
*    
*/
public class JedisSentinelUtil {
	/**  
	* 日志打印
	*/  
	private Logger logger = LoggerFactory.getLogger(JedisSentinelUtil.class);
	
	/**  
	* 主节点名称
	*/  
	private static final String MASTE_RNAME = "mymaster"; 
	/**  
	* 主节点连接密码
	*/  
	private static final String MASTER_AUTH_PASSWORD = "redis";
	/**  
	* 哨兵集合
	*/  
	private static Set<String> sentinelSet = new HashSet<>();
	static{
		sentinelSet.add("10.20.61.141:26379");
		sentinelSet.add("10.20.61.141:26380");
		sentinelSet.add("10.20.61.141:26381");
	}
	
	public static Jedis getInstance() {
		@SuppressWarnings("resource")
		JedisSentinelPool jSentinelPool  = new JedisSentinelPool(MASTE_RNAME, sentinelSet, MASTER_AUTH_PASSWORD);
		return jSentinelPool.getResource();
	}
	
	public static void main(String[] args) {
		Jedis jedis = getInstance();
		System.out.println(jedis.llen("message"));
		List<String> list = jedis.lrange("message", 0, -1);
		System.out.println(list);
//		System.out.println("name: "+jedis.get("name"));
//		jedis.set("year", "2019");
//		System.out.println("year: "+jedis.get("year"));
	}
}
