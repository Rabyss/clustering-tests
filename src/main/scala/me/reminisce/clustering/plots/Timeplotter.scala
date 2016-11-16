package me.reminisce.clustering.plots

import breeze.plot._
import me.reminisce.clustering.model.TimestampWrapper
import org.apache.commons.math3.ml.clustering.Cluster
import java.awt.{Color, Paint}

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.util.Random

object Timeplotter {

  private val colors: List[Paint] = List (
    new Color(255, 0, 0, 200),
    new Color(0, 255, 0, 200),
    new Color(0, 0, 255, 200),
    new Color(35, 69, 82, 200),
    new Color(249, 84, 27, 200),
    new Color(142, 226, 170, 200),
    new Color(173, 128, 111, 200),
    new Color(114, 226, 41, 200),
    new Color(29, 28, 202, 200),
    new Color(123, 51, 132, 200)
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

  private def getClusterMap(timeClusters: List[Cluster[TimestampWrapper]]): Map[Long, Int] = {
    timeClusters.zipWithIndex.flatMap {
      case (cluster, index) =>
        cluster.getPoints.asScala.map {
          t =>
            (t.getTimestamp, index)
        }
    }.toMap
  }

  private def getColorMap(timeClusters: List[Cluster[TimestampWrapper]]): Map[Int, Paint] = {
    val clusterMap = getClusterMap(timeClusters)
    clusterMap.values.zipWithIndex.map {
      case (clusterNumber, index) => index -> colors(clusterNumber % colors.size)
    }.toMap
  }

  private def getValuesList(timeClusters: List[Cluster[TimestampWrapper]]): List[Long] =
    timeClusters.flatMap(_.getPoints.asScala.map(_.getTimestamp)).distinct

  def plotCluster(timeClusters: List[Cluster[TimestampWrapper]]): Unit = {
    val (minTime, maxTime) = findMinMax(timeClusters)
    val f = Figure()
    val p = f.subplot(0)
    val values = getValuesList(timeClusters)
    val colorMap = getColorMap(timeClusters)
    val xLength = maxTime - minTime
    p.xlim = (minTime.toDouble, maxTime.toDouble)
    p += scatter(x = values, y = List.fill(values.size)(0L), _ => 0.001 * xLength, colorMap.apply)
    p.ylabel = ""
    p.xlabel = "Timestamps"
    p.title = "lines plotting"
    f.saveas("image.png")
  }
}
