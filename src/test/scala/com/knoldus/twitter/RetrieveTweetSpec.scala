package com.knoldus.twitter

import com.knoldus.twitter.view.{RetrieveTweet, RetrieveTweetOb}
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}
import twitter4j.Status

import scala.concurrent.Future


class RetrieveTweetSpec extends AsyncFlatSpec with BeforeAndAfterAll {

  var posts: Future[List[Status]] = _

  var retrieveTweet : RetrieveTweet = _

  override def beforeAll(): Unit = {
    posts = RetrieveTweetOb.posts
    retrieveTweet = new RetrieveTweet
  }

  behavior of "getTweetsCount"
  it should "return the count of the tweets" in {
    val expectedCount = 1;
    retrieveTweet.getTweetsCount(posts).map(actualCount => assert(expectedCount == actualCount))
  }

  behavior of "getAverageTweetsPerDay"
  it should "return the average tweets per day" in {
    val expectedActualTweet = 1;
    retrieveTweet.getAverageTweetsPerDay(posts).
      map(actualActualTweet => assert(expectedActualTweet == actualActualTweet))
  }

  behavior of "getAverageLikes"
  it should "return the average likes on a particular" in {
    val expectedAverageLikes = 1;
    retrieveTweet.getAverageLikes(posts).map(actualAverageLikes => assert(expectedAverageLikes == actualAverageLikes))
  }

  behavior of "getReTweetPerTweet"
  it should "return the re-tweets made on tweet" in {
    val expectedActualReTweetPerTweet = 1;
    retrieveTweet.getAverageTweetsPerDay(posts).
      map(actualActualReTweetPerTweet => assert(expectedActualReTweetPerTweet == actualActualReTweetPerTweet))
  }
}
