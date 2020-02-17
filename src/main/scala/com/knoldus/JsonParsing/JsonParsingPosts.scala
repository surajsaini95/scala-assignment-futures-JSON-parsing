package com.knoldus.JsonParsing

import com.knoldus.JsonParsing.traits.ReadJsonData
import net.liftweb.json.DefaultFormats
import net.liftweb.json.JsonAST.{JNothing, JValue}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.implicitConversions
import scala.util.{Failure, Success}


class JsonParsingPosts extends ReadJsonData {
  def getAllPosts : List[Post] = {
    val url = "https://jsonplaceholder.typicode.com/posts"
    val jsonData: Future[String] = Future {
      readData(url)
    }
    val parsedJsonData = for {
      jsonCommentData <- jsonData
      parsedJsonData <- Future(JsonDataParsingPosts.parse(jsonCommentData))
    } yield parsedJsonData

    val postData = Await.result(parsedJsonData, 10.seconds)
    postData
   /* parsedJsonData onComplete {
      case Success(post) =>post
      case Failure(exception) => println(exception.getMessage)
    }

    List.empty[Post]*/
  }
}
case class Post(userId: String, id: String, title: String, body: String)


object JsonDataParsingPosts {

  implicit val formats: DefaultFormats.type = DefaultFormats

  def parse(jsonData: String): List[Post] = {
    val parsedJsonData = net.liftweb.json.parse(jsonData)
    //parsedJsonData.e
    parsedJsonData.children map { comment =>

      val userId = (comment \ "userId").extract[String]
      val id = (comment \ "id").extract[String]
      val title = (comment \ "title").extract[String]
      val body = (comment \ "body").extract[String]

      Post(userId, id, title, body)
    }
  }

  implicit def extract(json: JValue): String = json match {
    case JNothing => ""
    case data => data.extract[String]
  }
}
/*

object JsonDataParsingPostOb extends App with ReadJsonData {
  val url = "https://jsonplaceholder.typicode.com/posts"
  val jsonData: Future[String] = Future {
    readData(url)
  }
  val parsedJsonData = for {
    jsonCommentData <- jsonData
    parsedJsonData <- Future(JsonDataParsingPosts.parse(jsonCommentData))
  } yield parsedJsonData

  parsedJsonData onComplete {
    case Success(user) => println("User = " + user)
    case Failure(exception) => println(exception.getMessage)
  }
  val postData = Await.result(parsedJsonData, 10.seconds)
  //println(postData)
}*/
