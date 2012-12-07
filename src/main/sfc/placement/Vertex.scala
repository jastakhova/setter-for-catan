package sfc.placement

import sfc.placement.VertexOrientation._
/**
 * @author noel.yap@gmail.com
 */
object Vertex extends Enumeration {
  type Vertex = Value

  val Top = Value
  val TopRight = Value
  val BottomRight = Value
  val Bottom = Value
  val BottomLeft = Value
  val TopLeft = Value

  def apply(orientation: VertexOrientation): Vertex = {
    orientation match {
      case VertexOrientation.Top => Top
      case VertexOrientation.TopRight => TopRight
      case VertexOrientation.BottomRight => BottomRight
      case VertexOrientation.Bottom => Bottom
      case VertexOrientation.BottomLeft => BottomLeft
      case VertexOrientation.TopLeft => TopLeft
    }
  }

  class VertexValue(vertex: Value) {
    def isOnTop = this == Top || this == TopRight || this == TopLeft
    def isOnRight = this == TopRight || this == BottomRight
    def isOnLeft = this == BottomLeft || this == TopLeft
    def isOnBottom = this == BottomRight || this == Bottom || this == BottomLeft
    def before = Vertex((vertex.id + maxId - 1) % maxId)
    def after = Vertex((vertex.id + 1) % maxId)
    def to(to: Vertex): List[Vertex] = {
      val ids: List[Int] = if (vertex.id > to.id) {
        (vertex.id until Vertex.maxId).toList ::: (0 to to.id).toList
      } else {
        (vertex.id to to.id).toList
      }

      ids map {
        Vertex(_)
      }
    }
  }

  implicit def value2VertexValue(vertex: Value) = new VertexValue(vertex)
}
