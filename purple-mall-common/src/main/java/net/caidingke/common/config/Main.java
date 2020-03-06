package net.caidingke.common.config;

import com.google.common.base.Stopwatch;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {

    // 1s无访问则缓存过期, 每次加载一个key需要耗时2s
    private static LoadingCache<String, String> cache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    System.out.println("begin to query db...");
                    Thread.sleep(2000);
                    System.out.println("success to query db...");
                    return UUID.randomUUID().toString();
                }
            });

    private static CountDownLatch latch = new CountDownLatch(1);


    public static void main(String[] args) throws Exception {

        cache.put("name", "aty");
        Thread.sleep(1500);

        for (int i = 0; i < 8; i++) {
            startThread(i);
        }

        // 让线程运行
        latch.countDown();

    }

    private static void startThread(int id) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + "...begin");
                    latch.await();
                    Stopwatch watch = Stopwatch.createStarted();
                    System.out.println("value..." + cache.get("name"));
                    watch.stop();

                    System.out.println(Thread.currentThread().getName() + "...finish,cost time=" + watch.elapsed(TimeUnit.SECONDS));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t.setName("Thread-" + id);
        t.start();
    }

}
