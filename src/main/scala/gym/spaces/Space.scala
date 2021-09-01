package io.github.cric96
package gym.spaces

import me.shadaj.scalapy.py
import me.shadaj.scalapy.readwrite.Reader
import me.shadaj.scalapy.readwrite.Writer

/** refer to https://github.com/openai/gym/blob/master/gym/spaces/space.py
  * @tparam Action the type of admissible action for this environment, e.g. Int, py.Any, ...
  */
@py.native
trait Space[Action] extends py.Object {
  /** @param reader type class needed to get the underlying python value
    * @return refer to https://github.com/openai/gym/blob/master/gym/spaces/space.py
    */
  def sample()(implicit reader: Reader[Action]): Action = py.native

  /** @param action refer to https://github.com/openai/gym/blob/master/gym/spaces/space.py
    * @param wr
    * @return refer to https://github.com/openai/gym/blob/master/gym/spaces/space.py
    */
  def contains(action: Action)(implicit wr: Writer[Action]): Boolean = py.native
}
