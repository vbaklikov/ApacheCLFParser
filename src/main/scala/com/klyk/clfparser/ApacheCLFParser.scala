package com.klyk.clfparser

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.Pattern
import scala.util.control.Exception._

/**
 * Created by klyk on 7/10/15.
 */
@SerialVersionUID(100L)
class ApacheCLFParser extends Serializable{

  private val ddd = "\\d{1,3}"                      // at least 1 but not more than 3 times (possessive)
  private val ip = s"($ddd\\.$ddd\\.$ddd\\.$ddd)?"  // like `123.456.7.89`
  private val client = "(\\S+)"                     // '\S' is 'non-whitespace character'
  private val user = "(\\S+)"
  private val dateTime = "(\\[.+?\\])"              // like `[21/Jul/2009:02:48:13 -0700]`
  private val request = "\"(.*?)\""                 // any number of any character, reluctant
  private val status = "(\\d{3})"
  private val bytes = "(\\S+)"                      // this can be a "-"
  private val referer = "\"(.*?)\""
  private val agent = "\"(.*?)\""
  private val regex = s"$ip $client $user $dateTime $request $status $bytes $referer $agent"

  private val p = Pattern.compile(regex)

  def parseRecord(record:String): Option[ApacheCLFRecord] = {
    val matcher = p.matcher(record)
    if (matcher.find){
      Some(ApacheCLFRecord(
        matcher.group(1),
        matcher.group(2),
        matcher.group(3),
        matcher.group(4),
        matcher.group(5),
        matcher.group(6),
        matcher.group(7),
        matcher.group(8),
        matcher.group(9)))
    }
    else {
      None
    }
  }

}

object ApacheCLFParser{

  def parseDateTimeField(field:String): Option[java.util.Date] ={
    val dateRegex = "\\[(.*?) .+]"
    val p = Pattern.compile(dateRegex)
    val m = p.matcher(field)
    if(m.find){
      val dateString = m.group(1)
      val dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH)
      allCatch.opt(dateFormat.parse(dateString))
    }
    else{
      None
    }

  }
}
