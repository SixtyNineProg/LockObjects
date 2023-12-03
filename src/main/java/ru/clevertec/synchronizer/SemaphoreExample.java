package ru.clevertec.synchronizer;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SemaphoreExample {

  public static void main(String[] args) {
    Semaphore semaphore = new Semaphore(2);
    Runnable task =
        () -> {
          try {
            var threadName = Thread.currentThread().getName();
            log.info("run");
            semaphore.acquire();
            log.info("-------lock: " + threadName);
            log.info("==================== availablePermits: " + semaphore.availablePermits());
            Thread.sleep(1000);
            log.info("++++++++unlock: " + threadName);
            semaphore.release();
          } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
          }
        };

    IntStream.range(0, 20).forEach(i -> new Thread(task).start());
  }
}
