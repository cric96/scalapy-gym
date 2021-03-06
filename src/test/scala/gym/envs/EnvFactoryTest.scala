package io.github.cric96
package gym.envs

import me.shadaj.scalapy.readwrite.{Reader, Writer}

import gym.ExternalType
import gym.ExternalType.NumpyArray
import gym.core.Env
import gym.envs.EnvFactory.{Box2D, ClassicControl, ToyText}
import gym.spaces.{Box, Discrete, Space, Tuple}
import scala.util.{Failure, Try}
import utest.{test, Tests, TestSuite}

object EnvFactoryTest extends TestSuite {

  @SuppressWarnings(Array("org.wartremover.warts.Nothing")) // because of test frame
  val tests = Tests {
    @SuppressWarnings(Array("org.wartremover.warts.Any", "org.wartremover.warts.DefaultArguments")) // because of CI
    def checkEnv[A, O, AS[a] <: Space[a], OS[o] <: Space[o]](
        env: Env[A, O, AS, OS],
        onCI: Boolean = true
    )(implicit
        actionReader: Reader[A],
        actionWriter: Writer[A],
        observationReader: Reader[O],
        spaceReader: Reader[AS[A]],
        obsReader: Reader[OS[O]]
    ): Boolean = {
      val ci = System.getenv().containsKey("CI")
      val result = for {
        initState   <- Try(env.reset())
        observation <- Try(env.step(env.actionSpace.sample()).observation)
      } yield (initState, observation)
      env.close()
      result.recoverWith { case exc: Throwable =>
        scribe.info(exc.getMessage)
        if (onCI && ci) { Failure(exc) }
        else {
          scribe.info("Fail...")
          Try()
        }
      }.isSuccess
    }

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

      test("Box2D") {
        test("bipedalWalkerV3 should be correctly typed") {
          assert(checkEnv[ExternalType.NumpyArray, ExternalType.NumpyArray, Discrete, Box](Box2D.bipedalWalkerV3()))
        }

        test("bipedalWalkerHardcoreV3 should be correctly typed") {
          assert(checkEnv[NumpyArray, NumpyArray, Box, Box](Box2D.bipedalWalkerHardcoreV3))
        }

        test("carRacingV0 should be correctly typed") {
          assert(
            checkEnv[NumpyArray, NumpyArray, Box, Box](Box2D.carRacingV0, onCI = false)
          ) // it renders by default, does not work on CI
        }

        test("lunarLanderV2 should be correctly typed") {
          assert(checkEnv[Int, NumpyArray, Discrete, Box](Box2D.lunarLanderV2))
        }

        test("lunarLanderV2 should be correctly typed") {
          assert(checkEnv[NumpyArray, NumpyArray, Box, Box](Box2D.lunarLanderContinuousV2))
        }
      }
    }
  }

}
