package com.knoldus.twitter

import com.knoldus.twitter.controller.{ReadTwitterStatusImpl, TwitterInstance, TwitterInstanceImpl}
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec
import twitter4j.Query

class ReadTwitterStatusImplSpec extends AsyncFlatSpec with BeforeAndAfterAll with MockitoSugar {

  val mockTwitterInstance : TwitterInstance = mock[TwitterInstanceImpl]
    var readTwitterStatusImpl : ReadTwitterStatusImpl = _

  override def beforeAll(): Unit = {
    readTwitterStatusImpl =new ReadTwitterStatusImpl(mockTwitterInstance)
  }

  "getTwitterStatus" should "return the list of custom tweets" in{
  when(mockTwitterInstance.getTwitterInstance).thenReturn((new TwitterInstanceImpl).getTwitterInstance)
  val actualCustomTweetList = readTwitterStatusImpl.getTwitterStatus(new Query("#suraj"))
    val expectedCustomTweetList = TestUtils.getTestData

    actualCustomTweetList.flatMap(actualCustomTweetList => expectedCustomTweetList
      .map(expectedCustomTweetList => assert(expectedCustomTweetList==actualCustomTweetList)))
  }
}
