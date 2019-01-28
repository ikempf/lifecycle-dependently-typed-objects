package com.ikempf

import java.time.Instant

sealed trait UserIdentification
case object NoId                 extends UserIdentification
case class UserId(value: String) extends UserIdentification

sealed trait UserAuthentication
case object NoPassword                         extends UserAuthentication
case class Clear(value: String)                extends UserAuthentication
case class Hashed(value: String, salt: String) extends UserAuthentication

sealed trait UserCreation
case object NoDate               extends UserCreation
case class Since(value: Instant) extends UserCreation

case class User[Id <: UserIdentification, Pass <: UserAuthentication, Creation <: UserCreation]
               (id: Id, login: String, password: Pass, creation: Creation)


object User {

  type UserCreateRequest = User[NoId.type, Clear, NoDate.type]
  type UserToCreate = User[UserId, Clear, NoDate.type]
  type CreatedUser = User[UserId, Hashed, Since]

  def apply(login: String, password: Clear): UserCreateRequest =
    new User(NoId, login, password, NoDate)

  def apply(id: UserId, login: String, password: Clear): UserToCreate =
    new User(id, login, password, NoDate)

}