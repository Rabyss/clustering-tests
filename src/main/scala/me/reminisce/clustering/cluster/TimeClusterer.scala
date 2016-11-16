package me.reminisce.clustering.cluster

import me.reminisce.clustering.model.TimestampWrapper
import org.apache.commons.math3.ml.clustering.DBSCANClusterer
import org.apache.commons.math3.ml.distance.DistanceMeasure

import scala.collection.JavaConverters._
import scala.io.Source

class TimeClusterer(filename: String, eps: Double, minPts: Int, maybeMeasure: Option[DistanceMeasure])
  extends ClustererWrapper[TimestampWrapper] {

  def this(filename: String, eps: Double, minPts: Int) {
    this(filename, eps, minPts, None)
  }

  override val clusterer = maybeMeasure match {
    case Some(measure) =>
      new DBSCANClusterer[TimestampWrapper](eps, minPts, measure)
    case None =>
      new DBSCANClusterer[TimestampWrapper](eps, minPts)
  }

  override def loadData = {
    val resource = getClass.getResourceAsStream(filename)
    val lines = Source.fromInputStream(resource).getLines

    val timestamps =
      lines map {
        line =>
          new TimestampWrapper(line.toLong)
      }

    timestamps.toIterable.asJavaCollection
  }
}
