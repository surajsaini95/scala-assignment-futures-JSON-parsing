package com.knoldus.twitter.controller

import twitter4j.{Query, Status, Twitter}

import scala.concurrent.Future

trait ReadTwitterStatus {
    def  getTwitterStatus(twitter: Twitter,hashTagQuery: Query) : Future[List[Status]]
}
