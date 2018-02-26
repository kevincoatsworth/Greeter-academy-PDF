package app

import scala.io.StdIn

object Prompt {
  def ask(message : String) = StdIn.readLine(message)
}

abstract class BankAccount(accountNumber : String, val balance : Double) {
  def withdraw(amount : Double) : BankAccount
  def deposit(amount : Double) : BankAccount
}

final class SavingsAccount(accountNumber : String, balance : Double) extends BankAccount(accountNumber, balance) {
  override def withdraw(amount : Double) : BankAccount = {
    if((balance - amount) < 0) {

      println(s"You have insufficient funds")
      this
    } else {

      val deducted = balance - amount
      println(s"Balance after deductions: $deducted")
      new SavingsAccount(accountNumber, deducted)
    }
  }
  override def deposit(amount : Double) : BankAccount = {
    new SavingsAccount(accountNumber, balance + amount)
  }

}

final class CashISASavingsAccount(accountNumber : String, balance : Double) extends BankAccount(accountNumber, balance) {

  override def withdraw(amount: Double): BankAccount = {

    println(s"You can't withdraw yet, your money is locked in for 3 years!!!")
    this
  }

  override def deposit(amount: Double): BankAccount = {

    new CashISASavingsAccount(accountNumber, balance + amount)

  }

}

class Person(name : String, age : Int, private val bankAccount: BankAccount) {

  def this(name: String, age : Int) = this(name, age, new SavingsAccount("12345", 0.00))
  private val years : String = if(age > 1) "years" else "year"
  def speak() : String = {
    if(name == "adam") {
      s"You don't get a hello!"
    } else {
      s"Hello $name, you are $age $years old. \nYou have ${bankAccount.balance} in your account."
    }
  }
}

object GreeterApplication extends App {
  val name = Prompt.ask("What is your name? ")
  val age = Prompt.ask("How old are you? ")

  val person = new Person(name, age.toInt)
  println(person.speak())

}
