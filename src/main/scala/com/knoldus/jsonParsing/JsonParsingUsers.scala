package com.knoldus.jsonParsing

import com.knoldus.jsonParsing.traits.ReadJsonData
import net.liftweb.json.DefaultFormats
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.implicitConversions

/**
 * This is a case class used to model Geographical Location of a user
 * @param latitude contains latitude details
 * @param longitude contains longitude details
 */
case class GeographicalLocation(latitude : String, longitude: String)

/**
 * This is a case class used to model Address of a user
 * @param street contains street details
 * @param suite contains suite details
 * @param city contains city details
 * @param zipcode contains zipcode details
 * @param geographicalLocation contains geographical location details
 */
case class Address(street: String, suite: String, city: String, zipcode: String, geographicalLocation: GeographicalLocation)

/**
 * This is a case class used to model Company details of a user
 * @param name contains name of the company
 * @param catchPhrase contains catching phrase of the company
 * @param bs contains contains bs of the company
 */
case class Company(name: String, catchPhrase: String, bs: String)

/**
 * This is a case class used to model personal details of a user
 * @param id contains unique id of the user
 * @param name contains name of the user
 * @param username contains username of the user
 * @param email contains email of  the user
 * @param address contains address of  the user
 * @param phone contains phone number of the user
 * @param website contains website of the user
 * @param company contains company details of the user
 */
case class User(id: String, name: String, username: String, email: String, address: Address, phone: String, website: String, company: Company)


object JsonParsingUsers extends ReadJsonData {

  /**
   *  method getAllUsers is used to fetch the json data  of all the Users from the specified url.
   * @return the list of model class Users wrapped in Future
   */
  def getAllUsers : Future[List[User]] = {
    val url = "https://jsonplaceholder.typicode.com/users"

    val jsonData: Future[String] = Future {
      readData(url)
    }
    val allUsersFallback : Future[List[User]] = Future{ List.empty[User] }

    val parsedJsonData: Future[List[User]] = for {
      jsonUserData <- jsonData
      parsedJsonData <- Future(parse(jsonUserData))
    } yield parsedJsonData

    val allUsers = parsedJsonData fallbackTo allUsersFallback

    allUsers

  }

  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * method parse is used to parse the data into model class User
   * @param jsonData it accepts a string of json data
   * @return the list of model class User
   */
  def parse(jsonData: String): List[User] = {
  val parsedJsonData = net.liftweb.json.parse(jsonData)

  parsedJsonData.children map { user =>

      user.extract[User]
  }
  }
}
