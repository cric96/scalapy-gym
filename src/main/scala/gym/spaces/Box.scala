package org.cric96.github.io
package gym.spaces

import me.shadaj.scalapy.py

@py.native
trait Box[Action] extends Space[Action] {
  val high: py.Any = py.native
  val low: py.Any = py.native
}
