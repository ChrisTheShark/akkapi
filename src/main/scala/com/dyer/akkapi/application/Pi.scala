package com.dyer.akkapi.application

import akka.actor._
import com.dyer.akkapi.actors.{ControllingWorker, ResultListener}
import com.dyer.akkapi.domain.Calculation

object Pi extends App {

  calculate(nrOfWorkers = 4, nrOfElements = 10000, nrOfMessages = 10000)

  def calculate(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int) {
    val system = ActorSystem("PiSystem")
    val listener = system.actorOf(Props[ResultListener], name = "listener")
    val master = system.actorOf(Props(new ControllingWorker(
      nrOfWorkers, nrOfMessages, nrOfElements, listener)),
      name = "master")
    master ! Calculation
  }

}