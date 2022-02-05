package io.github.cric96
package gym.envs

import gym.{ExternalType, Gym}
import gym.core.Env
import gym.spaces.{Box, Discrete, Tuple}

/** a facade for some of the standard open ai environments */
object EnvFactory {

  object ToyText {

    def blackJackV0(): Env[Int, (Int, Int, Int), Discrete, Tuple] =
      Gym.make[Int, (Int, Int, Int), Discrete, Tuple]("Blackjack-v0")

    def frozenLakeV1(): Env[Int, Int, Discrete, Discrete] = Gym.make[Int, Int, Discrete, Discrete]("FrozenLake-v1")

    def frozenLakeV18x8(): Env[Int, Int, Discrete, Discrete] =
      Gym.make[Int, Int, Discrete, Discrete]("FrozenLake8x8-v1")

    def guessingGameV0(): Env[Float, Int, Box, Discrete] = Gym.make[Float, Int, Box, Discrete]("GuessingGame-v0")

    def hotterColderV0(): Env[Float, Int, Box, Discrete] = Gym.make[Float, Int, Box, Discrete]("HotterColder-v0")

    def nChainV0(): Env[Int, Int, Discrete, Discrete] = Gym.make[Int, Int, Discrete, Discrete]("NChain-v0")

    def rouletteV0(): Env[Int, Int, Discrete, Discrete] = Gym.make[Int, Int, Discrete, Discrete]("Roulette-v0")

  }

  object ClassicControl {

    def acrobotV1(): Env[Int, ExternalType.NumpyArray, Discrete, Box] =
      Gym.make[Int, ExternalType.NumpyArray, Discrete, Box]("Acrobot-v1")

    def cartPoleV1(): Env[Int, ExternalType.NumpyArray, Discrete, Box] =
      Gym.make[Int, ExternalType.NumpyArray, Discrete, Box]("CartPole-v1")

    def mountainCarV0(): Env[Int, ExternalType.NumpyArray, Discrete, Box] =
      Gym.make[Int, ExternalType.NumpyArray, Discrete, Box]("MountainCar-v0")

    def mountainCarContinuousV0(): Env[ExternalType.NumpyArray, ExternalType.NumpyArray, Box, Box] =
      Gym.make[ExternalType.NumpyArray, ExternalType.NumpyArray, Box, Box]("MountainCarContinuous-v0")

    def pendulumV0(): Env[ExternalType.NumpyArray, ExternalType.NumpyArray, Box, Box] =
      Gym.make[ExternalType.NumpyArray, ExternalType.NumpyArray, Box, Box]("Pendulum-v0")

  }

  object Box2D {

    def bipedalWalkerV3(): Env[ExternalType.NumpyArray, ExternalType.NumpyArray, Discrete, Box] =
      Gym.make[ExternalType.NumpyArray, ExternalType.NumpyArray, Discrete, Box]("BipedalWalker-v3")

    def bipedalWalkerHardcoreV3: Env[ExternalType.NumpyArray, ExternalType.NumpyArray, Box, Box] =
      Gym.make[ExternalType.NumpyArray, ExternalType.NumpyArray, Box, Box]("BipedalWalkerHardcore-v3")

    def carRacingV0: Env[ExternalType.NumpyArray, ExternalType.NumpyArray, Box, Box] =
      Gym.make[ExternalType.NumpyArray, ExternalType.NumpyArray, Box, Box]("CarRacing-v0")

    def lunarLanderV2: Env[Int, ExternalType.NumpyArray, Discrete, Box] =
      Gym.make[Int, ExternalType.NumpyArray, Discrete, Box]("LunarLander-v2")

    def lunarLanderContinuousV2: Env[ExternalType.NumpyArray, ExternalType.NumpyArray, Box, Box] =
      Gym.make[ExternalType.NumpyArray, ExternalType.NumpyArray, Box, Box]("LunarLanderContinuous-v2")

  }

}
