package io.github.cric96
package gym.spaces

import gym.core.Env
import gym.envs.EnvFactory

import io.github.cric96.gym.spaces.Box.BoundedManner
import me.shadaj.scalapy.readwrite.Reader
import utest.{TestSuite, Tests, test}

import scala.util.Try

object SpacesTest extends TestSuite {
  private val basicEnv: Env[Int, Int, Discrete, Discrete] = EnvFactory.ToyText.frozenLakeV1()
  private val boxSpace = EnvFactory.ToyText.hotterColderV0().actionSpace
  val space: Space[Int] = basicEnv.actionSpace

  @SuppressWarnings(Array("org.wartremover.warts.Nothing")) //because of test frame
  val tests: Tests = Tests {
    test("Space") {
      test("sample should work accordingly") {
        assert {
          Try(space.sample()).isSuccess
        }
      }
      test("should contains a value gotten by sampling") {
        val data = space.sample()
        assert(space.contains(data))
      }
    }
    test("Box") {
      test("high and low should work accordingly") {
        assert {
          (for {
            high <- Try(boxSpace.high)
            low <- Try(boxSpace.low)
          } yield (high, low)).isSuccess
        }
      }
      test("boundedAbove and boundedBelow should work accordingly") {
        assert {
          (for {
            high <- Try(boxSpace.boundedAbove)
            low <- Try(boxSpace.boundedBelow)
          } yield (high, low)).isSuccess
        }
      }
      test("isBounded work accordingly") {
        assert {
          Try(boxSpace.isBounded()).isSuccess
        }
      }
      test("isBounded work accordingly even with scala types") {
        assert {
          Try(boxSpace.isBounded(BoundedManner.Both)).isSuccess
        }
      }
    }
  }
}
