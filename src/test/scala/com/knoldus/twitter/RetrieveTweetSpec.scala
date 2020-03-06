package com.knoldus.twitter

import com.knoldus.twitter.view.RetrieveTweet
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}

class RetrieveTweetSpec extends AsyncFlatSpec with BeforeAndAfterAll {

  var retrieveTweet : RetrieveTweet = _

  override def beforeAll(): Unit = {
    retrieveTweet = new RetrieveTweet
  }


  behavior of "getTweetsCount"
  it should "return the count of the tweets" in {
    val expectedCount = 15
    retrieveTweet.getTweetsCount(TestUtils.getTestData).map(actualCount => assert(expectedCount == actualCount))
  }

  behavior of "getAverageTweetsPerDay"
  it should "return the average tweets per day" in {
    val expectedActualTweet = 3
    retrieveTweet.getAverageTweetsPerDay(TestUtils.getTestData).
      map(actualActualTweet => assert(expectedActualTweet == actualActualTweet))
  }

  behavior of "getAverageLikes"
  it should "return the average likes on a particular" in {
    val expectedAverageLikes = 1
    retrieveTweet.getAverageLikes(TestUtils.getTestData).map(actualAverageLikes => assert(expectedAverageLikes == actualAverageLikes))
  }

  behavior of "getReTweetPerTweet"
  it should "return the re-tweets made on tweet" in {
    val expectedActualReTweetPerTweet = TestUtils.getExpectedActualReTweetPerTweetList
    retrieveTweet.getReTweetPerTweet(TestUtils.getTestData).
      map(actualActualReTweetPerTweet => assert(expectedActualReTweetPerTweet == actualActualReTweetPerTweet))
  }


}
