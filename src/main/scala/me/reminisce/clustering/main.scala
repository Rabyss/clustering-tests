package me.reminisce.clustering

import me.reminisce.clustering.cluster.{GeoClusterer, TimeClusterer}
import me.reminisce.clustering.model.TimeMeasure
import me.reminisce.clustering.plots.Timeplotter

import scala.collection.JavaConverters._

object main {
  def main(args: Array[String]): Unit = {
    //geoCluster()
    args.headOption match {
      case Some(file) =>
        timeCluster(file)
      case None =>
        println("Using default test file...")
        timeCluster("/timeInput.data")
    }
  }

  def geoCluster(): Unit = {
    println("---------- Geolocalisation ----------")
    val clusterer = new GeoClusterer("/geoInput.data", 2, 3)
    val clusterResults = clusterer.cluster

    clusterResults.zipWithIndex.foreach {
      case (cluster, i) =>
        println(s"Cluster $i, of size ${cluster.getPoints.size()}")
        cluster.getPoints.asScala.foreach {
          location =>
            println(s"\t$location")
        }
    }
  }

  def timeCluster(filename: String): Unit = {
    val daySeconds = 24L * 60 * 60 * 1000
    val eps = daySeconds * 15
    val clusterer = new TimeClusterer(filename, eps, 2, Some(new TimeMeasure))
    val clusterResults = clusterer.cluster

    clusterResults.zipWithIndex.foreach {
      case (cluster, i) =>
        println(s"Cluster $i, of size ${cluster.getPoints.size()}")
        cluster.getPoints.asScala.foreach {
          time =>
            println(s"\t$time")
        }
    }

    Timeplotter.plotCluster(clusterResults)
  }
}
