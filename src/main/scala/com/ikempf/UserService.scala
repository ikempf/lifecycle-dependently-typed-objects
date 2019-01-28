package com.ikempf

import java.util.UUID

import com.ikempf.User.{CreatedUser, UserCreateRequest}
import com.ikempf.UserRepository.hash

class UserService(repository: UserRepository) {

  def list: List[CreatedUser] =
    repository.list

  def delete(userId: UserId): Unit =
    repository.delete(userId)

  def create(user: UserCreateRequest): CreatedUser = {
    val id           = UUID.randomUUID().toString
    val userToCreate = user.copy(id = UserId(id))
    repository.create(userToCreate)
  }

  def auth(login: String, pass: Clear): Boolean =
    repository
      .find(login)
      .exists(created => created.password == hash(pass))
//      .exists(created => created.password == pass)

}
