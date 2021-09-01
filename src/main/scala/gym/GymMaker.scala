package org.cric96.github.io
package gym

import gym.core.Env
import gym.spaces.Space

import me.shadaj.scalapy.py
import me.shadaj.scalapy.readwrite.Reader

/** the shape for a gym module. It is inspired by https://github.com/openai/gym/blob/master/gym/__init__.py
  * the scope of this module is to make environments
  * the key idea is that different python module can be reused with this interface, define what kind of environment
  * the module might return.
  */
trait GymMaker {
  /** define the environment kind, e.g. Env or Wrapper are both admissible. No other env are currently supported
    * @tparam A the type of admissible action for this environment, e.g. Int, py.Any, ...
    * @tparam O the type of admissible observation for this environment, e.g. Int, py.Any, ...
    * @tparam AS the type of the space associate to actions, e.g. Space, Box, Dict, Tuple, ..
    * @tparam OS the type of the space associate to actions, e.g. Space, Box, Dict, Tuple, ..
    */
  type E[A, B, AS[a] <: Space[a], OS[o] <: Space[o]] <: Env[A, B, AS, OS]

  /** build an environment as gym.make(...) does, but typed.
    * @param name the name of the environment selected
    * @param obs type class needed to get the underlying python value
    * @param actionSpace type class needed to get the underlying python value
    * @param observationSpace type class needed to get the underlying python value
    * @tparam A the type of admissible action for this environment, e.g. Int, py.Any, ...
    * @tparam O the type of admissible observation for this environment, e.g. Int, py.Any, ...
    * @tparam AS the type of the space associate to actions, e.g. Space, Box, Dict, Tuple, ..
    * @tparam OS the type of the space associate to actions, e.g. Space, Box, Dict, Tuple, ..
    * @return an instance of E
    */
  //TEMPLATE METHOD
  def make[A, O, AS[a] <: Space[a], OS[o] <: Space[o]](name: String)(implicit
      obs: Reader[O],
      actionSpace: Reader[AS[A]],
      observationSpace: Reader[OS[O]]
  ): E[A, O, AS, OS]

  /** build an environment as gym.make(...) does, but typed.
    * @param name the name of the environment selected
    * @param obs type class needed to get the underlying python value
    * @param actionSpace type class needed to get the underlying python value
    * @param observationSpace type class needed to get the underlying python value
    * @param env type class needed to get the underlying python value
    * @param eq verify the constraint GE <:< E
    * @tparam A the type of admissible action for this environment, e.g. Int, py.Any, ...
    * @tparam O the type of admissible observation for this environment, e.g. Int, py.Any, ...
    * @tparam AS the type of the space associate to actions, e.g. Space, Box, Dict, Tuple, ..
    * @tparam OS the type of the space associate to actions, e.g. Space, Box, Dict, Tuple, ..
    * @tparam GE the type of environment selected
    * @return an instance of GE
    */
  def make[A, O, AS[a] <: Space[a], OS[o] <: Space[o], GE[_, _, _[a] <: Space[a], _[o] <: Space[o]]](
      name: String
  )(implicit
      obs: Reader[O],
      actionSpace: Reader[AS[A]],
      observationSpace: Reader[OS[O]],
      env: Reader[GE[A, O, AS, OS]],
      eq: GE[A, O, AS, OS] <:< E[A, O, AS, OS]
  ): GE[A, O, AS, OS] = make[A, O, AS, OS](name).as[GE[A, O, AS, OS]]

  /** build an environment as gym.make(...) does, but partially typed. Actions and observations are considered python value.
    * Action space instead, are accessed type safely.
    * @param name the name of the environment selected
    * @return an instance of E
    */
  def unsafe(name: String): E[py.Dynamic, py.Dynamic, Space, Space] = make[py.Dynamic, py.Dynamic, Space, Space](name)

  /** build an environment as gym.make(...) does, but partially typed. Actions and observations are considered python value.
    * Action space instead, are accessed type safely.
    * @param name the name of the environment selected
    * @param actionSpace type class needed to get the underlying python value
    * @param observationSpace type class needed to get the underlying python value
    * @tparam AS the type of the space associate to actions, e.g. Space, Box, Dict, Tuple, ..
    * @tparam OS the type of the space associate to actions, e.g. Space, Box, Dict, Tuple, ..
    * @return an instance of E
    */
  def make[AS[_A] <: Space[_A], OS[_O] <: Space[_O]](
      name: String
  )(implicit
      actionSpace: Reader[AS[py.Dynamic]],
      observationSpace: Reader[OS[py.Dynamic]]
  ): E[py.Dynamic, py.Dynamic, AS, OS] =
    make[py.Dynamic, py.Dynamic, AS, OS](name)
}
