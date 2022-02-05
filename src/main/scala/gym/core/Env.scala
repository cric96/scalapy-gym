package io.github.cric96
package gym.core

import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.|
import me.shadaj.scalapy.readwrite.{Reader, Writer}

import gym.core.Env.{StandardRenderMode, StepResponse}
import gym.spaces.Space
import util.PythonInternals

/** From https://github.com/openai/gym/blob/master/gym/core.py Env is a facade that adds types to standard open-ai
  * environments.
  * @tparam A
  *   the type of admissible action for this environment, e.g. Int, py.Any, ...
  * @tparam O
  *   the type of admissible observation for this environment, e.g. Int, py.Any, ...
  * @tparam AS
  *   the type of the space associate to actions, e.g. Space, Box, Dict, Tuple, ..
  * @tparam OS
  *   the type of the space associate to actions, e.g. Space, Box, Dict, Tuple, ..
  */
@py.native
trait Env[A, O, AS[A] <: Space[A], OS[O] <: Space[O]] extends py.Object with PythonInternals {

  /** python name: action_space
    * @param reader
    *   type class needed to get the underlying python value
    * @return
    *   the open ai action space
    */
  def actionSpace(implicit reader: Reader[AS[A]]): AS[A] =
    pyThis.action_space.as[AS[A]]

  /** python name: observation_space
    * @param reader
    *   type class needed to get the underlying python value
    * @return
    *   the open ai observation space
    */
  def observationSpace(implicit reader: Reader[OS[O]]): OS[O] =
    pyThis.observation_space.as[OS[O]]

  /** python name: reward_range
    * @return
    *   the admissible reward range
    */
  def rewardRange: (Double, Double) = pyThis.reward_range.as[(Double, Double)]

  /** @param action
    *   refer to https://github.com/openai/gym/blob/master/gym/core.py
    * @param obs
    *   type class needed to get the underlying python value
    * @param wr
    *   type class needed to convert the scala value to the python value
    * @return
    *   a tuple with (observation, reward, done, info)
    */
  def step(action: A)(implicit obs: Reader[O], wr: Writer[A]): StepResponse[O] = {
    val step: (O, Float, Boolean, py.Dynamic) =
      pyThis.step(action).as[(O, Float, Boolean, py.Dynamic)]
    StepResponse.create(step)
  }

  /** @param obs
    *   type class needed to convert the scala value to the python value
    * @return
    *   refer to https://github.com/openai/gym/blob/master/gym/core.py
    */
  def reset()(implicit obs: Reader[O]): O = py.native

  @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments")) // because of python interface
  /** @param mode
    *   a String o one of the standard mode ("human", "rbg_array", "ansi")
    * @return
    *   refer to https://github.com/openai/gym/blob/master/gym/core.py
    */
  def render(mode: String | StandardRenderMode = "human"): py.Any = mode.map(
    leftMap = stringMode => pyThis.render(stringMode),
    rightMap = sealedMode => pyThis.render(sealedMode.value)
  )

  /** refer to https://github.com/openai/gym/blob/master/gym/core.py */
  def close(): Unit = py.native

  @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments")) // because of python interface
  /** @param seed
    *   an integer or an optional
    * @return
    *   refer to https://github.com/openai/gym/blob/master/gym/core.py
    */
  def seed(seed: Int | Option[Int] = None): py.Any = seed.map(
    leftMap = seed => pyThis.seed(seed),
    rightMap = seed => seed.fold(pyThis.seed())(seed => pyThis.seed(seed))
  )

}

object Env {

  /** The return type of Env.step. It is conceptually a tuple of (O, Float, Boolean, py.Dynamic)
    * @param observation
    *   agent's observation of the current environment
    * @param reward
    *   amount of reward returned after previous action
    * @param done
    *   whether the episode has ended, in which case further step() calls will return undefined results
    * @param info
    *   contains auxiliary diagnostic information (helpful for debugging, and sometimes learning)
    * @tparam O
    *   the type of admissible observation for this environment, e.g. Int, py.Any, ...
    */
  final case class StepResponse[O](observation: O, reward: Float, done: Boolean, info: py.Dynamic)

  object StepResponse {

    def create[O](data: (O, Float, Boolean, py.Dynamic)): StepResponse[O] = {
      val (obs, reward, done, info) = data
      StepResponse(obs, reward, done, info)
    }

  }

  /** Union type for render mode, admissible value: human, rgb_array, ansi
    * @param value
    *   the python string value
    */
  sealed abstract class StandardRenderMode(val value: py.Any)

  object StandardRenderMode {

    case object Human    extends StandardRenderMode("human")
    case object RgbArray extends StandardRenderMode("rgb_array")
    case object Ansi     extends StandardRenderMode("ansi")

  }

}
