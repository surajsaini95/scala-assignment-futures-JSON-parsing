package com.knoldus.twitter.view

import java.sql.Timestamp

import com.knoldus.twitter.controller.{ReadTwitterStatusImpl, TwitterInstance, TwitterInstanceImpl}
import com.knoldus.twitter.model.ReTweet
import twitter4j._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.language.implicitConversions
import java.time.Duration

import com.knoldus.json.controller.DataParser

import scala.concurrent.duration._
import scala.io.Source
/**
 * This is a class used to retrieve tweets from Twitter.
 */
class RetrieveTweet {

  /**
   * the method getTweetsCount can be used to find the number of tweets made using #hashtag
   * @param posts list of status from twitter.
   * @return the count of the tweets made
   */
  def getTweetsCount(posts: Future[List[Status]]) : Future[Int] = {
    posts.map(post => post.length)
  }.fallbackTo(Future{-1})

  /**
   * the method getAverageTweetsPerDay can be used to find the number of average tweets per day on a #hashtag
   * @param posts list of status from twitter.
   * @return the average tweets per day
   */
  def getAverageTweetsPerDay(posts: Future[List[Status]]): Future[Long] = {
    val sortedPosts = posts.map(post => post.sortWith((a,b) => a.getCreatedAt.before(b.getCreatedAt)))

    val start = sortedPosts.map(sortedPost => sortedPost.head.getCreatedAt)
    val end = sortedPosts.map(sortedPost => sortedPost.reverse.head.getCreatedAt)

    val localStart = start.map(start => new Timestamp(start.getTime).toLocalDateTime)
    val localEnd = end.map(end => new Timestamp(end.getTime).toLocalDateTime)

    val duration = localEnd.map(localEnd =>
                  localStart.map(localStart =>
                    Duration.between(localStart,localEnd))).flatten

    duration.map(duration => posts.map(posts => posts.length/(duration.getSeconds/86400))).flatten
  }.fallbackTo(Future{-1})

  /**
   * the method getAverageLikes can be used to find the number of average likes per day on tweets
   * @param posts list of status from twitter.
   * @return the average likes on a particular #hashtag
   */
  def getAverageLikes(posts: Future[List[Status]]) : Future[Int] = {
    @scala.annotation.tailrec
    def getCount(posts : List[Status], counter : Int) : Int = {
      posts match {
        case p :: Nil => p.getFavoriteCount + counter
        case p :: res => getCount(res,p.getFavoriteCount + counter)
        case Nil      => 0
      }
    }
    posts.map(post => (getCount(post,0)/post.length).ceil.toInt)
  }.fallbackTo(Future{-1})

  /**
   * the method getReTweetPerTweet can be used to find the number of re-tweets made on tweet
   * @param posts list of status from twitter.
   * @return the re-tweets made on tweet
   */
  def getReTweetPerTweet(posts: Future[List[Status]]) : Future[List[ReTweet]] = {
    posts.map(post => post.map(p => ReTweet(p.getId,p.getRetweetCount)))
  }.fallbackTo(Future{List.empty[ReTweet]})

}
object Ob extends App{
  val retrieveTweet = new RetrieveTweet

  val data=Source.fromFile("./src/test/resources/testJsonDataPosts").toString()

  println(new JSONObject(data))


}
object RetrieveTweetOb extends App {

  val retrieveTweet = new RetrieveTweet
  val hashTagQuery = new Query("#suraj")
  val twitterInstance = new TwitterInstanceImpl

  /*val posts: Future[List[Status]] = Future {
    new ReadTwitterStatusImpl(twitterInstance).getTwitterStatus(hashTagQuery)
  }.fallbackTo(Future{List.empty[Status]})
*/

  val posts = Future{
    DataParser.parser[Status](Source.fromFile("./src/test/resources/testJsonDataPosts").getLines.mkString)
  }
  val tweetsCount = retrieveTweet.getTweetsCount(posts)
  val averageTweetsPerDay = retrieveTweet.getAverageTweetsPerDay(posts)
  val averageLikes = retrieveTweet.getAverageLikes(posts)
  val reTweetPerTweet =retrieveTweet.getReTweetPerTweet(posts)

  val result = Await.result(averageTweetsPerDay,8.second)

  println(averageTweetsPerDay)


}
