package com.knoldus.jsonParsing.controller

import com.knoldus.jsonParsing.model.{Comment, Post, User}
import net.liftweb.json.DefaultFormats


/**
 * contains parsing methods of json data.
 */
object DataParser {

  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * method postParser is used to parse the data into model class Post
   *
   * @param jsonData it accepts a string of json data
   * @return the list of model class Post
   */
  def postParser(jsonData: String): List[Post] = {

    try{
      val parsedJsonData = net.liftweb.json.parse(jsonData)
    parsedJsonData.children map { post =>
      post.extract[Post]
    }
    }catch {
      case exception: Exception => List.empty[Post]
    }

  }

  /**
   * method userParser is used to parse the data into model class User
   *
   * @param jsonData it accepts a string of json data
   * @return the list of model class User
   */
  def userParser(jsonData: String): List[User] = {

    try{
      val parsedJsonData = net.liftweb.json.parse(jsonData)
    parsedJsonData.children map { user =>
      user.extract[User]
    }
    }catch {
      case exception: Exception => List.empty[User]
    }
  }

  /**
   * method commentParser is used to parse the data into model class Comment
   *
   * @param jsonData it accepts a string of json data
   * @return the list of model class Comment
   */
  def commentParser(jsonData: String): List[Comment] = {

    try{
      val parsedJsonData = net.liftweb.json.parse(jsonData)
      parsedJsonData.children map { comment =>
        comment.extract[Comment]
      }
    }catch {
      case exception: Exception => List.empty[Comment]
    }
  }

}
