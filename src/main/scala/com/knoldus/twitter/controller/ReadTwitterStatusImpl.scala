package com.knoldus.twitter.controller

import twitter4j.{Query, Status, Twitter}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.JavaConverters._
import scala.concurrent.Future

class ReadTwitterStatusImpl extends ReadTwitterStatus{
  override def getTwitterStatus(twitter: Twitter,hashTagQuery: Query): Future[List[Status]] = Future{
    twitter.search(hashTagQuery).getTweets.asScala.toList
  }
}
