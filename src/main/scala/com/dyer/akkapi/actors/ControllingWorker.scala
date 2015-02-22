package com.dyer.akkapi.actors

import akka.actor._
import akka.routing.RoundRobinPool

import com.dyer.akkapi.domain.{Calculation, Result, PiApproximation, Work}

import scala.concurrent.duration._

class ControllingWorker(nrOfWorkers: Int, nrOfMessages: Int, nrOfElements: Int, listener: ActorRef) extends Actor {

  val workerRouter = context.actorOf(
    Props[CalculatingWorker].withRouter(RoundRobinPool(nrOfWorkers)), name = "workerRouter")
  val start: Long = System.currentTimeMillis
  var nrOfResults: Int = _
  var pi: Double = _

  def receive = {
    case Calculation ⇒
      for (i ← 0 until nrOfMessages) workerRouter ! Work(i * nrOfElements, nrOfElements)
    case Result(value) ⇒
      pi += value; nrOfResults += 1
      if (nrOfResults == nrOfMessages) {
        listener ! PiApproximation(pi, duration = (System.currentTimeMillis - start).millis )
        context.stop(self)
      }
  }

}
