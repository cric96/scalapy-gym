package org.cric96.github.io
package gym

import gym.core.Env
import gym.spaces.Discrete

import utest._

import scala.util.Try

object GymTest extends TestSuite {

  val tests = Tests {
    test("Gym should") {
      test("create envs untyped") {
        assert {
          Try(Gym.unsafe("Blackjack-v0")).isSuccess
        }
      }

      test("create a typed env") {
        val env: Env[Int, Int, Discrete, Discrete] = Gym.make("FrozenLake-v1")
        val eitherInt = Try(env.actionSpace.sample())
          .flatMap(_ => Try(env.observationSpace.sample()))
          .isSuccess
        assert(eitherInt)
      }

      test("thrown exception if env does not exists") {
        assert {
          Try(Gym.unsafe("Bibo-v0")).isFailure
        }
      }

      test("thrown exception if type is wrong") {
        val env: Env[String, Int, Discrete, Discrete] = Gym.make("FrozenLake-v1")
        val eitherInt = Try(env.actionSpace.sample())
          .flatMap(_ => Try(env.observationSpace.sample()))
          .isSuccess
        assert(eitherInt)
      }
    }
  }
}
