package io.github.cric96

import gym.Gym
import gym.spaces.Box

object Test extends App {
  val env = Gym.unsafe("Gopher-ram-v0")
  val elements = Gym.envs.registry.all()
  println(env.seed(None))
  env.reset()
  for (i <- 0 to 100) {
    Thread.sleep(100)
    env.step(env.actionSpace.sample())
  }
  env.close()
}
