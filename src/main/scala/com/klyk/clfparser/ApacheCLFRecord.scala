package com.klyk.clfparser

/**
 * Created by klyk on 7/10/15.
 */
case class ApacheCLFRecord (
  clientIpAddress: String,         // should be an ip address, but may also be the hostname if hostname-lookups are enabled
  rfc1413ClientIdentity: String,   // typically `-`
  remoteUser: String,              // typically `-`
  dateTime: String,                // [day/month/year:hour:minute:second zone]
  request: String,                 // `GET /foo ...`
  httpStatusCode: String,          // 200, 404, etc.
  bytesSent: String,               // may be `-`
  referer: String,                 // where the visitor came from
  userAgent: String                // long string to represent the browser and OS

                             )
