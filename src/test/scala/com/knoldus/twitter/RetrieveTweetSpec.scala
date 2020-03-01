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

}
