package com.knoldus.twitter.controller

import com.knoldus.twitter.model.CustomTweet
import twitter4j.{Query, Status}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReadTwitterStatusImpl(twitterInstance: TwitterInstance) extends ReadTwitterStatus{
  override def getTwitterStatus(hashTagQuery: Query): Future[List[CustomTweet]] = Future{

    twitterInstance.getTwitterInstance.search(hashTagQuery)
      .getTweets.asScala.toList
      .map(status => CustomTweet(status.getId,status.getFavoriteCount,
        status.getRetweetCount,status.getCreatedAt))
  }.fallbackTo(Future{List.empty[CustomTweet]})
}
