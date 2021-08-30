package org.cric96.github.io
package gym

import gym.core.Env
import gym.spaces.Space

import me.shadaj.scalapy.py
import me.shadaj.scalapy.readwrite.Reader

trait GymMaker {
  type E[A, B, AS[a] <: Space[a], OS[o] <: Space[o]] <: Env[A, B, AS, OS]

  def make[A, O, AS[a] <: Space[a], OS[o] <: Space[o]](name: String)(implicit
      obs: Reader[O],
      actionSpace: Reader[AS[A]],
      observationSpace: Reader[OS[O]]
  ): E[A, O, AS, OS]

  def make[A, O, AS[a] <: Space[a], OS[o] <: Space[o], GE[_, _, _[a] <: Space[a], _[o] <: Space[o]]](
      name: String
  )(implicit
      obs: Reader[O],
      actionSpace: Reader[AS[A]],
      observationSpace: Reader[OS[O]],
      e: Reader[GE[A, O, AS, OS]],
      eq: GE[A, O, AS, OS] <:< E[A, O, AS, OS]
  ): GE[A, O, AS, OS] = make[A, O, AS, OS](name).as[GE[A, O, AS, OS]]
  def unsafe(name: String): E[py.Any, py.Any, Space, Space] = make[py.Any, py.Any, Space, Space](name)

  def make[AS[_A] <: Space[_A], OS[_O] <: Space[_O]](
      name: String
  )(implicit actionSpace: Reader[AS[py.Any]], observationSpace: Reader[OS[py.Any]]): E[py.Any, py.Any, AS, OS] =
    make[py.Any, py.Any, AS, OS](name)
}
