package me.reminisce.clustering

import org.apache.commons.math3.ml.clustering.Clusterable
import org.joda.time.{DateTime, DateTimeZone}

class TimestampWrapper(private val timestamp: Long) extends Clusterable {

  private val point = Array[Double](timestamp)

  override def getPoint: Array[Double] = point

  override def toString: String = s"$timestamp : ${new DateTime(timestamp).withZone(DateTimeZone.UTC)}"
}
