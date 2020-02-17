package utilities

import com.knoldus.JsonParsing.{JsonParsingComments, Comment}
import utilities.traits.CommentTrait
class CommentUtilities extends JsonParsingComments with CommentTrait {

  val comments=getAllComments
  override def getAllCommentsByPostId(postId : String) : List[Comment] ={

    val res=for{
      comment<-comments
      if comment.postId == postId
      res=comment
    }yield res
    res
  }

  override def getCommentById(id: String): Comment = {
    //val comments=getAllComments
    val res=for{
      comment<-comments
      if comment.id == id
      res=comment
    }yield res
    res.head
  }

  override def getAllCommentsByPostId(postId: List[String]): List[Comment] = {
   // val comments=getAllComments

    val res=for{
      comment<-comments
      post<-postId
      if comment.postId == post
        res=comment
    }yield res
    res
  }

}
object CommentUtilities{
  val commentUtilities=new CommentUtilities
}
