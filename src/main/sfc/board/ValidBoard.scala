package sfc.board

/**
 * @author noel.yap@gmail.com
 */
object ValidBoard {
  def apply(piecesConfigSpec: Configuration.PiecesConfigSpec*): Board = {
    val configuration = Configuration.apply(piecesConfigSpec: _*)
    val board = Board(configuration.configuration)

    if (board.check) {
      board
    } else {
      apply(piecesConfigSpec: _*)
    }
  }
}
