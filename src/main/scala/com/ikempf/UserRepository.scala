package com.ikempf

import java.time.Instant
import java.util.UUID

import com.ikempf.User.{CreatedUser, UserToCreate}
import com.ikempf.UserRepository.hash

object UserRepository {

  private def hash(password: Clear): Hashed = {
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

  def create(user: UserToCreate): Unit = {
    val ready = user.copy(password = hash(user.password), creation = Since(Instant.now))
    users += ready.id -> ready
  }

  def delete(userId: UserId): Unit =
    users -= userId

  def list: List[CreatedUser] =
    users.values.toList

}
