package com.knoldus.jsonParsing

import com.knoldus.jsonParsing.traits.ReadJsonData
import net.liftweb.json.DefaultFormats
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.implicitConversions

/**
 * This is a Post case class which is used to capture the details of a Post
 * @param userId it is the id of the user who has created the post
 * @param id it is the unique id of the post
 * @param title it is the title of the post
 * @param body it is the body of the post
 */
case class Post(userId: String, id: String, title: String, body: String)

object JsonParsingPosts extends ReadJsonData {

  /**
   *  method getAllPosts is used to fetch the json data  of all the posts from the specified url.
   * @return the list of model class Post wrapped in Future
   */
  def getAllPosts : Future[List[Post]] = {
    val url = "https://jsonplaceholder.typicode.com/posts"
    val jsonData: Future[String] = Future {
      readData(url)
    }
    val allPostsFallback : Future[List[Post]] = Future{ List.empty[Post] }

    val parsedJsonData: Future[List[Post]] = for {
      jsonCommentData <- jsonData
      parsedJsonData <- Future(parse(jsonCommentData))
    } yield parsedJsonData

    val allPosts = parsedJsonData fallbackTo allPostsFallback

    allPosts
  }

  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * method parse is used to parse the data into model class Post
   * @param jsonData it accepts a string of json data
   * @return the list of model class Post
   */
  def parse(jsonData: String): List[Post] = {
    val parsedJsonData = net.liftweb.json.parse(jsonData)

    parsedJsonData.children map { post =>
      post.extract[Post]
    }
  }

}
