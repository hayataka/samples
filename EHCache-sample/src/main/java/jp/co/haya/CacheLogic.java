package jp.co.haya;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

public class CacheLogic {

    private static final Logger LOGGER = Logger.getLogger(CacheLogic.class);

    /**
     * キャッシュを追加する
     * @param conf 設定
     * @param cacheKey キー
     * @param obj 値
     */
    public void setCache(String conf, String cacheKey, Object obj) {
        if (obj == null) {
            return;
        }
        CacheManager manager = CacheManager.getInstance();
        if (!manager.cacheExists(conf)) {
            LOGGER.error("キャッシュ設定が見つかりませんでした。cacheName=" + conf);
            return;
        }
        Cache cache = manager.getCache(conf);
        cache.put(new Element(cacheKey, obj));
        LOGGER.info("キャッシュに登録しました。key=" + cacheKey);
    }

    /**
     * キャッシュを取得する
     * @param conf 設定
     * @param cacheKey キー
     * @return 値
     */

    public <T> T getCache(String conf, String cacheKey) {
        CacheManager manager = CacheManager.getInstance();
        if (!manager.cacheExists(conf)) {
            LOGGER.error("キャッシュ設定が見つかりませんでした。cacheName=" + conf);
            return null;
        }
        Cache cache = manager.getCache(conf);
        Element element = cache.get(cacheKey);
        if (element == null) {
            LOGGER.info("キャッシュが見つかりませんでした。key=" + cacheKey);
            return null;
        }
        LOGGER.info("キャッシュを取得しました。key=" + cacheKey);

        @SuppressWarnings("unchecked")
        final T t = (T) element.getValue();
        
        return t;
    }
}