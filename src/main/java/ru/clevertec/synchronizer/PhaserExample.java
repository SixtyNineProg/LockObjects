package ru.clevertec.synchronizer;

import java.util.concurrent.Phaser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhaserExample {

  public static void main(String[] args) throws InterruptedException {
    Phaser phaser = new Phaser();
    // Вызывая метод register, мы регистрируем текущий поток (main) как участника
    phaser.register();
    log.info("Phasecount is " + phaser.getPhase());
    testPhaser(phaser);
    testPhaser(phaser);
    testPhaser(phaser);
    // Через 3 секунды прибываем к барьеру и снимаемся регистрацию. Кол-во прибывших = кол-во
    // регистраций = пуск
    Thread.sleep(3000);
    phaser.arriveAndDeregister();
    log.info("Phasecount is " + phaser.getPhase());
  }

  private static void testPhaser(final Phaser phaser) {
    // Говорим, что будет +1 участник на Phaser
    phaser.register();
    // Запускаем новый поток
    new Thread(
            () -> {
              String name = Thread.currentThread().getName();
              log.info(name + " arrived");
              phaser.arriveAndAwaitAdvance(); // threads register arrival to the phaser.
              log.info(name + " after passing barrier");
            })
        .start();
  }
}
