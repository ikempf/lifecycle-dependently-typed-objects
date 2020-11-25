package com.ikempf

import java.time.Instant

import com.ikempf.Complex.Animal.{ExistingAnimal, SavedAnimal}
import com.ikempf.Complex.Person.SavedPerson

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

case class User[Id <: UserIdentification, Pass <: UserAuthentication, Creation <: UserCreation](
    id: Id,
    login: String,
    password: Pass,
    creation: Creation
)

object User {

  type UserCreateRequest = User[NoId.type, Clear, NoDate.type]
  type UserToCreate      = User[UserId, Clear, NoDate.type]
  type CreatedUser       = User[UserId, Hashed, Since]

  def apply(login: String, password: Clear): UserCreateRequest =
    new User(NoId, login, password, NoDate)

  def apply(id: UserId, login: String, password: Clear): UserToCreate =
    new User(id, login, password, NoDate)

}

object Complex {
  case class Animal[Id <: Option[String], Update <: Option[Instant]](
      id: Id,
      name: String,
      lastUpdate: Update
  ) {
    def withId(id: String)(implicit a: Id =:= None.type, b: Update =:= None.type): ExistingAnimal =
      copy(id = Some(id))
    def withLastUpdate(lastUpdate: Instant)(implicit a: Id =:= Some[String], b: Update =:= None.type): SavedAnimal =
      copy(lastUpdate = Some(lastUpdate))
  }

  object Animal {
    type AnimalCreateRequest = Animal[None.type, None.type]
    type ExistingAnimal      = Animal[Some[String], None.type]
    type SavedAnimal         = Animal[Some[String], Some[Instant]]
  }

  case class Person[Saved[_] <: Option[_]](
      id: Saved[String],
      name: String,
      uploadDate: Saved[Instant]
  ) {
    def saved(id: String, uploadDate: Instant)(implicit saved: Saved[_] =:= None.type): SavedPerson =
      copy(id = Some(id), uploadDate = Some(uploadDate))
  }

  object Person {
    type UnsavedPerson = Person[Lambda[A => None.type]]
    type SavedPerson   = Person[Some]
  }
}
