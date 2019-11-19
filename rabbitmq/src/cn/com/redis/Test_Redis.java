/**
 * 
 */
package cn.com.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**  
* @ClassName: Test_Redis  
* @Description: 测试redis的增删改查
* @author wanghaixiang  
* @date 2019年9月29日 下午2:00:00   
*    
*/
public class Test_Redis {
	/**  
	* 日志打印
	*/  
	private static Logger logger = LoggerFactory.getLogger(Test_Redis.class);
	/**  
	* jedis实例
	*/  
	private static Jedis jedis;
	
	/**  
	*
	* @Description:  
	* @return  连接Redis服务器
	* @return Jedis    
	* @throws  
	*/ 
	public static void getConnection() {
		jedis = RedisUtil.getJedis();
	}
	
	/**  
	*
	* @Description: 操作字符串
	* @return void    
	* @throws  
	*/ 
	public static void testString(){
		// 添加数据
		jedis.set("NBA", "kebi");
		logger.info("NBA: "+jedis.get("NBA"));
		//拼接字符串
		jedis.append("NBA", "+james");
		logger.info("NBA: "+jedis.get("NBA"));
		// 删除数据
		jedis.del("NBA");
		logger.info("NBA: "+jedis.get("NBA"));
		//设置多个键值对
		jedis.mset("name","qiaodan","age","45","qq","10086");
		//加1操作
		jedis.incr("age");
		logger.info("name: "+jedis.get("name")+" age: "+jedis.get("age")+" qq: "+jedis.get("qq"));
	}
	
	/**  
	*
	* @Description: 操作map 
	* @return void    
	* @throws  
	*/ 
	public static void testMap() {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("champion", "china");
		map.put("name", "laker");
		map.put("age", "33");
		map.put("sex", "male");
		jedis.hmset("map", map);
		jedis.hmset("map2", map2);
		//第一个参数是存入redis中map对象的key,后面的参数是map对象的key
		List<String> hmget = jedis.hmget("map","name","age","sex");
		List<String> hmget2 = jedis.hmget("map2", "champion");
		logger.info(hmget.toString());
		logger.info(hmget2.toString());
	}
	
	/**  
	*
	* @Description: 操作list 
	* @return void    
	* @throws  
	*/ 
	public static void testList() {
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0, -1));
		//先向key java framework 中存放三条数据
		/*lpush: 将一个或多个值value插入到列表key的表头，如果有多个 value 值，
		那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，*/
//		jedis.lpush("java framework", "spring");
//		jedis.lpush("java framework", "springmvc");
//		jedis.lpush("java framework", "mybatis");
		jedis.lpush("java framework", "ssm","ssh","ssl");
		//参数说明：第一个是key,第二个是起始位置，第三个是结束位置。jedis.llen 获取list长度 .-1表示取得所有
		Long llen = jedis.llen("java framework");
		System.out.println(llen);
		//再取出所有数据jedis.lrange是按范围取出
		System.out.println(jedis.lrange("java framework", 0, -1)); //[mybatis, springmvc, spring]先进后出
		jedis.del("java framework");
		//rpush:将一个或多个值 value 插入到列表 key 的表尾(最右边)。
//		jedis.rpush("java framework", "spring");
//		jedis.rpush("java framework", "struts");
//		jedis.rpush("java framework", "hibernate");
		jedis.rpush("java framework", "ssm","ssh","ssl");
		System.out.println(jedis.lrange("java framework", 0, -1));//[spring, struts, hibernate]
	}
	
	/**  
	*
	* @Description: 操作set
	* @return void    
	* @throws  
	*/ 
	public static void testSet() {
		jedis.sadd("names", "xjp");
		jedis.sadd("names", "jzm");
		jedis.sadd("names", "hjt");
		jedis.sadd("names", "wjb");
		jedis.sadd("names", "lkq");
		//获取set中所有value
		Set<String> set = jedis.smembers("names");
		System.out.println(set);
		//返回集合中的元素个数
		Long scard = jedis.scard("names");
		System.out.println(scard);
		
	}
	
	/**  
	*
	* @Description:redis排序
	* @return void    
	* @throws  
	*/ 
	public static void testSort() {
		jedis.del("sort");
		jedis.lpush("sort", "1","9","2","100","-1","55","0");
		System.out.println(jedis.lrange("sort", 0, -1));
		List<String> sort = jedis.sort("sort");
		System.out.println(sort);
	}
	/**  
	*
	* @Description:  
	* @param args  
	* @return void    
	* @throws  
	*/
	public static void main(String[] args) {
		getConnection();
		//testString();
		//testMap();
		//testList();
		//testSet();
		testSort();
	}

}
