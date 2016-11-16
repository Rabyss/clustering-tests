package me.reminisce.clustering.cluster

import java.util

import me.reminisce.clustering.model.{Location, LocationWrapper}
import org.apache.commons.math3.ml.clustering.{Clusterer, DBSCANClusterer}
import org.apache.commons.math3.ml.distance.DistanceMeasure

import scala.collection.JavaConverters._
import scala.io.Source

class GeoClusterer(filename: String, eps: Double, minPts: Int, maybeMeasure: Option[DistanceMeasure])
  extends ClustererWrapper[LocationWrapper] {

  def this(filename: String, eps: Double, minPts: Int) {
    this(filename, eps, minPts, None)
  }

  override val clusterer: Clusterer[LocationWrapper] = new DBSCANClusterer[LocationWrapper](eps, minPts)

  override def loadData: util.Collection[LocationWrapper] = {
    val resource = getClass.getResourceAsStream(filename)
    val lines = Source.fromInputStream(resource).getLines

    val locations =
      lines map {
        line =>
          val locStr = line.split(", ")
          new Location(locStr(0).toDouble, locStr(1).toDouble)
      }

    (locations map {
      location =>
        new LocationWrapper(location)
    }).toIterable.asJavaCollection
  }

}
