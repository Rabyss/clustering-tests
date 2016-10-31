package me.reminisce.clustering

class Location(private val x: Double, private val y: Double) {
  def getX: Double = {
    x
  }

  def getY: Double = {
    y
  }

  def this(point: Array[Double]) {
    this(point(0), point(1))
  }

  override def toString: String = s"($x, $y)"
}
