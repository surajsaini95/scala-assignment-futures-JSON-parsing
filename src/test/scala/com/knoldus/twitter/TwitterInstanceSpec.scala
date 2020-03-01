package com.knoldus.twitter

import com.knoldus.twitter.controller.TwitterInstance
import org.scalatest.{AsyncFlatSpec, FlatSpec}

class TwitterInstanceSpec extends FlatSpec {

  behavior of "getTwitterInstance"
  it should "instance of type Twitter" in {
    val consumerKey = "e6uS4phTxImI68qlA6h4V3zwR"
    val twitter = TwitterInstance.getTwitterInstance(consumerKey)
   assert(twitter.getScreenName == "TheGauravRawat")
  }

  it should "throw exception as credentials are invalid" in {
    try{
      val consumerKey = ""
      val twitter = TwitterInstance.getTwitterInstance(consumerKey)
    }catch{
      case exception: Exception => assert(exception.getMessage == "failed to get twitter instance")
    }
  }

}
