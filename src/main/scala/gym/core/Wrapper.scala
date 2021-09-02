package io.github.cric96
package gym.core

import gym.spaces.Space

import me.shadaj.scalapy.py

/** From https://github.com/openai/gym/blob/master/gym/core.py
  * @tparam A
  * @tparam O
  * @tparam AS
  * @tparam OS
  * @tparam W
  */
@py.native
trait Wrapper[A, O, AS[a] <: Space[a], OS[o] <: Space[o], W <: Env[A, O, AS, OS]] extends Env[A, O, AS, OS]
