package com.knoldus.twitter

import com.knoldus.twitter.traits.TwitterInstance
import twitter4j._
import scala.language.implicitConversions
import scala.concurrent.Future
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This is a class used to retrieve tweets from Twitter.
 */
class RetrieveTweet extends TwitterInstance{

  val twitter=getTwitterInstance

  val hashTag = new Query("#love")

  val posts = Future {
    twitter.search(hashTag).getTweets.asScala.toList
  }

  /**
   * the method getTweetsCount can be used to find the number of tweets made using #hashtag
   * @return the count of the tweets made
   */
  def getTweetsCount : Future[Int] = {
    posts.map(post => post.length)
  }

  /**
   * the method getAverageTweetsPerDay can be used to find the number of average tweets per day on a #hashtag
   * @return the average tweets per day
   */
  def getAverageTweetsPerDay: Future[Int] = {
    val sortedPosts = posts.map(post => post.sortWith((a,b) => a.getCreatedAt.before(b.getCreatedAt)))
    sortedPosts.map(post => post.reverse.head.getCreatedAt.compareTo(post.head.getCreatedAt))
  }

  /**
   * the method getAverageLikes can be used to find the number of average likes per day on tweets
   * * @return the average likes on a particular #hashtag
   */
  def getAverageLikes : Future[Int] = {
    def getCount(posts : List[Status],counter : Int) : Int = {
      posts match {
        case p :: res => getCount(res,p.getFavoriteCount + counter)
        case p :: Nil => p.getFavoriteCount + counter
        case Nil      => 0
      }
    }
    posts.map(post => (getCount(post,0)/post.length).ceil.toInt)
  }

  /**
   * the method getReTweetPerTweet can be used to find the number of re-tweets made on tweet
   * * @return the re-tweets made on tweet
   */
  def getReTweetPerTweet : Future[List[(Long,Int)]] = {
    posts.map(post => post.map(p => (p.getId,p.getRetweetCount)))
  }

}
object RetrieveTweetOb extends App{

  val tweet = new RetrieveTweet

  val tweetsCount = tweet.getTweetsCount

  val averageTweetsPerDay = tweet.getAverageTweetsPerDay

  val averageLikes = tweet.getAverageLikes

  val reTweetPerTweet =tweet.getReTweetPerTweet


  Thread.sleep(10000)

  println(tweetsCount)
  println(averageTweetsPerDay)
  println(averageLikes)
  println(reTweetPerTweet)



}
