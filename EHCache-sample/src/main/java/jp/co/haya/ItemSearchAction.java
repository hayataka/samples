package jp.co.haya;

public class ItemSearchAction {

    protected CacheLogic cacheLogic;

    public void index() {

    	String cacheKey = "aaaaa";
        AuthDto dto = cacheLogic.getCache("rakutenItemSearchCache", cacheKey);
        if (dto == null) {
            // キャッシュ無し（普通に検索する）
//            dto = executeItemSearch(getItemSearchParameter());
            cacheLogic.setCache("rakutenItemSearchCache", cacheKey, dto);
        }
    }
}