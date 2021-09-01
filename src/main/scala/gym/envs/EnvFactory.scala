package org.cric96.github.io
package gym.envs

import gym.Gym
import gym.core.Env
import gym.spaces.Box
import gym.spaces.Discrete
import gym.spaces.Tuple

/** a facade for some of the standard open ai environments */
object EnvFactory {

  object ToyText {
    def blackJackV0(): Env[Int, (Int, Int, Int), Discrete, Tuple] = Gym.make("Blackjack-v0")
    def frozenLakeV1(): Env[Int, Int, Discrete, Discrete] = Gym.make("FrozenLake-v1")
    def frozenLakeV18x8(): Env[Int, Int, Discrete, Discrete] = Gym.make("FrozenLake8x8-v1")
    def guessingGame(): Env[Int, Float, Discrete, Box] = Gym.make("GuessingGame-v0")
    def hotterColderV0(): Env[Float, Int, Box, Discrete] = Gym.make("HotterColder-v0")
    def nChainV0(): Env[Int, Int, Discrete, Discrete] = Gym.make("NChain-v0")
    def rouletteV0(): Env[Int, Int, Discrete, Discrete] = Gym.make("Roulette-v0")
  }

  object ClassicControl {}
}
