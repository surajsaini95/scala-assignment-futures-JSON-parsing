package com.knoldus.twitter.view

import com.knoldus.twitter.controller.TwitterInstance
import com.knoldus.twitter.model.ReTweet
import twitter4j._
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.implicitConversions

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
  def getAverageTweetsPerDay(posts: Future[List[Status]]): Future[Int] = {
    val sortedPosts = posts.map(post => post.sortWith((a,b) => a.getCreatedAt.before(b.getCreatedAt)))
    sortedPosts.map(post => post.reverse.head.getCreatedAt.compareTo(post.head.getCreatedAt))
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
        case p :: res => getCount(res,p.getFavoriteCount + counter)
        case p :: Nil => p.getFavoriteCount + counter
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

  val consumerKey = "e6uS4phTxImI68qlA6h4V3zwR"

  val twitter: Twitter =TwitterInstance.getTwitterInstance(consumerKey)

  val hashTag = new Query("#srk")

  val posts: Future[List[Status]] = Future {
    twitter.search(hashTag).getTweets.asScala.toList
  }.fallbackTo(Future{List.empty[Status]})


  val tweetsCount = tweet.getTweetsCount(posts)

  val averageTweetsPerDay = tweet.getAverageTweetsPerDay(posts)

  val averageLikes = tweet.getAverageLikes(posts)

  val reTweetPerTweet =tweet.getReTweetPerTweet(posts)

}
