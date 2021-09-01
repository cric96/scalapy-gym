package io.github.cric96
package gym.spaces

import gym.ExternalType
import gym.spaces.Box.BoundedManner
import util.PythonInternals

import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.|

/** refer to https://github.com/openai/gym/blob/master/gym/spaces/box.py
  * @tparam Action the type of admissible action for this environment, e.g. Int, py.Any, ...
  */
@py.native
trait Box[Action] extends Space[Action] with PythonInternals {
  val high: ExternalType.NumpyArray = py.native
  val low: ExternalType.NumpyArray = py.native
  val shape: ExternalType.NumpyArray = py.native
  /** python name: reward_range */
  val boundedLow: ExternalType.NumpyArray = me.bounded_low.as[ExternalType.NumpyArray]
  /** python name: reward_range */
  val boundedAbove: ExternalType.NumpyArray = me.bounded_above.as[ExternalType.NumpyArray]

  /** python name: is_range
    * @param manner
    * @return
    */
  def isBounded(manner: BoundedManner | String = BoundedManner.Both): Boolean = manner
    .map(
      leftMap = manner => me.is_bounded(manner.manner),
      rightMap = manner => me.is_bounded(manner)
    )
    .as[Boolean]
}

object Box {
  /** utility union type for manner
    * @param manner the underlying manner
    */
  sealed abstract class BoundedManner(val manner: py.Any)

  object BoundedManner {
    case object Both extends BoundedManner("both")
    case object Below extends BoundedManner("below")
    case object Above extends BoundedManner("above")
  }
}
