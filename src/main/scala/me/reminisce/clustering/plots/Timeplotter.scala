package me.reminisce.clustering.plots

import java.awt.{Color, Paint}

import breeze.plot._
import me.reminisce.clustering.model.TimestampWrapper
import org.apache.commons.math3.ml.clustering.Cluster

import scala.collection.JavaConverters._
import scala.collection.mutable

object Timeplotter {

  private val colors: List[Paint] = List(
    new Color(255, 0, 0, 200),
    new Color(0, 255, 0, 200),
    new Color(0, 0, 255, 200)
  )

  private def findMinMax(timeClusters: List[Cluster[TimestampWrapper]]): (Long, Long) = {
    timeClusters.foldLeft((Long.MaxValue, Long.MinValue)) {
      case ((min, max), cluster) =>
        mergeMinMax(min, max, findMinMax(cluster.getPoints.asScala))
    }
  }

  private def findMinMax(points: mutable.Buffer[TimestampWrapper]): (Long, Long) = {
    points.foldLeft((Long.MaxValue, Long.MinValue)) {
      case ((min, max), point) =>
        mergeMinMax(min, max, (point.getTimestamp, point.getTimestamp))
    }
  }

  private def mergeMinMax(min: Long, max: Long, newMinMax: (Long, Long)): (Long, Long) = newMinMax match {
    case (innerMin, innerMax) =>
      val newMin = if (innerMin < min) innerMin else min
      val newMax = if (innerMax > max) innerMax else max
      (newMin, newMax)
  }

  private def getColorMap(valuesByCluster: List[(Int, TimestampWrapper)]): Map[Int, Paint] =
    valuesByCluster.zipWithIndex.map {
      case ((clusterNumber, timestampWrapper), index) => index -> colors(clusterNumber % colors.size)
    }.toMap


  private def getTips(valuesByCluster: List[(Int, TimestampWrapper)]): Map[Int, String] =
    valuesByCluster.zipWithIndex.map {
      case ((clusterNumber, timestampWrapper), index) =>
        index -> s"($clusterNumber) $timestampWrapper"
    }.toMap

  private def getValuesByClusterList(timeClusters: List[Cluster[TimestampWrapper]]): List[(Int, TimestampWrapper)] =
    timeClusters.zipWithIndex.flatMap {
      case (cluster, clusterNumber) =>
        cluster.getPoints.asScala.map {
          tw =>
            (clusterNumber, tw)
        }
    }

  def plotCluster(timeClusters: List[Cluster[TimestampWrapper]]): Unit =
    timeClusters match {
      case Nil =>
        System.err.println("Empty clusters!")
      case _ =>
        val (minTime, maxTime) = findMinMax(timeClusters)
        val f = Figure()
        val p = f.subplot(0)
        val valuesByCluster = getValuesByClusterList(timeClusters)
        val values = valuesByCluster.unzip._2.map(_.getTimestamp)
        val colorsMap = getColorMap(valuesByCluster)
        val tipsMap = getTips(valuesByCluster)
        val xLength = maxTime - minTime
        p.xlim = (minTime.toDouble, maxTime.toDouble)
        p += scatter(x = values, y = List.fill(values.size)(0L), _ => 0.01 * xLength, colorsMap, tips = tipsMap)
        p.ylabel = ""
        p.xlabel = "Timestamps"
        p.title = "Time clusters"
    }
}
