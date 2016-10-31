package me.reminisce.clustering

import org.apache.commons.math3.ml.clustering.DBSCANClusterer

import scala.collection.JavaConverters._
import scala.io.Source

object main {
  def main(args: Array[String]): Unit = {
    //geoCluster()
    timeCluster()
  }

  def geoCluster(): Unit = {
    println("---------- Geolocalisation ----------")
    val filename = "/geoInput.data"
    val resource = getClass.getResourceAsStream(filename)
    val lines = Source.fromInputStream(resource).getLines

    val locations =
      lines map {
        line =>
          val locStr = line.split(", ")
          new Location(locStr(0).toDouble, locStr(1).toDouble)
      }

    val clusterInput =
      (locations map {
        location =>
          new LocationWrapper(location)
      }).toIterable.asJavaCollection

    val clusterer = new DBSCANClusterer[LocationWrapper](2, 3)
    val clusterResults = clusterer.cluster(clusterInput)

    clusterResults.asScala.zipWithIndex.foreach {
      case (cluster, i) =>
        println(s"Cluster ${i + 1}, of size ${cluster.getPoints.size()}")
        cluster.getPoints.asScala.foreach {
          location =>
            println(s"\t$location")
        }
    }
  }

  def timeCluster(): Unit = {
    println("---------- Time ----------")
    val filename = "/timeInput.data"
    val resource = getClass.getResourceAsStream(filename)
    val lines = Source.fromInputStream(resource).getLines

    val timestamps =
      lines map {
        line =>
          new TimestampWrapper(line.toLong)
      }

    val clusterInput = timestamps.toIterable.asJavaCollection

    val eps = 10L * 24 * 60 * 60 * 1000
    val clusterer = new DBSCANClusterer[TimestampWrapper](eps, 2, new TimeMeasure)
    val clusterResults = clusterer.cluster(clusterInput)

    clusterResults.asScala.zipWithIndex.foreach {
      case (cluster, i) =>
        println(s"Cluster ${i + 1}, of size ${cluster.getPoints.size()}")
        cluster.getPoints.asScala.foreach {
          time =>
            println(s"\t$time")
        }
    }
  }
}
