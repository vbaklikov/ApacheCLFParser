package com.klyk.clfparser

import java.util.Calendar

import org.scalatest.{FlatSpec, GivenWhenThen}

/**
 * Created by klyk on 7/11/15.
 */
class ApacheCLFParserTest extends FlatSpec with GivenWhenThen{

  "A good Apache Common Log Format line" should "parse correctly when passed to parser" in{
    Given("a good Apache CLF record")
    val goodRecord = "124.30.9.161 - - [21/Jul/2009:02:48:11 -0700] \"GET /java/edu/pj/pj010004/pj010004.shtml HTTP/1.1\" 200 16731 \"http://www.google.co.in/search?hl=en&client=firefox-a&rlz=1R1GGGL_en___IN337&hs=F0W&q=reading+data+from+file+in+java&btnG=Search&meta=&aq=0&oq=reading+data+\" \"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 GTB5\""

    When("passed to parser")
    val parser = new ApacheCLFParser
    val record = parser.parseRecord(goodRecord)

    Then("record should not be None")
    assert(record != None)

    And("ip should be correct")
    assert(record.get.clientIpAddress == "124.30.9.161")

    And("client should be correct")
    assert(record.get.rfc1413ClientIdentity == "-")

    And("remote user")
    assert(record.get.remoteUser == "-")

    And("date/time")
    assert(record.get.dateTime == "[21/Jul/2009:02:48:11 -0700]")

    And("date should be a correct date")
    val dateF = ApacheCLFParser.parseDateTimeField(record.get.dateTime)
    assert(dateF != None)
    dateF.foreach { d =>
      val cal = Calendar.getInstance()
      cal.setTimeInMillis(d.getTime)
      assert(cal.get(Calendar.YEAR) == 2009)
      assert(cal.get(Calendar.MONTH) == 6)  // 0-based
      assert(cal.get(Calendar.DAY_OF_MONTH) == 21)
      assert(cal.get(Calendar.HOUR) == 2)
      assert(cal.get(Calendar.MINUTE) == 48)
      assert(cal.get(Calendar.SECOND) == 11)
    }

    And("request")
    assert(record.get.request == "GET /java/edu/pj/pj010004/pj010004.shtml HTTP/1.1")

    And("status code should be 200")
    assert(record.get.httpStatusCode == "200")

    And("bytes sent should be 16731")
    assert(record.get.bytesSent == "16731")

    And("referer")
    assert(record.get.referer == "http://www.google.co.in/search?hl=en&client=firefox-a&rlz=1R1GGGL_en___IN337&hs=F0W&q=reading+data+from+file+in+java&btnG=Search&meta=&aq=0&oq=reading+data+")

    And("user agent")
    assert(record.get.userAgent == "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 GTB5")

    markup("Good Record test finished!")
  }

  "A bad Apache CLF line" should "return None" in {
    Given("a bad Apache CLF record")
    val badRecord = ""

    When("passed to parser")
    val parser = new ApacheCLFParser
    val record = parser.parseRecord(badRecord)

    Then("record should be None")
    assert(record == None)

    markup("Bad Record test passed!")

  }



}
