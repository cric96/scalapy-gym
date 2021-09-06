package io.github.cric96
package gym.envs

import gym.Gym
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

  object ClassicControl
}