package org.cric96.github.io

import gym.Gym
import gym.envs.EnvFactory

object Test extends App {
  val env = EnvFactory.ToyText.hotterColderV0
  val elements = Gym.envs.registry.all()

  println(env.actionSpace)
  println(env.actionSpace.sample())

  println(env.observationSpace)
  println(env.observationSpace.sample())

  env.reset()
  for (i <- 0 to 100) {
    //env.render()
    Thread.sleep(100)
    env.step(env.actionSpace.sample())
  }
  env.close()
}
