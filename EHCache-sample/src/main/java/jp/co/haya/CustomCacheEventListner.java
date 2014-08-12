package jp.co.haya;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class CustomCacheEventListner implements CacheEventListener {

	public void notifyElementRemoved(Cache cache, Element element)
			throws CacheException {

		System.out.println("notifyElementRemoved");

	}

	public void notifyElementPut(Cache cache, Element element)
			throws CacheException {
		//nothing
		System.out.println("notifyElementPut");
	}

	public void notifyElementUpdated(Cache cache, Element element)
			throws CacheException {
		//nothing
		System.out.println("notifyElementUpdated");
	}

	public void notifyElementExpired(Cache cache, Element element) {
		//nothing
		
		Serializable key = element.getKey();
		String aaaaa = (String)key;
		
		System.out.println("notifyElementExpired");
	}

	public void dispose() {
		//nothing
		System.out.println("dispose");
	}

}
