package com.dyer.akkapi.actors

import akka.actor.{ActorLogging, Actor}
import com.dyer.akkapi.domain.{Result, Work}

class CalculatingWorker extends Actor with ActorLogging {

  def calculatePiFor(start: Int, elements: Int): Double = {
    var acc = 0.0
    for (i ← start until (start + elements))
      acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)
    acc
  }

  def receive = {
    case Work(start, elements) =>
      sender ! Result(calculatePiFor(start, elements))
  }

}