package jp.co.haya;

import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
	
	private static final Logger log = Logger.getLogger(App.class);
	private static final String CACHE_XML = "TEST";
	
    public static void main( String[] args ) throws Exception
    {

    	CacheManager manager = CacheManager.getInstance();
    	
    	AuthDto dto = new AuthDto();
    	dto.setKana("haya");
    	dto.setMail("test@test.com");
    	dto.setNameJ("taka");
    	dto.setUserId("ABC");    	
    	try {
    		  Cache cache = manager.getCache("sampleCache1");
    		  {
    		    // キャッシュに値を格納
    		    Element element = new Element(dto.getUserId(), dto);
    		    cache.put(element);
    		  }
    		  {
    		    // キャッシュから値を取得
    		    Element element = cache.get(dto.getUserId());
    		    AuthDto value = (AuthDto) element.getValue();
    		    System.out.println(value);

    		  
    		  
    		  
    		  
    		  }
    		  {
    		    // キーを指定してキャッシュを削除
    		    cache.remove(dto.getUserId());
    		    // キャッシュ内のすべてのエントリを削除
    		    cache.removeAll();
    		  }

    		} finally {
    		  manager.shutdown();
    		}
    	
    	
//
//    	
//    	logic.setCache(CACHE_XML, dto.getUserId(), dto);
//    	
//
//    	AuthDto cache = logic.getCache(CACHE_XML, dto.getUserId());
//
//    	
//   		log.info("同じ");
    }
    
    

}
