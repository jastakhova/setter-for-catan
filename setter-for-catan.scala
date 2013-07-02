#!/bin/bash

classpath="${CLASSPATH}"
unset CLASSPATH # this is needed to prevent Scala object not found errors

exec ${SCALA_HOME}/bin/scala -cp "${classpath}" "$0" "$@" 2>&1
!#

import sfc.board._

/*
 * @author noel.yap@gmail.com
 */
object `setter-for-catan` {
  def main(args: Array[String]) {
    if (args.length == 1) {
      println(args(0) match {
        case "small" => SmallBoard.board
        case "small-spiral" => SmallSpiralBoard.board
        case "small-traders-and-barbarians" => SmallTradersAndBarbariansBoard.board
        case "small-traders-and-barbarians-spiral" => SmallTradersAndBarbariansSpiralBoard.board
      })
    }
  }
}
