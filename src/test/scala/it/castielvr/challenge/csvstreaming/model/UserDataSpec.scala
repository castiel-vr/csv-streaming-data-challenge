package it.castielvr.challenge.csvstreaming.model

import org.scalatest._
import Matchers._
import it.castielvr.challenge.csvstreaming.exceptions.MalformedUserDataCsv

class UserDataSpec extends FlatSpec{
  "UserData.fromCsv utility" should "create UserData instances from well formatted CSV lines" in {

    val input1 = "id1,encoded1,0.111,123123,111199991991991991991"
    val expectedUd1 = UserData("id1", "encoded1",0.111f, 123123, BigInt("111199991991991991991"))
    val input2 = "id2,encoded2,0.121,123123,111199991991991991992221"
    val expectedUd2 = UserData("id2", "encoded2",0.121f, 123123, BigInt("111199991991991991992221"))
    val input3 = "id3,encoded3,0.131,0,11119999199199199199111111111111111111111"
    val expectedUd3 = UserData("id3", "encoded3",0.131f, 0, BigInt("11119999199199199199111111111111111111111"))
    val input4 = "id4,encoded4,0.1411111223232323323231,123123,11"
    val expectedUd4 = UserData("id4", "encoded4",0.1411111223232323323231f, 123123, BigInt("11"))
    val input5 = "id5,encoded5,0.151,123123,111199991991991991991111199991991991991991111199991991991991991111199991991991991991111199991991991991991"
    val expectedUd5 = UserData("id5", "encoded5",0.151f, 123123, BigInt("111199991991991991991111199991991991991991111199991991991991991111199991991991991991111199991991991991991"))
    val input6 = "id6,encoded6,0.161111111211111121,123123,111199991991991991991"
    val expectedUd6 = UserData("id6", "encoded6",0.161111111211111121f, 123123, BigInt("111199991991991991991"))

    val output1 = UserData.fromCsv(input1)
    val output2 = UserData.fromCsv(input2)
    val output3 = UserData.fromCsv(input3)
    val output4 = UserData.fromCsv(input4)
    val output5 = UserData.fromCsv(input5)
    val output6 = UserData.fromCsv(input6)

    assert(output1 == expectedUd1)
    assert(output2 == expectedUd2)
    assert(output3 == expectedUd3)
    assert(output4 == expectedUd4)
    assert(output5 == expectedUd5)
    assert(output6 == expectedUd6)
  }

  "UserData.fromCsv utility" should "throw MalformedUserDataCsv exceptions from bad formatted CSV lines" in {
    val input1 = "id1,encoded1,0.111aa,123123,111199991991991991991"
    val input2 = "id2,encoded2,0.121,1231"
    val input3 = "id3,encoded3,0.131,0,111199991991a99199199111111111111111111111"
    val input4 = "id4;encoded4;0.1411111223232323323231;123123;11"

    assertThrows[MalformedUserDataCsv](UserData.fromCsv(input1))
    assertThrows[MalformedUserDataCsv](UserData.fromCsv(input2))
    assertThrows[MalformedUserDataCsv](UserData.fromCsv(input3))
    assertThrows[MalformedUserDataCsv](UserData.fromCsv(input4))
  }
}
