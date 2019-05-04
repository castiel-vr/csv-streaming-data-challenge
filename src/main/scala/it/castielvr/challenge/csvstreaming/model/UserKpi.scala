package it.castielvr.challenge.csvstreaming.model

object UserKpi{
  def apply(userData: UserData): UserKpi = {
    new UserKpi(userData.third, 1, userData.fourth, userData.fifth)
  }
}

class UserKpi(
               private var thirdSum: Float,
               private var thirdCount: Int,
               var latestFourth: Long,
               var fifthSum: BigInt){

  def combineWith(other: UserKpi): Unit = {
    this.thirdSum += other.thirdSum
    this.thirdCount += other.thirdCount
    this.latestFourth = other.latestFourth
    this.fifthSum += other.fifthSum
  }

  def thirdAvg: Float = thirdSum / thirdCount

  // Needed for testing purposes
  override def equals(obj: Any) = obj match {
    case x: UserKpi =>
      (this.thirdSum - x.thirdSum).abs < 0.0001 && (this.thirdCount == x.thirdCount) &&
        this.latestFourth == x.latestFourth && this.fifthSum == x.fifthSum
    case _ => false
  }
}
