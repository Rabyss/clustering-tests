package me.reminisce.clustering

import me.reminisce.clustering.cluster.{GeoClusterer, TimeClusterer}
import me.reminisce.clustering.model.TimeMeasure
import me.reminisce.clustering.plots.Timeplotter

import scala.collection.JavaConverters._

object main {
  def main(args: Array[String]): Unit = {
    //geoCluster()
    timeCluster()
  }

  def geoCluster(): Unit = {
    println("---------- Geolocalisation ----------")
    val clusterer = new GeoClusterer("/geoInput.data", 2, 3)
    val clusterResults = clusterer.cluster

    clusterResults.zipWithIndex.foreach {
      case (cluster, i) =>
        println(s"Cluster ${i + 1}, of size ${cluster.getPoints.size()}")
        cluster.getPoints.asScala.foreach {
          location =>
            println(s"\t$location")
        }
    }
  }

  def timeCluster(): Unit = {
    val eps = 24L * 60 * 60 * 1000 * 14
    val clusterer = new TimeClusterer("/timeInput.data", eps, 2, Some(new TimeMeasure))
    val clusterResults = clusterer.cluster

    clusterResults.zipWithIndex.foreach {
      case (cluster, i) =>
        println(s"Cluster ${i + 1}, of size ${cluster.getPoints.size()}")
        cluster.getPoints.asScala.foreach {
          time =>
            println(s"\t$time")
        }
    }

    Timeplotter.plotCluster(clusterResults)
  }
}
