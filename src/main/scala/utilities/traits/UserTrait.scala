package utilities.traits

import com.knoldus.JsonParsing.User

trait UserTrait {
  def getUserById(id : String) : User
  def getUserByUsername(username : String) : User

}
