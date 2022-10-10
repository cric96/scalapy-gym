# ScalaPy Gym Facade
A [ScalaPy](https://scalapy.dev/) Facade for OpenAI Gym!
## Quality
[![DeepSource](https://deepsource.io/gh/cric96/scalapy-gym.svg/?label=active+issues&show_trend=true&token=sesd4g2NALBojik4-0diuFj8)](https://deepsource.io/gh/cric96/scalapy-gym/?ref=repository-badge)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/63e1dd4638ba4874983e89abb354ed26)](https://www.codacy.com/gh/cric96/scalapy-gym/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=cric96/scalapy-gym&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/63e1dd4638ba4874983e89abb354ed26)](https://www.codacy.com/gh/cric96/scalapy-gym/dashboard?utm_source=github.com&utm_medium=referral&utm_content=cric96/scalapy-gym&utm_campaign=Badge_Coverage)
[![Codiga Badge](https://api.codiga.io/project/30695/score/svg)](https://api.codiga.io/project/30695/score/svg)
## CI status
| Main  | Develop  |
|---|---|
| ![Build](https://github.com/cric96/scalapy-gym/actions/workflows/build.yml/badge.svg)  |  ![Build](https://github.com/cric96/scalapy-gym/actions/workflows/build.yml/badge.svg?branch=develop) |
## Links
[![Scala Doc](https://javadoc.io/badge2/io.github.cric96/scalapy-gym_2.13/scaladoc.svg?color=red)](https://javadoc.io/doc/io.github.cric96/scalapy-gym_2.13)
## What this project supports
The main aim of this facade consist in using environments described in [OpenAI Gym](http://gym.openai.com/envs/#classic_control).

Currently, there is no interesting in creating environment Scala side. The developing workflow consist in:
- develop your reinforcement learning in Scala,
- create a functional facade to interact with ScalaPy Gym
- test your algorithms in Open AI baselines and share your results!

## Installation
First, you should set up your ScalaPy project correctly, please refer to [this](https://scalapy.dev/docs/) documentation:

Then, you should add this library as dependency in your sbt file:
```
libraryDependencies += "io.github.cric96" %% "scalapy-gym" % "<x.y.z>"
```
The latest version is: [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.cric96/scalapy-gym_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.cric96/scalapy-gym_2.13/badge.svg)

Then you should install OpenAI dependencies. I suggest you to use `pyenv`. The main dependencies are:
- gym
- scipy

Look to [requirements.txt](/requirements.txt).

To use other environments (`box2d` or `MuJuCo` and `Atari`), please refer to [OpenAI Documentation](http://gym.openai.com/docs/).

## How to use

This library tries to make environments type safe. 
So you have to define:
- action type
- observation type
- action space type
- observation space type

For example, for [FrozenLake](http://gym.openai.com/envs/FrozenLake-v0/) you should write:
```scala
val env = Gym.make[Int, Int, Discrete, Discrete]("FrozenLake-v0")
```

If you do not care about the action and observation type, you can type:
```scala
val env = Gym.unsafe("FrozenLake-v0")
```

A simple loop that advances in the simulation could be:
```scala
import io.github.cric96.gym.Gym
val env = Gym.unsafe("FrozenLake-v0") // or EnvFactory.ToyText.frozenLakeV0
env.reset()
val observations = (0 to 1000)
        .tapEach(_ => env.render)
        .map(env.step(env.actionSpace.sample()))
env.close()
```

The python counterpart is:
```python

val env = Gym.unsafe("FrozenLake-v0")
env.reset()
for _ in range(1000):
  env.render()
  env.step(env.action_space.sample()) # take a random action
env.close()
```

As you can see, the experience is very similar :)

Some environments have already the correct typing (look to [EnvFactory](/src/main/scala/gym/envs/EnvFactory.scala))

### Typings
- ToyTest
    - [x] FrozenLake
    - [x] FrozenLake
    - [x] GuessingGame
    - [x] HotterColder
    - [x] nChain
    - [x] Roulette
- ClassicControl
    - [x] Acrobot
    - [x] CartPole
    - [x] MountainCar
    - [x] MountainCarContinuous
    - [x] Pendulum
- [ ] Atari
- [x] Box2D
    - [X] BipedalWalker
    - [X] BipedalWalkerHardcore
    - [X] CarRacing
    - [X] LunarLander
    - [X] LunarLanderContinuous
- [ ] MuJoCo
- [ ] Robotics
- [ ] Algorithms


## Example
- [Basic Q-Learning implementation](https://github.com/cric96/scala-rl-examples/blob/main/qlearning.ipynb)
