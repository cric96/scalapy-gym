package org.cric96.github.io
package gym.core

import me.shadaj.scalapy.py
import me.shadaj.scalapy.readwrite.Reader
import me.shadaj.scalapy.readwrite.Writer
import org.cric96.github.io.gym.core.Env.StepResponse
import org.cric96.github.io.gym.spaces.Space

@py.native
trait Env[Action, Observation, ActionSpace[A] <: Space[A], ObservationSpace[O] <: Space[O]] extends py.Object {
  private val action_space: Space[Action] = py.native //internal details
  private val observation_space: Space[Observation] = py.native //internal details
  def actionSpace(implicit reader: Reader[ActionSpace[Action]]): ActionSpace[Action] =
    action_space.as[ActionSpace[Action]]

  def observationSpace(implicit reader: Reader[ObservationSpace[Observation]]): ObservationSpace[Observation] =
    observation_space.as[ObservationSpace[Observation]]
  val reward_range: (Double, Double) = py.native
  def step(action: Action)(implicit obs: Reader[Observation], wr: Writer[Action]): StepResponse[Observation] = py.native
  def reset()(implicit obs: Reader[Observation]): Observation = py.native
  def render(mode: String = "human"): Unit = py.native
  def close(): Unit = py.native
  //def seed(seed: Int) //TODO
}

object Env {
  type StepResponse[O] = (O, Float, Boolean, py.Any)
}
