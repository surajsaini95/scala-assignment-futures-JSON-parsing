package utilities.traits

import com.knoldus.JsonParsing.{Comment, Post, User}
import utilities.PostWithAllComments

trait PostTrait {
  def getPostById(id : String) : Post
  def getAllPostsByUserId(userId : String) : List[Post]
  def getAllPostsIdByUserId(userId : String) : List[String]
  def getAllPostIdListByUserId(userId : String) : List[String]
  def postWithAllCommentsById(userId : String) : List[PostWithAllComments]
}
