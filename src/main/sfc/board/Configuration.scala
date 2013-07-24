package sfc.board

import scala.util.Random
import sfc.placement._
import sfc.pieces.Chits
import sfc.pieces.Tile.Tile

/**
 * @author noel.yap@gmail.com
 */
class Configuration(configuration: Map[Position, Pair[Tile, Chits]]) {
  def sorted: List[(Position, (Tile, Chits))] = configuration.toList.sortWith {
    (lhs, rhs) =>
      val lCoordinate = lhs._1.coordinate
      val rCoordinate = rhs._1.coordinate

      math.abs(lCoordinate.x) + math.abs(lCoordinate.y) < math.abs(rCoordinate.x) + math.abs(rCoordinate.y)
  }

  def check(adjacentPositionsToIntersection: Intersection => Set[Position]): Boolean = {
    def oddsBound(from: Int, to: Int) = (from to to).sum

    configuration forall { pieceConfig: Configuration.PieceConfig =>
      val position = pieceConfig._1

      position.vertices forall { vertex =>
        val intersection = Intersection(position.coordinate, vertex)
        val adjacentPositions = adjacentPositionsToIntersection(intersection)

        val chitsList = {
          (configuration filterKeys { key =>
            adjacentPositions contains key
          } map { entry =>
            entry._2._2
          }).toList
        }

        val adjacentCount = chitsList.length
        val adjacentOdds: Int = (chitsList map { chits =>
          chits.odds
        }).sum

        val oddsRange = oddsBound(1, adjacentCount) to oddsBound(Vertex.maxId - adjacentCount, Vertex.maxId - 1)

        //println(adjacentCount + " " + adjacentOdds + " " + oddsRange)
        oddsRange contains adjacentOdds
      }
    }
  }
}

object Configuration {
  type PieceConfig = Pair[Position, Pair[Tile, Chits]]
  type PiecesConfigSpec = Pair[IndexedSeq[Position], IndexedSeq[Pair[IndexedSeq[Tile], IndexedSeq[Chits]]]]

  def apply(piecesConfigSpec: PiecesConfigSpec*): Configuration = {
    // TODO: investigate whether or not it makes sense to use `Future` here
    new Configuration((piecesConfigSpec flatMap {
      it =>
        val coordinates = it._1
        val tileSets = it._2

        val tileSet = (tileSets map {
          it =>
            shuffleZip(it._1, it._2)
        }).flatten

        coordinates zip Random.shuffle(tileSet)
    }).toMap)
  }

  private def shuffleZip[T, U](lhs: IndexedSeq[T], rhs: IndexedSeq[U]): IndexedSeq[Pair[T, U]] = {
    require(lhs.size == rhs.size)

    // TODO: use `breeze.stats.random.MersenneTwister` from scalanlp.org
    Random.shuffle(lhs) zip Random.shuffle(rhs)
  }
}
