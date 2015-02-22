package com.dyer.akkapi.actors

import akka.actor.Actor
import com.dyer.akkapi.domain.PiApproximation

class ResultListener extends Actor {

  def receive = {
    case PiApproximation(pi, duration) ⇒
      println("\n\tPi approximation: \t\t%s\n\tCalculation time: \t%s"
        .format(pi, duration))
      context.system.shutdown()
  }

}
