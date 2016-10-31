package me.reminisce.clustering

import org.apache.commons.math3.exception.DimensionMismatchException
import org.apache.commons.math3.exception.util.{DummyLocalizable, Localizable}
import org.apache.commons.math3.ml.distance.DistanceMeasure

class TimeMeasure extends DistanceMeasure {
  override def compute(a: Array[Double], b: Array[Double]): Double = {
    if (a.length != 1) {
      throw new DimensionMismatchException(new DummyLocalizable("me.reminisce.clustering.TimeMeasure"), a.length, 1)
    }

    if (b.length != 1) {
      throw new DimensionMismatchException(new DummyLocalizable("me.reminisce.clustering.TimeMeasure"), b.length, 1)
    }

    Math.abs(a(0) - b(0))
  }
}
