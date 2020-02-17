package utilities

import com.knoldus.JsonParsing.{Comment, JsonParsingPosts, Post, User}
import utilities.traits.PostTrait
class PostUtilities extends JsonParsingPosts with PostTrait {

  val posts=getAllPosts

  override def getPostById(id: String): Post = {
     val res=for{
      post<-posts
      if post.id == id
      res=post
    }yield res
    res.head
  }

  override def getAllPostsByUserId(userId: String): List[Post] = {
    //val posts=getAllPosts
    val res=for{
      post<-posts
      if post.userId == userId
      res=post
    }yield res
    res
  }

  override def getAllPostsIdByUserId(userId: String): List[String] = {
    //val posts=getAllPosts
    val res=for{
      post<-posts
      if post.userId == userId
      res=post.id
    }yield res
    res
  }

  override def getAllPostIdListByUserId(userId: String): List[String] = {
   // val posts=getAllPosts
    val res=for{
      post<-posts
      if post.userId == userId
      res=post.id
    }yield res
    res
  }

  override def postWithAllCommentsById(userId : String) : List[PostWithAllComments] = for{
    post<-posts
    if(post.userId==userId)
    res=PostWithAllComments(post,CommentUtilities.commentUtilities.getAllCommentsByPostId(post.id))
  }yield res


}
object PostUtilities{
  val postUtilities=new PostUtilities
}
