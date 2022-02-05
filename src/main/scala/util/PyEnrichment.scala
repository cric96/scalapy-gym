package io.github.cric96
package util

import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.PyQuote

object PyEnrichment {

  implicit class RichPyAny(val data: py.Any) {

    def pythonEquals(other: py.Any): Boolean = py"$data == $other".as[Boolean]

  }

}
