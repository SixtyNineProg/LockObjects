package ru.clevertec.synchronizer;

import java.util.concurrent.Exchanger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExchangerExample {

  public static void main(String[] args) throws InterruptedException {
    Exchanger<String> exchanger = new Exchanger<>();
    Runnable task =
        () -> {
          try {
            log.info("run");
            Thread thread = Thread.currentThread();
            String withThreadName = exchanger.exchange(thread.getName());
            log.info(thread.getName() + " обменялся с " + withThreadName);
          } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
          }
        };
    new Thread(task).start();
    Thread.sleep(1000);
    new Thread(task).start();
  }
}
