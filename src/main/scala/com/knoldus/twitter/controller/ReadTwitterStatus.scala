package com.knoldus.twitter.controller

import com.knoldus.twitter.model.CustomTweet
import twitter4j.Query

import scala.concurrent.Future

trait ReadTwitterStatus {
    def  getTwitterStatus(hashTagQuery: Query) : Future[List[CustomTweet]]
}
