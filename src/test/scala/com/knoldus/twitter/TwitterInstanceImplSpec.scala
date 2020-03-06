package com.knoldus.twitter

import com.knoldus.twitter.controller.TwitterInstanceImpl
import org.scalatest.FlatSpec
import twitter4j.Twitter

class TwitterInstanceImplSpec extends FlatSpec {

  behavior of "getTwitterInstance"
  it should "return the instance of type Twitter" in {
    val twitter = (new TwitterInstanceImpl).getTwitterInstance
    assert(twitter.isInstanceOf[Twitter])
  }
}
