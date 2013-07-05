package sfc.board

import sfc.placement._
import sfc.pieces.Chits

import play.api.libs.json.{JsArray, JsObject}

/**
 * @author noel.yap@gmail.com
 */
class Board(configuration: Configuration) {
  private val sortedConfiguration = configuration.sorted

  override def toString = {
    sortedConfiguration map { pp =>
      val position = pp._1
      val pieces = pp._2

      position + " -> " + (if (pieces._2 != Chits(0)) {
        pieces
      } else {
        pieces._1
      })
    } mkString "\n"
  }

  def toJson: JsArray = {
    JsArray(sortedConfiguration map { pp =>
      val position = pp._1
      val pieces = pp._2
      val tile = pieces._1
      val chits = pieces._2

      JsObject(Seq(
        "position" -> position.toJson,
        "tile" -> tile.toJson,
        "chits" -> chits.toJson
      ))
    })
  }

  def check: Boolean = configuration.check { intersection: Intersection =>
    HexagonPosition.adjacentPositions(intersection) ++ ChevronPosition.adjacentPositions(intersection)
  }
}

object Board {
  def apply(configuration: Configuration.PiecesConfigSpec*) = new Board(Configuration(configuration: _*))
}