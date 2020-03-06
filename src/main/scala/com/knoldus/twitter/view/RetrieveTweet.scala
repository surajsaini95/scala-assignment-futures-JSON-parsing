package com.knoldus.twitter.view

import java.sql.Timestamp
import com.knoldus.twitter.controller.{ReadTwitterStatusImpl, TwitterInstanceImpl}
import com.knoldus.twitter.model.{CustomTweet, ReTweet}
import twitter4j._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.language.implicitConversions
import java.time.Duration

import scala.concurrent.duration._

/**
 * This is a class used to retrieve tweets from Twitter.
 */
class RetrieveTweet {

  /**
   * the method getTweetsCount can be used to find the number of tweets made using #hashtag
   * @param listOfCustomTweets list of custom status from twitter.
   * @return the count of the tweets made
   */
  def getTweetsCount(listOfCustomTweets: Future[List[CustomTweet]]) : Future[Int] = {
      listOfCustomTweets.map(customTweets => customTweets.length)
  }.fallbackTo(Future{-1})

  /**
   * the method getAverageTweetsPerDay can be used to find the number of average tweets per day on a #hashtag
   * @param listOfCustomTweets list of custom status from twitter.
   * @return the average tweets per day
   */
  def getAverageTweetsPerDay(listOfCustomTweets: Future[List[CustomTweet]]): Future[Long] = {
    val sortedListOfCustomTweets = listOfCustomTweets.map(customTweets => customTweets.sortWith((a,b) => a.createdAt.before(b.createdAt)))

    val start = sortedListOfCustomTweets.map(sortedPost => sortedPost.head.createdAt)
    val end = sortedListOfCustomTweets.map(sortedPost => sortedPost.reverse.head.createdAt)

    val localStart = start.map(start => new Timestamp(start.getTime).toLocalDateTime)
    val localEnd = end.map(end => new Timestamp(end.getTime).toLocalDateTime)

    val duration = localEnd.map(localEnd =>
                  localStart.map(localStart =>
                    Duration.between(localStart,localEnd))).flatten
    val secondsInOneDay = 86400
    duration.map(duration => listOfCustomTweets.map(customTweets => customTweets.length/(duration.getSeconds/secondsInOneDay))).flatten
  }.fallbackTo(Future{-1})

  /**
   * the method getAverageLikes can be used to find the number of average likes per day on tweets
   * @param listOfCustomTweets list of custom status from twitter.
   * @return the average likes on a particular #hashtag
   */
  def getAverageLikes(listOfCustomTweets: Future[List[CustomTweet]]) : Future[Int] = {
    @scala.annotation.tailrec
    def getCount(listOfCustomTweets : List[CustomTweet], counter : Int) : Int = {
      listOfCustomTweets match {
        case p :: Nil => p.favoriteCount + counter
        case p :: res => getCount(res,p.favoriteCount + counter)
        case Nil      => 0
      }
    }
    listOfCustomTweets.map(customTweets => (getCount(customTweets,0)/customTweets.length).ceil.toInt)
  }.fallbackTo(Future{-1})

  /**
   * the method getReTweetPerTweet can be used to find the number of re-tweets made on tweet
   * @param listOfCustomTweets list of custom status from twitter.
   * @return the re-tweets made on tweet
   */
  def getReTweetPerTweet(listOfCustomTweets: Future[List[CustomTweet]]) : Future[List[ReTweet]] = {
    listOfCustomTweets.map(customTweets => customTweets.map(customTweet => ReTweet(customTweet.id,customTweet.reTweetCount)))
  }.fallbackTo(Future{List.empty[ReTweet]})

}
object Ob extends App{
  val retrieveTweet = new RetrieveTweet
  val hashTagQuery = new Query("#suraj")
  val twitterInstance = new TwitterInstanceImpl

  val posts: Future[List[CustomTweet]] = new ReadTwitterStatusImpl(twitterInstance).getTwitterStatus(hashTagQuery)

  
  val tweetsCount = retrieveTweet.getTweetsCount(posts)
  val averageTweetsPerDay = retrieveTweet.getAverageTweetsPerDay(posts)
  val averageLikes = retrieveTweet.getAverageLikes(posts)
  val reTweetPerTweet =retrieveTweet.getReTweetPerTweet(posts)

  val result = Await.result(reTweetPerTweet,5.second)

  println("tweet count : "+tweetsCount)
  println("averageTweetsPerDay  : "+averageTweetsPerDay)
  println("averageLikes : "+averageLikes)
  println("reTweetPerTweet  : "+reTweetPerTweet)


}