package org.cric96.github.io
package gym.spaces

import gym.ExternalType
import gym.spaces.Box.BoundedManner
import me.shadaj.scalapy.py

@py.native
trait Box[Action] extends Space[Action] {
  val high: ExternalType.NumpyArray = py.native
  val low: ExternalType.NumpyArray = py.native
  val shape: ExternalType.NumpyArray = py.native
  val bounded_low: ExternalType.NumpyArray = py.native
  val bounded_above: ExternalType.NumpyArray = py.native
  private def is_bounded(manner: String = "both"): Boolean = py.native
  def isBounded(manner: BoundedManner = BoundedManner.Both): Boolean = is_bounded(manner.manner)
  def isBounded(manner: String): Boolean = is_bounded(manner)
}

object Box {
  sealed abstract class BoundedManner(val manner: String)

  object BoundedManner {
    case object Both extends BoundedManner("both")
    case object Below extends BoundedManner("below")
    case object Above extends BoundedManner("above")
  }

}
