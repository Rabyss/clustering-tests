package me.reminisce.clustering

import org.apache.commons.math3.random.RandomDataGenerator

object main extends App {
  val randomData = new RandomDataGenerator()

  println(randomData.nextLong(0, 100))
}
