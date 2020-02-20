package com.knoldus.twitter.traits

import twitter4j.auth.AccessToken
import twitter4j.{Twitter, TwitterFactory}

/**
 * this is a trait which can be used to get the instance of the twitter through factory method
 */
trait TwitterInstance {

  /**
   * method getTwitterInstance can be called to get the instance of Twitter
   * @return the instance of type Twitter
   */
  def getTwitterInstance : Twitter = {
    val consumerKey = "e6uS4phTxImI68qlA6h4V3zwR"
    val consumerSecret = "M8b4Q3sudgU9mNZgJx1onUlqQYi5h5YCK1GVacjAc8yHDAohFc"
    val token = "160922224-AKOoOasbqi3huqT7uyq4Og0Oqlucn8rKeD9IcUvU"
    val tokenSecret = "7HgIJUmjOX2AZThvVp7RPWsZwOrW1ffpvkEpjeBSQynnH"

    val twitterInstance = new TwitterFactory().getInstance()
    twitterInstance.setOAuthConsumer(consumerKey,consumerSecret)
    twitterInstance.setOAuthAccessToken(new AccessToken(token,tokenSecret))
    twitterInstance
  }
}
