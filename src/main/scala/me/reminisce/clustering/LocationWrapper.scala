package me.reminisce.clustering

import org.apache.commons.math3.ml.clustering.Clusterable

class LocationWrapper(private val location: Location) extends Clusterable {

  private val point = Array(location.getX, location.getY)

  override def getPoint: Array[Double] = point

  override def toString: String = location.toString
}
