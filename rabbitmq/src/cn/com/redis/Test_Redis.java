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
* @Description: ����redis����ɾ�Ĳ�
* @author wanghaixiang  
* @date 2019��9��29�� ����2:00:00   
*    
*/
public class Test_Redis {
	/**  
	* ��־��ӡ
	*/  
	private static Logger logger = LoggerFactory.getLogger(Test_Redis.class);
	/**  
	* jedisʵ��
	*/  
	private static Jedis jedis;
	
	/**  
	*
	* @Description:  
	* @return  ����Redis������
	* @return Jedis    
	* @throws  
	*/ 
	public static void getConnection() {
		jedis = RedisUtil.getJedis();
	}
	
	/**  
	*
	* @Description: �����ַ���
	* @return void    
	* @throws  
	*/ 
	public static void testString(){
		// �������
		jedis.set("NBA", "kebi");
		logger.info("NBA: "+jedis.get("NBA"));
		//ƴ���ַ���
		jedis.append("NBA", "+james");
		logger.info("NBA: "+jedis.get("NBA"));
		// ɾ������
		jedis.del("NBA");
		logger.info("NBA: "+jedis.get("NBA"));
		//���ö����ֵ��
		jedis.mset("name","qiaodan","age","45","qq","10086");
		//��1����
		jedis.incr("age");
		logger.info("name: "+jedis.get("name")+" age: "+jedis.get("age")+" qq: "+jedis.get("qq"));
	}
	
	/**  
	*
	* @Description: ����map 
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
		//��һ�������Ǵ���redis��map�����key,����Ĳ�����map�����key
		List<String> hmget = jedis.hmget("map","name","age","sex");
		List<String> hmget2 = jedis.hmget("map2", "champion");
		logger.info(hmget.toString());
		logger.info(hmget2.toString());
	}
	
	/**  
	*
	* @Description: ����list 
	* @return void    
	* @throws  
	*/ 
	public static void testList() {
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0, -1));
		//����key java framework �д����������
		/*lpush: ��һ������ֵvalue���뵽�б�key�ı�ͷ������ж�� value ֵ��
		��ô���� value ֵ�������ҵ�˳�����β��뵽��ͷ�� ����˵���Կ��б� mylist ִ������ LPUSH mylist a b c ���б��ֵ���� c b a ��*/
//		jedis.lpush("java framework", "spring");
//		jedis.lpush("java framework", "springmvc");
//		jedis.lpush("java framework", "mybatis");
		jedis.lpush("java framework", "ssm","ssh","ssl");
		//����˵������һ����key,�ڶ�������ʼλ�ã��������ǽ���λ�á�jedis.llen ��ȡlist���� .-1��ʾȡ������
		Long llen = jedis.llen("java framework");
		System.out.println(llen);
		//��ȡ����������jedis.lrange�ǰ���Χȡ��
		System.out.println(jedis.lrange("java framework", 0, -1)); //[mybatis, springmvc, spring]�Ƚ����
		jedis.del("java framework");
		//rpush:��һ������ֵ value ���뵽�б� key �ı�β(���ұ�)��
//		jedis.rpush("java framework", "spring");
//		jedis.rpush("java framework", "struts");
//		jedis.rpush("java framework", "hibernate");
		jedis.rpush("java framework", "ssm","ssh","ssl");
		System.out.println(jedis.lrange("java framework", 0, -1));//[spring, struts, hibernate]
	}
	
	/**  
	*
	* @Description: ����set
	* @return void    
	* @throws  
	*/ 
	public static void testSet() {
		jedis.sadd("names", "xjp");
		jedis.sadd("names", "jzm");
		jedis.sadd("names", "hjt");
		jedis.sadd("names", "wjb");
		jedis.sadd("names", "lkq");
		//��ȡset������value
		Set<String> set = jedis.smembers("names");
		System.out.println(set);
		//���ؼ����е�Ԫ�ظ���
		Long scard = jedis.scard("names");
		System.out.println(scard);
		
	}
	
	/**  
	*
	* @Description:redis����
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
