package utilities

import com.knoldus.JsonParsing.{Comment, Post, User}

case class UserWithPostAndComments(user : User,posts : List[Post],comments : List[Comment])

object UserWithPostAndCommentsOb extends App{

  val allUsers=UserUtilities.userUtilities.getAllUsers
  val allPosts=PostUtilities.postUtilities.getAllPosts
  val allComments=CommentUtilities.commentUtilities.getAllComments

}