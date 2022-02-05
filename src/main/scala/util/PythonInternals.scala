package io.github.cric96
package util

import me.shadaj.scalapy.py

/** give access to the underlying python type. */
trait PythonInternals {
  self: py.Any =>

  protected[this] lazy val pyThis = this.as[py.Dynamic]

}
