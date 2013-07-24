#!/bin/bash

exec ${SCALA_HOME}/bin/scala "$0" "$@" 2>&1
!#

import sfc.board._

/*
 * @author noel.yap@gmail.com
 */
object `setter-for-catan` {
  def main(args: Array[String]) {
    args.length match {
      case 1 => {
        // TODO: use graphics to display board
        println(args(0) match {
        case "small" => SmallBoard.board
        case "small-spiral" => SmallSpiralBoard.board
        case "small-traders-and-barbarians" => SmallTradersAndBarbariansBoard.board
        case "small-traders-and-barbarians-spiral" => SmallTradersAndBarbariansSpiralBoard.board
        })
      }
      case 2 => {
        if (args(1) == "count") {
          val probability = args(0) match {
            case "small" => SmallBoard.probability
            case "small-spiral" => SmallSpiralBoard.probability
            case "small-traders-and-barbarians" => SmallTradersAndBarbariansBoard.probability
            case "small-traders-and-barbarians-spiral" => SmallTradersAndBarbariansSpiralBoard.probability
          }

          println (probability)
        }
      }
    }
  }
}
