package utilities

import com.knoldus.JsonParsing.{JsonParsingUsers, User}
import utilities.traits.UserTrait

class UserUtilities extends JsonParsingUsers with UserTrait {

  val users=getAllUsers

  override def getUserById(id : String) : User ={
    val res=for{
      user<-users
      if user.id == id
        res=user
    }yield res
    res.head
  }

  override def getUserByUsername(username: String): User = {
    val res=for{
      user<-users
      if user.username == username
      res=user
    }yield res
    res.head
  }
}
object UserUtilities{
  val userUtilities=new UserUtilities
}
