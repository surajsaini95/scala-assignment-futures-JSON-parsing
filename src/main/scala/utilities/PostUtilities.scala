package utilities

import com.knoldus.JsonParsing.{JsonParsingPosts, Post}
import utilities.traits.PostTrait
class PostUtilities extends JsonParsingPosts with PostTrait {

   override def getPostById(id: String): Post = {
    val posts=getAllPosts
    val res=for{
      post<-posts
      if post.id == id
      res=post
    }yield res
    res.head
  }

  override def getAllPostsByUserId(userId: String): List[Post] = {
    val posts=getAllPosts
    val res=for{
      post<-posts
      if post.userId == userId
      res=post
    }yield res
    res
  }

  override def getAllPostsIdByUserId(userId: String): List[String] = {
    val posts=getAllPosts
    val res=for{
      post<-posts
      if post.userId == userId
      res=post.id
    }yield res
    res
  }
}
object PostUtilities{
  val postUtilities=new PostUtilities
}
