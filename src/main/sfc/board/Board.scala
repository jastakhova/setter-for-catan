package sfc.board

import sfc.placement._
import sfc.pieces.Tile.Tile
import sfc.pieces.Chits

import play.api.libs.json.{JsArray, JsObject}

/**
 * @author noel.yap@gmail.com
 */
class Board(configuration: Board.Configuration) {
  type PieceConfig = Pair[Position, Pair[Tile, Chits]]

  private val sortedConfiguration: List[(Position, (Tile, Chits))] = configuration.toList.sortWith {
    (lhs, rhs) =>
      val lCoordinate = lhs._1.coordinate
      val rCoordinate = rhs._1.coordinate

      math.abs(lCoordinate.x) + math.abs(lCoordinate.y) < math.abs(rCoordinate.x) + math.abs(rCoordinate.y)
  }

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

  def check: Boolean = {
    def oddsBound(from: Int, to: Int) = (from to to).sum

    configuration forall { pieceConfig: PieceConfig =>
      val position = pieceConfig._1

      position.vertices forall { vertex =>
        val intersection = Intersection(position.coordinate, vertex)
        val adjacentPositions = (
          HexagonPosition.adjacentPositions(intersection) ++
          ChevronPosition.adjacentPositions(intersection))
        //println("intersection = " + intersection)
        //println("adjacentPositions = " + adjacentPositions)

        val chitsList: List[Chits] = (configuration filterKeys { key =>
          //println("key = " + key + " " + (adjacentPositions contains key))
          adjacentPositions contains key
        } map { entry =>
          entry._2._2
        }).toList
        val adjacentCount = chitsList.length
        val adjacentOdds: Int = (chitsList map { chits =>
          //println(chits + ".odds = " + chits.odds)
          chits.odds
        }).sum

        val oddsRange = oddsBound(1, adjacentCount) to oddsBound(Vertex.maxId - adjacentCount, Vertex.maxId - 1)

        //println(adjacentCount + " " + adjacentOdds + " " + oddsRange)
        oddsRange contains adjacentOdds
      }
    }
  }
}

object Board {
  // TODO: use Configuration class/object
  type Configuration = Map[Position, Pair[Tile, Chits]]

  def apply(configuration: Configuration) = new Board(configuration)
}