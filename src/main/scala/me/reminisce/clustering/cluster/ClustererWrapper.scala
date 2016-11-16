package me.reminisce.clustering.cluster

import java.util

import org.apache.commons.math3.ml.clustering.{Cluster, Clusterable, Clusterer}

import scala.collection.JavaConverters._

trait ClustererWrapper[T <: Clusterable] {
  val clusterer: Clusterer[T]
  def loadData: util.Collection[T]
  def cluster: List[Cluster[T]] = {
    clusterer.cluster(loadData).asScala.toList
  }
}
