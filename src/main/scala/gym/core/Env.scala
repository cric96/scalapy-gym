package org.cric96.github.io
package gym.core

import gym.core.Env.StandardRenderMode
import gym.core.Env.StepResponse
import gym.spaces.Space
import util.PythonInternals

import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.|
import me.shadaj.scalapy.readwrite.Reader
import me.shadaj.scalapy.readwrite.Writer

/** From https://github.com/openai/gym/blob/master/gym/core.py
  * Env is a facade that adds types to standard open-ai environments.
  * @tparam Action the type of admissible action for this environment, e.g. Int, py.Any, ...
  * @tparam Observation the type of admissible observation for this environment, e.g. Int, py.Any, ...
  * @tparam ActionSpace the type of the space associate to actions, e.g. Space, Box, Dict, Tuple, ..
  * @tparam ObservationSpace the type of the space associate to actions, e.g. Space, Box, Dict, Tuple, ..
  */
@py.native
trait Env[Action, Observation, ActionSpace[A] <: Space[A], ObservationSpace[O] <: Space[O]]
    extends py.Object
    with PythonInternals {

  /** python name: action_space
    * @param reader type class needed to get the underlying python value
    * @return the open ai action space
    */
  def actionSpace(implicit reader: Reader[ActionSpace[Action]]): ActionSpace[Action] =
    me.action_space.as[ActionSpace[Action]]

  /** python name: observation_space
    * @param reader type class needed to get the underlying python value
    * @return the open ai observation space
    */
  def observationSpace(implicit reader: Reader[ObservationSpace[Observation]]): ObservationSpace[Observation] =
    me.observation_space.as[ObservationSpace[Observation]]

  /** python name: reward_range
    * @return the admissible reward range
    */
  def rewardRange: (Double, Double) = me.reward_range.as[(Double, Double)]

  /** @param action refer to https://github.com/openai/gym/blob/master/gym/core.py
    * @param obs type class needed to get the underlying python value
    * @param wr type class needed to convert the scala value to the python value
    * @return a tuple with (observation, reward, done, info)
    */
  def step(action: Action)(implicit obs: Reader[Observation], wr: Writer[Action]): StepResponse[Observation] = {
    val step = me.step(action).as[(Observation, Float, Boolean, py.Dynamic)]
    StepResponse(step._1, step._2, step._3, step._4)
  }

  /** @param obs type class needed to convert the scala value to the python value
    * @return refer to https://github.com/openai/gym/blob/master/gym/core.py
    */
  def reset()(implicit obs: Reader[Observation]): Observation = py.native

  /** @param mode a String o one of the standard mode ("human", "rbg_array", "ansi")
    * @return refer to https://github.com/openai/gym/blob/master/gym/core.py
    */
  def render(mode: String | StandardRenderMode = "human"): py.Any = mode.map(
    leftMap = stringMode => me.render(stringMode),
    rightMap = sealedMode => me.render(sealedMode.value)
  )

  /** refer to https://github.com/openai/gym/blob/master/gym/core.py */
  def close(): Unit = py.native

  /** @param seed an integer or an optional
    * @return refer to https://github.com/openai/gym/blob/master/gym/core.py
    */
  def seed(seed: Int | Option[Int] = None): py.Any = seed.map(
    leftMap = seed => me.seed(seed),
    rightMap = seed => seed.fold(me.seed())(seed => me.seed(seed))
  )
}

object Env {

  /** The return type of Env.step. It is conceptually a tuple of (O, Float, Boolean, py.Dynamic)
    * @param observation agent's observation of the current environment
    * @param reward amount of reward returned after previous action
    * @param done whether the episode has ended, in which case further step() calls will return undefined results
    * @param info contains auxiliary diagnostic information (helpful for debugging, and sometimes learning)
    * @tparam O the type of admissible observation for this environment, e.g. Int, py.Any, ...
    */
  case class StepResponse[O](observation: O, reward: Float, done: Boolean, info: py.Dynamic)
      extends Product4[O, Float, Boolean, py.Any] {
    override def _1: O = observation
    override def _2: Float = reward
    override def _3: Boolean = done
    override def _4: py.Dynamic = info
  }

  /** Union type for render mode, admissible value: human, rgb_array, ansi
    * @param value the python string value
    */
  sealed abstract class StandardRenderMode(val value: py.Any)

  object StandardRenderMode {
    case object Human extends StandardRenderMode("human")
    case object RgbArray extends StandardRenderMode("rgb_array")
    case object Ansi extends StandardRenderMode("ansi")
  }
}
