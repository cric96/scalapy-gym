package org.cric96.github.io
package gym.spaces

import me.shadaj.scalapy.py

@py.native
trait MultiDiscrete[Action] extends Space[Action] {}
