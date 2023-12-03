package ru.clevertec.synchronizer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CyclicBarrierExample {

  static AtomicInteger i = new AtomicInteger(0);

  public static void main(String[] args) {
    Runnable action = () -> log.info("На старт!");
    CyclicBarrier barrier = new CyclicBarrier(3, action);
    Runnable task =
        () -> {
          try {
            // try i.incrementAndGet() == 4
            if (i.incrementAndGet() == 5) {
              // If any parties are currently waiting at the barrier, they will return with a
              // BrokenBarrierException.
              barrier.reset();
            }
            log.info("run");
            barrier.await(5L, TimeUnit.SECONDS);
            log.info("Finished");
          } catch (BrokenBarrierException | InterruptedException | TimeoutException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
          }
        };
    log.info("Limit: " + barrier.getParties());

    IntStream.range(0, 7)
        .forEach(
            i -> {
              try {
                Thread.sleep(500);
              } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
              }
              new Thread(task).start();
            });
  }
}
