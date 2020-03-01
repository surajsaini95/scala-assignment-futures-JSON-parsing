package com.knoldus.jsonParsing.controller

import org.scalatest.AsyncFlatSpec

import scala.io.Source

class DataParserSpec extends AsyncFlatSpec {


  behavior of "postParser"
  it should "eventually fetch the json data  of all the posts from the specified json string " in {
    val jsonDataString = Source.fromFile("./src/test/resources/testJsonDataPosts").getLines.mkString
    val actualPostsList = DataParser.postParser(jsonDataString)
    val expectedFirstPostId = "1"
    assert(actualPostsList.head.id == expectedFirstPostId)
  }
  it should "eventually return empty list of all the posts as the specified json string is incorrect" in {

    val jsonDataString = Source.fromFile("./src/test/resources/testJsonDataPosts").getLines.mkString
    val actualPostsList = DataParser.postParser(jsonDataString.replaceAll(",","#"))
    assert(actualPostsList.isEmpty)
  }

  behavior of "userParser"
  it should "eventually fetch the json data  of all the users from the specified json string " in {
    val jsonDataString = Source.fromFile("./src/test/resources/testJsonDataUsers").getLines.mkString
    val actualUsersList = DataParser.userParser(jsonDataString)
    val expectedFirstUserId = "1"
    assert(actualUsersList.head.id == expectedFirstUserId)
  }
  it should "eventually return empty list of all the users as the specified json string is incorrect" in {

    val jsonDataString = Source.fromFile("./src/test/resources/testJsonDataUsers").getLines.mkString
    val actualUsersList = DataParser.userParser(jsonDataString.replaceAll(",","#"))
    assert(actualUsersList.isEmpty)
  }

  behavior of "commentParser"
  it should "eventually fetch the json data  of all the comments from the specified json string " in {
    val jsonDataString = Source.fromFile("./src/test/resources/testJsonDataComments").getLines.mkString
    val actualCommentsList = DataParser.commentParser(jsonDataString)
    val expectedFirstCommentId = "1"
    assert(actualCommentsList.head.id == expectedFirstCommentId)
  }
  it should "eventually return empty list of all the comments as the specified json string is incorrect" in {

    val jsonDataString = Source.fromFile("./src/test/resources/testJsonDataComments").getLines.mkString
    val actualCommentsList = DataParser.commentParser(jsonDataString.replaceAll(",","#"))
    assert(actualCommentsList.isEmpty)
  }

}
