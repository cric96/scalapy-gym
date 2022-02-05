package io.github.cric96
package gym.core

import gym.core.Env.StandardRenderMode
import gym.envs.EnvFactory
import gym.spaces.Discrete
import util.PyEnrichment.RichPyAny

import utest.TestSuite
import utest.Tests
import utest.test

import scala.util.Try

object EnvTest extends TestSuite {

  val basicEnv: Env[Int, Int, Discrete, Discrete] = EnvFactory.ToyText.frozenLakeV1()

  val aSeed: Int                                  = 42

  def progress(env: Env[Int, Int, Discrete, Discrete]): Env.StepResponse[Int] =
    env.step(basicEnv.actionSpace.sample())

  @SuppressWarnings(Array("org.wartremover.warts.Nothing")) // because of test frame
  val tests: Tests = Tests {
    test("Env") {
      test("should return action space accordingly") {
        assert {
          Try(basicEnv.actionSpace).isSuccess
        }
      }

      test("should return reward range accordingly") {
        assert {
          Try(basicEnv.rewardRange).isSuccess
        }
      }

      test("should return observation space accordingly") {
        assert {
          Try(basicEnv.observationSpace).isSuccess
        }
      }

      test("should be possible to reset the environment") {
        assert {
          Try(basicEnv.reset()).isSuccess
        }
      }

      test("should be possible request the seed") {
        assert {
          (for {
            basicRequest <- Try(basicEnv.seed())
            withNumber   <- Try(basicEnv.seed(aSeed))
            withNone     <- Try(basicEnv.seed(None))
            withSome     <- Try(basicEnv.seed(Some(aSeed)))
          } yield (basicRequest, withNumber, withNone, withSome)).isSuccess
        }
      }

      test("should be possible to render environment") {
        assert {
          Try(basicEnv.render()).isSuccess
        }
      }

      test("should be possible to render environment with StandardRenderModes") {
        assert {
          Try(basicEnv.render(StandardRenderMode.Human)).isSuccess
        }
      }

      test("should return the same seed with the same input") {
        val (first, second) = (basicEnv.seed(aSeed), basicEnv.seed(aSeed))
        assert {
          first.pythonEquals(second)
        }
      }

      test("should update the world when step is called") {
        val reset    = basicEnv.reset()
        val newState = progress(basicEnv)
        assert {
          (for {
            reward <- Try(newState.reward)
            done   <- Try(newState.done)
            info   <- Try(newState.info)
            obs    <- Try(newState.observation)
          } yield (reward, done, info, obs)).isSuccess
        }
      }

      test("should possible to close env accordingly") {
        val newEnv = EnvFactory.ToyText.nChainV0()
        assert {
          Try(newEnv.close()).isSuccess
        }
      }

      test("thrown exception if step is called before reset") {
        val newEnv = EnvFactory.ToyText.nChainV0()
        assert {
          Try(progress(newEnv)).isFailure
        }
      }

      test("StepResponse") {
        test("should work as tuple") {
          val reset    = basicEnv.reset()
          val newState = progress(basicEnv)
        }
      }
    }
  }

}
