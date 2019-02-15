package com.example.licola.myandroiddemo.collections;

import android.support.v4.util.LruCache;
import com.licola.llogger.LLogger;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;

/**
 * @author LiCola
 * @date 2019/2/15
 */
public class LruCacheTest {


  @Test
  public void onCache() {
    int size = 8 * 2;
    LruCache<String, byte[]> cache = new LruCache<String, byte[]>(size) {
      @Override
      protected int sizeOf(String key, byte[] value) {
        return value.length * 8;
      }
    };

    print("init",cache);

    cache.put("1", new byte[1]);
    cache.put("2", new byte[1]);
    cache.put("3", new byte[1]);

    print("put",cache);

    cache.get("2");
    cache.get("1");

    print("get",cache);

  }

  private void print(String prefix, LruCache<String, byte[]> cache) {
    LLogger.d(prefix + ":" + cache.toString());
    Map<String, byte[]> snapshot = cache.snapshot();
    if (snapshot.isEmpty()) {
      LLogger.d("cache is empty");
      return;
    }

    for (Entry<String, byte[]> entry : snapshot.entrySet()) {
      LLogger.d(entry.getKey() + ":" + entry.getValue());
    }
  }

}
