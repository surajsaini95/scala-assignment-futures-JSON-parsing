package com.knoldus.twitter.controller

import twitter4j.auth.AccessToken
import twitter4j.{Twitter, TwitterFactory}

/**
 * this object can be used to get the instance of the Twitter through factory method
 */
object TwitterInstance {

  /**
   * method getTwitterInstance can be called to get the instance of Twitter
   * @return the instance of type Twitter
   */
  def getTwitterInstance(consumerKey : String) : Twitter = {

    val consumerSecret = "M8b4Q3sudgU9mNZgJx1onUlqQYi5h5YCK1GVacjAc8yHDAohFc"
    val token = "160922224-AKOoOasbqi3huqT7uyq4Og0Oqlucn8rKeD9IcUvU"
    val tokenSecret = "7HgIJUmjOX2AZThvVp7RPWsZwOrW1ffpvkEpjeBSQynnH"

   try{
     val twitterInstance = new TwitterFactory().getInstance()
     twitterInstance.setOAuthConsumer(consumerKey,consumerSecret)
     twitterInstance.setOAuthAccessToken(new AccessToken(token,tokenSecret))
     twitterInstance
   }catch {
     case exception: Exception =>throw new Exception("failed to get twitter instance")
   }
  }


}
