package io.github.cric96
package gym.spaces

import me.shadaj.scalapy.py

//TODO
@py.native
trait Discrete[Action] extends Space[Action] {

  def n: Int = py.native

}
