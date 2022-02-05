package io.github.cric96
package gym.spaces

import me.shadaj.scalapy.py

//TODO
@py.native
trait Discrete[A] extends Space[A] {

  def n: Int = py.native

}
