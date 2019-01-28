package com.ikempf

import java.time.Instant
import java.util.UUID

import com.ikempf.User.{CreatedUser, UserToCreate}
import com.ikempf.UserRepository.hash

object UserRepository {

  def hash(password: Clear): Hashed = {
    val salt = UUID.randomUUID().toString
    val hash = password.value + salt // Super hasing

    Hashed(
      value = hash,
      salt = salt
    )
  }

}

class UserRepository {

  var users = Map.empty[UserId, CreatedUser]

  def create(user: UserToCreate): CreatedUser = {
    val ready = user.copy(password = hash(user.password), creation = Since(Instant.now))
    users += ready.id -> ready
    ready
  }

  def delete(userId: UserId): Unit =
    users -= userId

  def list: List[CreatedUser] =
    users.values.toList

  def find(login: String): Option[CreatedUser] =
    users.values.find(_.login == login)

}
