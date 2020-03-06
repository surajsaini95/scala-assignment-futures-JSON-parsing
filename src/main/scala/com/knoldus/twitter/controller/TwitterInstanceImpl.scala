package com.knoldus.twitter.controller

import com.typesafe.config.ConfigFactory
import twitter4j.auth.AccessToken
import twitter4j.{Twitter, TwitterFactory}

class TwitterInstanceImpl extends TwitterInstance {

  /**
   * method getTwitterInstance can be called to get the instance of Twitter
   *
   * @return the instance of type Twitter
   */
  def getTwitterInstance: Twitter = {

    //val config = ConfigFactory.load()

    /*val twitterInstance = new TwitterFactory().getInstance()
    twitterInstance.setOAuthConsumer(config.getString("consumer.key"),
      config.getString("consumer.secret"))
    twitterInstance.setOAuthAccessToken(new AccessToken(
      config.getString("token.key"),
      config.getString("token.secret")))
    twitterInstance*/

    val twitterInstance = new TwitterFactory().getInstance()
    twitterInstance.setOAuthConsumer("e6uS4phTxImI68qlA6h4V3zwR",
      "M8b4Q3sudgU9mNZgJx1onUlqQYi5h5YCK1GVacjAc8yHDAohFc")
    twitterInstance.setOAuthAccessToken(new AccessToken(
      "160922224-AKOoOasbqi3huqT7uyq4Og0Oqlucn8rKeD9IcUvU",
      "7HgIJUmjOX2AZThvVp7RPWsZwOrW1ffpvkEpjeBSQynnH"))
    twitterInstance
  }
}
