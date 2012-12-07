package sfc.placement

import play.api.libs.json.JsString

/**
 * @author noel.yap@gmail.com
 */
object VertexOrientation extends Enumeration {
  type VertexOrientation = Value
  
  val Top = Value
  val TopRight = Value
  val BottomRight = Value
  val Bottom = Value
  val BottomLeft = Value
  val TopLeft = Value

  class OrientationValue(orientation: Value) {
    def toJson = JsString(orientation.toString)
    def before = VertexOrientation((orientation.id + maxId - 1) % maxId)
    def after = VertexOrientation((orientation.id + 1) % maxId)
    def to(to: VertexOrientation): List[VertexOrientation] = {
      val ids: List[Int] = if (orientation.id > to.id) {
        (orientation.id until VertexOrientation.maxId).toList ::: (0 to to.id).toList
      } else {
        (orientation.id to to.id).toList
      }

      ids map {
        VertexOrientation(_)
      }
    }
  }

  implicit def value2OrientationValue(orientation: Value) = new OrientationValue(orientation)
}
