package controllers

import play.api.libs.json.Json
import play.api.mvc._
import sfc.board.{SmallTradersAndBarbariansSpiralBoard, SmallSpiralBoard, SmallTradersAndBarbariansBoard, SmallBoard}

object Application extends Controller {
  def index = Action {
    Ok("small, small-spiral, small-traders-and-barbarians, small-traders-and-barbarians-spiral")
  }

  def small = Action {
    Ok(Json.toJson(Map("configuration" -> SmallBoard.board.toJson.value)))
  }

  def smallSpiral = Action { request =>
    Ok(Json.toJson(Map("configuration" -> SmallSpiralBoard.board.toJson.value)))
  }

  def smallTradersAndBarbarians = Action { request =>
    Ok(Json.toJson(Map("configuration" -> SmallTradersAndBarbariansBoard.board.toJson.value)))
  }

  def smallTradersAndBarbariansSpiral = Action { request =>
    Ok(Json.toJson(Map("configuration" -> SmallTradersAndBarbariansSpiralBoard.board.toJson.value)))
  }
}