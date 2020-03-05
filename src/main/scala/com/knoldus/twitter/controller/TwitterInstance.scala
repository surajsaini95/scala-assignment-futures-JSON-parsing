package com.knoldus.twitter.controller

import twitter4j.auth.AccessToken
import twitter4j.{Twitter, TwitterFactory}

/**
 * this trait can be used to get the instance of the Twitter through factory method.
 */
trait TwitterInstance {

  /**
   * method getTwitterInstance can be called to get the instance of Twitter
   * @return the instance of type Twitter
   */
  def getTwitterInstance : Twitter
}
