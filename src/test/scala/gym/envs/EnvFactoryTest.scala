package io.github.cric96
package gym.envs

import gym.ExternalType
import gym.core.Env
import gym.envs.EnvFactory.{ClassicControl, ToyText}
import gym.spaces.{Box, Discrete, Space, Tuple}

import me.shadaj.scalapy.readwrite.{Reader, Writer}
import utest.{TestSuite, Tests, test}

import scala.util.Try

object EnvFactoryTest extends TestSuite {

  @SuppressWarnings(Array("org.wartremover.warts.Nothing")) //because of test frame
  val tests = Tests {
    def checkEnv[A, O, AS[a] <: Space[a], OS[o] <: Space[o]](
        env: Env[A, O, AS, OS]
    )(implicit
        actionReader: Reader[A],
        actionWriter: Writer[A],
        observationReader: Reader[O],
        spaceReader: Reader[AS[A]],
        obsReader: Reader[OS[O]]
    ): Boolean =
      (for {
        initState <- Try(env.reset())
        observation <- Try(env.step(env.actionSpace.sample()).observation)
      } yield (initState, observation)).isSuccess

    test("EnvFactory") {
      test("ToyText") {
        test("nChainV0 should be correctly typed") {
          assert(checkEnv[Int, Int, Discrete, Discrete](ToyText.nChainV0()))
        }

        test("blackJackV0 should be correctly typed") {
          assert(checkEnv[Int, (Int, Int, Int), Discrete, Tuple](ToyText.blackJackV0()))
        }

        test("frozenLakeV1 should be correctly typed") {
          assert(checkEnv[Int, Int, Discrete, Discrete](ToyText.frozenLakeV1()))
        }

        test("frozenLakeV18x8 should be correctly typed") {
          assert(checkEnv[Int, Int, Discrete, Discrete](ToyText.frozenLakeV18x8()))
        }

        test("guessingGameV0 should be correctly typed") {
          assert(checkEnv[Float, Int, Box, Discrete](ToyText.guessingGameV0()))
        }

        test("hotterColderV0 should be correctly typed") {
          assert(checkEnv[Float, Int, Box, Discrete](ToyText.hotterColderV0()))
        }

        test("rouletteV0 should be correctly typed") {
          assert(checkEnv[Int, Int, Discrete, Discrete](ToyText.rouletteV0()))
        }
      }

      test("ClassicControl") {
        test("acrobotV1 should be correctly typed") {
          assert(checkEnv[Int, ExternalType.NumpyArray, Discrete, Box](ClassicControl.acrobotV1()))
        }
        test("cartPoleV1 should be correctly typed") {
          assert(checkEnv[Int, ExternalType.NumpyArray, Discrete, Box](ClassicControl.cartPoleV1()))
        }
        test("mountainCarV0 should be correctly typed") {
          assert(checkEnv[Int, ExternalType.NumpyArray, Discrete, Box](ClassicControl.mountainCarV0()))
        }

        test("mountainCarContinuousV0 should be correctly typed") {
          assert(
            checkEnv[ExternalType.NumpyArray, ExternalType.NumpyArray, Box, Box](
              ClassicControl.mountainCarContinuousV0()
            )
          )
        }
        test("pendulumV0 should be correctly typed") {
          assert(checkEnv[ExternalType.NumpyArray, ExternalType.NumpyArray, Box, Box](ClassicControl.pendulumV0()))
        }
      }
    }
  }
}
