package com.ikempf

import java.util.UUID

import com.ikempf.User.{CreatedUser, UserCreateRequest}

class UserService(repository: UserRepository) {

  def list: List[CreatedUser] =
    repository.list

  def delete(userId: UserId): Unit =
    repository.delete(userId)

  def create(user: UserCreateRequest): Unit = {
    val id = UUID.randomUUID().toString
    val userToCreate = user.copy(id = UserId(id))
    repository.create(userToCreate)
  }

}
