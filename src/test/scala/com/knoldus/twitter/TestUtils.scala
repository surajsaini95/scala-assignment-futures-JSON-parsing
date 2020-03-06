package com.knoldus.twitter

import java.text.SimpleDateFormat
import scala.concurrent.ExecutionContext.Implicits.global
import com.knoldus.twitter.model.{CustomTweet, ReTweet}
import scala.concurrent.Future

object TestUtils {

  def getTestData : Future[List[CustomTweet]] = Future{
    val  formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy")
    List(CustomTweet(1235703638187577349L,0,0,formatter.parse("Fri Mar 06 04:38:00 IST 2020")),
      CustomTweet(1235691216336904192L,0,0,formatter.parse("Fri Mar 06 03:48:38 IST 2020")),
      CustomTweet(1235341329737166849L,0,0,formatter.parse("Thu Mar 05 04:38:19 IST 2020")),
      CustomTweet(1235210893056606208L,3,0,formatter.parse("Wed Mar 04 20:00:00 IST 2020")),
      CustomTweet(1235179431779610629L,0,4,formatter.parse("Wed Mar 04 17:54:59 IST 2020")),
      CustomTweet(1235171025446461442L,0,4,formatter.parse("Wed Mar 04 17:21:35 IST 2020")),
      CustomTweet(1235170090166784000L,10,4,formatter.parse("Wed Mar 04 17:17:52 IST 2020")),
      CustomTweet(1235160055256760320L,0,0,formatter.parse("Wed Mar 04 16:37:59 IST 2020")),
      CustomTweet(1235101419889254400L,6,0,formatter.parse("Wed Mar 04 12:45:00 IST 2020")),
      CustomTweet(1234978849751846914L,0,0,formatter.parse("Wed Mar 04 04:37:57 IST 2020")),
      CustomTweet(1234831748204199938L,0,0,formatter.parse("Tue Mar 03 18:53:25 IST 2020")),
      CustomTweet(1234805403013275651L,0,0,formatter.parse("Tue Mar 03 17:08:44 IST 2020")),
      CustomTweet(1234676978294247425L,0,0,formatter.parse("Tue Mar 03 08:38:25 IST 2020")),
      CustomTweet(1234435278011416576L,0,0,formatter.parse("Mon Mar 02 16:37:59 IST 2020")),
      CustomTweet(1234254075346092032L,0,0,formatter.parse("Mon Mar 02 04:37:57 IST 2020")))
  }

  def getExpectedActualReTweetPerTweetList : List[ReTweet] = {
    List(ReTweet(1235703638187577349L,0),
      ReTweet(1235691216336904192L,0),
      ReTweet(1235341329737166849L,0),
      ReTweet(1235210893056606208L,0),
      ReTweet(1235179431779610629L,4),
      ReTweet(1235171025446461442L,4),
      ReTweet(1235170090166784000L,4),
      ReTweet(1235160055256760320L,0),
      ReTweet(1235101419889254400L,0),
      ReTweet(1234978849751846914L,0),
      ReTweet(1234831748204199938L,0),
      ReTweet(1234805403013275651L,0),
      ReTweet(1234676978294247425L,0),
      ReTweet(1234435278011416576L,0),
      ReTweet(1234254075346092032L,0))
  }

}
