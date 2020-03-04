package com.knoldus.twitter.view

import java.sql.Timestamp

import com.knoldus.twitter.controller.TwitterInstance
import com.knoldus.twitter.model.ReTweet
import twitter4j._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.implicitConversions
import java.time.Duration

/**
 * This is a class used to retrieve tweets from Twitter.
 */
class RetrieveTweet {

  def getTwitterInstance: Twitter ={
    val consumerKey = "e6uS4phTxImI68qlA6h4V3zwR"
    TwitterInstance.getTwitterInstance(consumerKey)
  }
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

object RetrieveTweetOb extends App{

  val tweet = new RetrieveTweet


  val hashTagQuery = new Query("#suraj")

  val posts: Future[List[Status]] = Future {
    val list = twitter.search(hashTagQuery).getTweets.asScala.toList
    list
  }.fallbackTo(Future{List.empty[Status]})


  val tweetsCount = tweet.getTweetsCount(posts)

  val averageTweetsPerDay = tweet.getAverageTweetsPerDay(posts)

  val averageLikes = tweet.getAverageLikes(posts)

  val reTweetPerTweet =tweet.getReTweetPerTweet(posts)

  Thread.sleep(8000)

  println(averageTweetsPerDay)
}
