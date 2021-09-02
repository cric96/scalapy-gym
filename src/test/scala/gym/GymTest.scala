package io.github.cric96
package gym

import gym.spaces.Discrete

import utest.{TestSuite, Tests, test}

import scala.util.Try

object GymTest extends TestSuite {

  @SuppressWarnings(Array("org.wartremover.warts.Nothing")) //because of test frame
  val tests: Tests = Tests {
    test("Gym should") {
      test("create envs untyped") {
        assert {
          Try(Gym.unsafe("Blackjack-v0")).isSuccess
        }
      }

      test("create a typed env") {
        val env = Gym.make[Int, Int, Discrete, Discrete]("FrozenLake-v1")
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
        val env = Gym.make[String, Int, Discrete, Discrete]("FrozenLake-v1")
        val eitherInt = Try(env.actionSpace.sample())
          .flatMap(_ => Try(env.observationSpace.sample()))
          .isFailure
        assert(eitherInt)
      }
    }
  }
}
