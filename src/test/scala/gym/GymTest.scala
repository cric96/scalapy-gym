package io.github.cric96
package gym

import gym.core.Env
import gym.spaces.Discrete

import utest.TestSuite
import utest.Tests
import utest.test

import scala.util.Try

object GymTest extends TestSuite {

  val baseEnv = "FrozenLake-v1"

  @SuppressWarnings(Array("org.wartremover.warts.Nothing")) // because of test frame
  val tests: Tests = Tests {
    test("Gym should") {
      test("create envs untyped") {
        assert {
          Try(Gym.unsafe("Blackjack-v0")).isSuccess
        }
      }

      test("create a typed env") {
        val env = Gym.make[Int, Int, Discrete, Discrete](baseEnv)
        val eitherInt = Try(env.actionSpace.sample())
          .flatMap(_ => Try(env.observationSpace.sample()))
          .isSuccess
        assert(eitherInt)
      }

      test("create a generic env") {
        val env = Gym.makeGenericEnv[Int, Int, Discrete, Discrete, Env](baseEnv)
        val eitherInt = Try(env.actionSpace.sample())
          .flatMap(_ => Try(env.observationSpace.sample()))
          .isSuccess
        assert(eitherInt)
      }

      test("create generic spaces env") {
        val env = Gym.makeGenericSpaces[Discrete, Discrete](baseEnv)
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
        val env = Gym.make[String, Int, Discrete, Discrete](baseEnv)
        val eitherInt = Try(env.actionSpace.sample())
          .flatMap(_ => Try(env.observationSpace.sample()))
          .isFailure
        assert(eitherInt)
      }
    }
  }

}
