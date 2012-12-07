#!/bin/bash

scala_home=/opt/scala-2.9.2

script_dir=$(cd $(dirname "$0") >/dev/null; pwd -P)
classes_dir=${script_dir}/build/classes
src_dir=${script_dir}/main

mkdir -p ${classes_dir}
${scala_home}/bin/scalac -d ${classes_dir} $(find ${src_dir} -name '*.scala')

exec ${scala_home}/bin/scala -deprecation -classpath ${classes_dir} "$0" "$@"
!#

import sfc.board._

/**
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
