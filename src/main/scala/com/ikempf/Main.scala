package com.ikempf

import java.time.Instant

import com.ikempf.User.{CreatedUser, UserCreateRequest, UserToCreate}

object Main extends App {

  val repository = new UserRepository
  val service    = new UserService(repository)

  val createRequest: UserCreateRequest = User(login = "login", password = Clear("pass"))
  val readyToCreate: UserToCreate      = User(id = UserId("id"), login = "login", password = Clear("pass"))
  val created: CreatedUser             = User(id = UserId("id"), login = "login", password = Hashed("aec123", "salt"), creation = Since(Instant.now))

  service.create(createRequest)
  repository.create(readyToCreate)
  service.delete(created.id)

}
