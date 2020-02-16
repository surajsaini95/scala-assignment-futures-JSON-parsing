package utilities.traits

import com.knoldus.JsonParsing.Comment

trait CommentTrait {
  def getAllCommentsByPostId(postId : List[String]) : List[Comment]
  def getAllCommentsByPostId(postId : String) : List[Comment]
  def getCommentById(id : String) : Comment
}
