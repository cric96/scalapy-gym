package org.cric96.github.io
package gym.envs

import gym.Gym
import gym.core.Env
import gym.spaces.Box
import gym.spaces.Discrete
import gym.spaces.Tuple

object EnvFactory {

  object ToyText {
    val blackJackV0: Env[Int, (Int, Int, Int), Discrete, Tuple] = Gym.make("Blackjack-v0")
    val frozenLakeV1: Env[Int, Int, Discrete, Discrete] = Gym.make("FrozenLake-v1")
    val frozenLakeV18x8: Env[Int, Int, Discrete, Discrete] = Gym.make("FrozenLake8x8-v1")
    val guessingGame: Env[Int, Float, Discrete, Box] = Gym.make("GuessingGame-v0")
    val hotterColderV0: Env[Float, Int, Box, Discrete] = Gym.make("HotterColder-v0")
    val nChainV0: Env[Int, Int, Discrete, Discrete] = Gym.make("NChain-v0")
    val rouletteV0: Env[Int, Int, Discrete, Discrete] = Gym.make("Roulette-v0")
  }

  object ClassicControl {}
}
