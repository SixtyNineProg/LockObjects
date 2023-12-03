package ru.clevertec.synchronizer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountDownLatchExample {
  private static final CountDownLatch LATCH = new CountDownLatch(4);

  public static void main(String[] args) {
    Runnable task =
        () -> {
          try {
            var threadName = Thread.currentThread().getName();
            log.info("start: " + threadName);

            TimeUnit.SECONDS.sleep((long) (Math.random() * 10L));

            LATCH.countDown();
            log.info("Countdown: " + LATCH.getCount());
            LATCH.await();

            TimeUnit.MILLISECONDS.sleep(10L);

            log.info("run after: " + threadName);
            TimeUnit.SECONDS.sleep((long) (Math.random() * 10L));
            log.info("Finished:" + threadName);
          } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
          }
        };

    IntStream.range(0, 5).forEach(i -> new Thread(task).start());
    //            IntStream.range(0, 2).forEach(i -> new Thread(task).start());
  }
}
