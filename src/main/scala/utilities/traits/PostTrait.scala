package utilities.traits

import com.knoldus.JsonParsing.Post

trait PostTrait {
  def getPostById(id : String) : Post
  def getAllPostsByUserId(userId : String) : List[Post]
  def getAllPostsIdByUserId(userId : String) : List[String]
}
