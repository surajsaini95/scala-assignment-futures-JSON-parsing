package com.knoldus.jsonParsing

import com.knoldus.jsonParsing.traits.ReadJsonData
import net.liftweb.json.DefaultFormats
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.implicitConversions

/**
 * This is a Comment case class which is used to capture the details of a Comment
 * @param postId  it is the id of the post on which the comment is made
 * @param id  it is the unique id of the comment
 * @param name  it is the name of the user who has commented
 * @param email it is the email through which the comment is made
 * @param body it contains the content of the comment's body
 */
case class Comment(postId: String, id: String, name: String, email: String, body: String)

object JsonParsingComments extends ReadJsonData {

  /**
   *  method getAllComments is used to fetch the json data  of all the Comments from the specified url.
   * @return the list of model class Comment wrapped in Future
   */
  def getAllComments: Future[List[Comment]] = {
    val url = "https://jsonplaceholder.typicode.com/comments"

    val jsonData: Future[String] = Future(readData(url))

    val allCommentsFallback : Future[List[Comment]] = Future{ List.empty[Comment] }

    val parsedJsonData = for {
      jsonCommentData <- jsonData
      parsedJsonData <- Future(parse(jsonCommentData))
    } yield parsedJsonData

    val allComments = parsedJsonData fallbackTo allCommentsFallback

    allComments

  }

  /**
   * method parse is used to parse the data into model class Comment
   * @param jsonData it accepts a string of json data
   * @return the list of model class Comment
   */
  def parse(jsonData: String): List[Comment] = {
    val parsedJsonData = net.liftweb.json.parse(jsonData)
    parsedJsonData.children map { comment =>

      comment.extract[Comment]

    }
  }

  implicit val formats: DefaultFormats.type = DefaultFormats

}
