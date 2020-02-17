package com.knoldus.JsonParsing

import com.knoldus.JsonParsing.traits.ReadJsonData
import net.liftweb.json.DefaultFormats
import net.liftweb.json.JsonAST.{JNothing, JValue}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.implicitConversions
import scala.util.{Failure, Success}


class JsonParsingComments extends ReadJsonData {
  def getAllComments: List[Comment] = {
    val url = "https://jsonplaceholder.typicode.com/comments"
    val jsonData: Future[String] = Future {
      readData(url)
    }
    val parsedJsonData = for {
      jsonCommentData <- jsonData
      parsedJsonData <- Future(JsonDataParsingComments.parse(jsonCommentData))
    } yield parsedJsonData

    val commentData = Await.result(parsedJsonData, 10.seconds)
    commentData
    /*parsedJsonData onComplete {
      case Success(comments) => comments
      case Failure(exception) => println(exception.getMessage)

    }
    List.empty[Comment]*/
  }
}
case class Comment(postId: String, id: String, name: String, email: String, body: String)

object JsonDataParsingComments {

  def parse(jsonData: String): List[Comment] = {
    val parsedJsonData = net.liftweb.json.parse(jsonData)
    parsedJsonData.children map { comment =>

      val postId = (comment \ "postId").extract[String]
      val id = (comment \ "id").extract[String]
      val name = (comment \ "name").extract[String]
      val email = (comment \ "email").extract[String]
      val body = (comment \ "body").extract[String]

      Comment(postId, id, name, email, body)
    }
  }

  implicit val formats: DefaultFormats.type = DefaultFormats

  implicit def extract(json: JValue): String = json match {
    case JNothing => ""
    case data => data.extract[String]
  }
}
/*

object JsonDataParsingCommentsOb extends App with ReadJsonData {
  val url = "https://jsonplaceholder.typicode.com/comments"
  val jsonData: Future[String] = Future {
    readData(url)
  }
  val parsedJsonData = for {
    jsonCommentData <- jsonData
    parsedJsonData <- Future(JsonDataParsingComments.parse(jsonCommentData))
  } yield parsedJsonData
  parsedJsonData onComplete {
    case Success(user) => println("User = " + user)
    case Failure(exception) => println(exception.getMessage)
  }
  val commentData = Await.result(parsedJsonData, 10.seconds)
  //println(commentData)
}
*/
