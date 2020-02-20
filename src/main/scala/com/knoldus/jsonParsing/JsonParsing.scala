package com.knoldus.jsonParsing

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * This is a case class used to model post with all comments
 * @param post contains the object of post data model
 * @param allComments contains list of the comments made on the post
 */
case class PostWithAllComments(post: Post, allComments:  List[Comment])

/**
 * This is a case class used to model user with all post and its respective comments
 * @param user contains the object of the user data model
 * @param postWithAllComments contains list of post with all comments
 */
case class UserWithPostAndComments(user: User, postWithAllComments: List[PostWithAllComments])

class JsonParsing {

  val allUsers: Future[List[User]] = JsonParsingUsers.getAllUsers
  val allPosts: Future[List[Post]] = JsonParsingPosts.getAllPosts
  val allComments : Future[List[Comment]]=JsonParsingComments.getAllComments

  val postWithAllComments: Future[List[PostWithAllComments]] = allPosts.map(posts=>
                                                                allComments.map(comments=>
                                                                              posts.map(post=>
                                                                        PostWithAllComments(post,
                                                                            comments.filter(_.postId==post.id))))).flatten


  val userWithPostAndComments: Future[List[UserWithPostAndComments]] =allUsers.map(users=>
                                                  postWithAllComments.map(postWithAllComment=>
                                                                                users.map(user=>
                                                                                  UserWithPostAndComments(user,
                                                                                    postWithAllComment.filter(_.post.userId==user.id))))).flatten

  val postWithMaxComments : Future[Post] = postWithAllComments.map(postWithAllComment =>
                                                                  postWithAllComment.sortWith((a,b) =>
                                                                    a.allComments.length >= b.allComments.length).head.post)


  val userIdWithMaxPosts : Future[String]  = postWithAllComments.map(postWithAllComment =>
                                                          postWithAllComment.sortWith((a,b) =>
                                                            a.allComments.length >= b.allComments.length).head.post.userId)

  /**
   *  method getUserWithMaxPosts can be used to find the user with maximum post
   * @return the name of the user with maximum posts
   */
  def getUserWithMaxPosts : Future[String] = {
    userIdWithMaxPosts.map(userIdWithMaxPost => allUsers.map( users => users.filter(_.id==userIdWithMaxPost).head.name)).flatten
  }

  /**
   *  method getUserWithMaxPostComments can be used to find the user with maximum comments on a post
   * @return the name of the user with maximum comments on a post
   */
  def getUserWithMaxPostComments : Future[String] = {
    postWithMaxComments.map(postWithMaxComment => allUsers.map( users => users.filter(_.id==postWithMaxComment.userId).head.name)).flatten
  }
}
