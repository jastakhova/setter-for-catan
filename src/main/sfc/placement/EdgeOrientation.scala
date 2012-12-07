package sfc.placement

import play.api.libs.json.JsString

/**
 * @author noel.yap@gmail.com
 */
object EdgeOrientation extends Enumeration {
  type EdgeOrientation = Value
  
  val TopRight = Value
  val Right = Value
  val BottomRight = Value
  val BottomLeft = Value
  val Left = Value
  val TopLeft = Value

  class OrientationValue(orientation: Value) {
    def toJson = JsString(orientation.toString)
    def before = EdgeOrientation((orientation.id + maxId - 1) % maxId)
    def after = EdgeOrientation((orientation.id + 1) % maxId)
    def to(to: EdgeOrientation): List[EdgeOrientation] = {
      val ids: List[Int] = if (orientation.id > to.id) {
        (orientation.id until EdgeOrientation.maxId).toList ::: (0 to to.id).toList
      } else {
        (orientation.id to to.id).toList
      }

      ids map {
        EdgeOrientation(_)
      }
    }
  }

  implicit def value2OrientationValue(orientation: Value) = new OrientationValue(orientation)
}
