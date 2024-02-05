import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class DemoEhCache {

    public static void main(String[] args) {
        ResourcePoolsBuilder resourcePoolsBuilder = ResourcePoolsBuilder.heap(10);
        CacheConfigurationBuilder<Long, String> cacheConfigurationBuilder = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Long.class, String.class, resourcePoolsBuilder);

        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured", cacheConfigurationBuilder)
                .build();
        cacheManager.init();

        Cache<Long, String> preConfigured = cacheManager.getCache("preConfigured", Long.class, String.class);

        Cache<Long, String> myCache = cacheManager.createCache("myCache", cacheConfigurationBuilder);
        myCache.put(1L, "hello");

        String value = myCache.get(1L);
        System.out.println(value);

        cacheManager.removeCache("preConfigured");
        cacheManager.close();
    }
}
