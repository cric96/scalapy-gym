package io.github.cric96
package gym.spaces

import gym.ExternalType
import gym.spaces.Box.BoundedManner
import util.PythonInternals

import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.|

/** refer to https://github.com/openai/gym/blob/master/gym/spaces/box.py
  * @tparam Action
  *   the type of admissible action for this environment, e.g. Int, py.Any, ...
  */
@py.native
trait Box[A] extends Space[A] with PythonInternals {

  val high: ExternalType.NumpyArray = pyThis.high

  val low: ExternalType.NumpyArray = pyThis.low

  val shape: ExternalType.NumpyArray = pyThis.shape

  /** python name: bounded_below */
  def boundedBelow: ExternalType.NumpyArray = pyThis.bounded_below.as[ExternalType.NumpyArray]

  /** python name: bounded_above */
  def boundedAbove: ExternalType.NumpyArray = pyThis.bounded_above.as[ExternalType.NumpyArray]

  @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments")) // because of python interface
  /** python name: is_range
    * @param manner
    * @return
    */
  def isBounded(manner: BoundedManner | String = "both"): Boolean = {
    val value = manner
      .map(
        leftMap = manner => pyThis.is_bounded(manner.manner),
        rightMap = manner => pyThis.is_bounded(manner)
      )
    value.toString().equalsIgnoreCase("True") // TODO IMPROVE, seems that manner.as[Boolean] does not work..
  }

}

object Box {

  /** utility union type for manner
    * @param manner
    *   the underlying manner
    */
  sealed abstract class BoundedManner(val manner: py.Any)

  object BoundedManner {

    case object Both  extends BoundedManner("both")
    case object Below extends BoundedManner("below")
    case object Above extends BoundedManner("above")

  }

}
