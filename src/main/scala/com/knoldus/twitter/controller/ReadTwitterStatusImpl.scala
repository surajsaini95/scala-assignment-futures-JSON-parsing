package com.knoldus.twitter.controller

import twitter4j.{Query, Status}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReadTwitterStatusImpl(twitterInstance: TwitterInstance) extends ReadTwitterStatus{
  override def getTwitterStatus(hashTagQuery: Query): Future[List[Status]] = Future{
    twitterInstance.getTwitterInstance.search(hashTagQuery).getTweets.asScala.toList
  }
}
