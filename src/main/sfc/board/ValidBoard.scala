package sfc.board

/**
 * @author noel.yap@gmail.com
 */
object ValidBoard {
  // TODO: use Akka
  def apply(piecesConfigSpec: Configuration.PiecesConfigSpec*): Board = {
    val board = Board(piecesConfigSpec: _*)

    if (board.check) {
      board
    } else {
      apply(piecesConfigSpec: _*)
    }
  }
}
