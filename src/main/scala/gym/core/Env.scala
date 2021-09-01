package org.cric96.github.io
package gym.core

import gym.core.Env.RenderMode
import gym.core.Env.StepResponse
import gym.spaces.Space

import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.|
import me.shadaj.scalapy.readwrite.Reader
import me.shadaj.scalapy.readwrite.Writer

@py.native
trait Env[Action, Observation, ActionSpace[A] <: Space[A], ObservationSpace[O] <: Space[O]] extends py.Object {
  private val me: py.Dynamic = this.as[py.Dynamic]

  def actionSpace(implicit reader: Reader[ActionSpace[Action]]): ActionSpace[Action] =
    me.action_space.as[ActionSpace[Action]]

  def observationSpace(implicit reader: Reader[ObservationSpace[Observation]]): ObservationSpace[Observation] =
    me.observation_space.as[ObservationSpace[Observation]]
  def rewardRange: (Double, Double) = me.reward_range.as[(Double, Double)]

  def step(action: Action)(implicit obs: Reader[Observation], wr: Writer[Action]): StepResponse[Observation] = {
    val step = me.step(action).as[(Observation, Float, Boolean, py.Any)]
    StepResponse(step._1, step._2, step._3, step._4)
  }
  def reset()(implicit obs: Reader[Observation]): Observation = py.native

  def render(mode: String | RenderMode = "human"): py.Any = mode.map(
    leftMap = stringMode => me.render(stringMode),
    rightMap = sealedMode => me.render(sealedMode.value)
  )
  def close(): Unit = py.native

  def seed(seed: Int | Option[Int] = None): py.Any = seed.map(
    leftMap = seed => me.seed(seed),
    rightMap = seed => seed.fold(me.seed())(seed => me.seed(seed))
  )
}

object Env {

  case class StepResponse[O](observation: O, reward: Float, done: Boolean, info: py.Any)
      extends Product4[O, Float, Boolean, py.Any] {
    override def _1: O = observation
    override def _2: Float = reward
    override def _3: Boolean = done
    override def _4: py.Any = info
  }
  sealed abstract class RenderMode(val value: py.Any)

  object RenderMode {
    case object Human extends RenderMode("human")
    case object RgbArray extends RenderMode("rgb_array")
    case object Ansi extends RenderMode("ansi")
  }
}
