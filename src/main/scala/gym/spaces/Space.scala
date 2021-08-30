package org.cric96.github.io
package gym.spaces

import me.shadaj.scalapy.py
import me.shadaj.scalapy.readwrite.Reader
import me.shadaj.scalapy.readwrite.Writer

@py.native
trait Space[Action] extends py.Object {
  def sample()(implicit reader: Reader[Action]): Action = py.native
  def contains(action: Action)(implicit wr: Writer[Action]): Boolean = py.native
}
