package io.github.cric96
package gym

import gym.core.Env
import gym.spaces.Space

import me.shadaj.scalapy.py
import me.shadaj.scalapy.readwrite.Reader

/** open ai gym module, refer to https://github.com/openai/gym/blob/master/gym/__init__.py */
@py.native
object Gym extends py.StaticModule("gym") with GymMaker {
  type E[A, B, AS[a] <: Space[a], OS[o] <: Space[o]] = Env[A, B, AS, OS]

  def make[A, O, AS[a] <: Space[a], OS[o] <: Space[o]](name: String)(implicit
      obs: Reader[O],
      actionSpace: Reader[AS[A]],
      observationSpace: Reader[OS[O]]
  ): Env[A, O, AS, OS] = py.native

  def envs: py.Dynamic = py.native
}
