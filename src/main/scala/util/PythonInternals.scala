package org.cric96.github.io
package util

import me.shadaj.scalapy.py

/** give access to the underlying python type. */
trait PythonInternals {
  self: py.Any =>
  protected[this] lazy val me = this.as[py.Dynamic]
}
