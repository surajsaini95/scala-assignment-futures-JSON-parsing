package utilities

import com.knoldus.JsonParsing.{Comment, Post, User}


case class PostWithAllComments(post :Post,allComments :List[Comment])
case class UserWithPostAndComments(user : User,postWithAllComments : List[PostWithAllComments])

case class UserWithPostCount(user: User,postCount : Int)

class JsonParsing

object JsonParsingOb extends App{

  val allUsers=UserUtilities.userUtilities.users
  val allPosts=PostUtilities.postUtilities.posts
  val allComments=CommentUtilities.commentUtilities.comments

  val postWithAllComments = for{
    post<-allPosts
  }yield PostWithAllComments(post,CommentUtilities.commentUtilities.getAllCommentsByPostId(post.id))


  val userWithPostAndComments =for{
    user<-allUsers
  } yield  UserWithPostAndComments(user,PostUtilities.postUtilities.postWithAllCommentsById(user.id))

  val userWithPostCount = for{
    userWithPostAndComment<-userWithPostAndComments
  }yield UserWithPostCount(userWithPostAndComment.user,userWithPostAndComment.postWithAllComments.length)

  val userWithMaxPostCount = userWithPostCount.reduceLeft((a,b) => if(a.postCount>b.postCount) a else b)
  
  val userWithPostCommentCount = for{
    userWithPostAndComment<-postWithAllComments
  }yield (userWithPostAndComment.post.id,userWithPostAndComment.allComments.length)

  val postIdWithMaxCommentCount = userWithPostCommentCount.reduceLeft((a,b) => if(a._2>b._2) a else b)._1

  val postWithMaxCommentCount=PostUtilities.postUtilities.getPostById(postIdWithMaxCommentCount)
  val userWithMaxPostCommentCount=UserUtilities.userUtilities.getUserById(postWithMaxCommentCount.userId)

  println(userWithMaxPostCount.user.name + "has most posts")
  println(userWithMaxPostCommentCount.name + "has most comments")

}