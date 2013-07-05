package sfc.board

/**
 * @author noel.yap@gmail.com
 */
object ValidCount {
  // TODO: use Akka
  def apply(piecesConfigSpec: Configuration.PiecesConfigSpec*): (Int => Int) = { sampleSize: Int =>
    var result = 0

    for (i <- 1 to sampleSize) {
      val board = Board(piecesConfigSpec: _*)

      if (board.check) {
        result += 1
      }
    }

    result
  }
}
