/**
 * Created by Simon Markham  on 02/10/2015.
 * Student ID:  12307233
 *
 * My project can be found on github here:
 *
 * Based off stack overflow post
 * http://stackoverflow.com/questions/6414942/scala-equivalent-of-python-echo-server-client-example
 * I used the setting up the connection from there as I wasn't sure how to use Scala
 * to implement socket programming
 *
 * I found out how to trim the trailing whitespace here:
 * http://stackoverflow.com/questions/6724537/how-to-trim-ending-blanks-of-a-string
 */
import java.io._
import java.net.{Socket}
import scala.io._

object EchoClient {
  // various member data to easily change
  var messageToSend = ""
  var message = ""
  var request = ""
  val host = "localhost"
  val port = 8000
  var path = "/echo.php?message="
  var get = "GET "
  var http = "\r\nHTTP/1.0\r\n"

  // Main method that runs the program
  def main(args: Array[String]) {
    message = readLine("Please enter the text to convert: ")
    // This removes any trailing spaces and converts the ' ' to '+'
    // so that php knows they're spaces and not malformed
    messageToSend = message.replaceAll("\\s+$", "").replace(' ','+')
    request = get+path+messageToSend+http
    println("Request sent: "+request)
    // Generic socket code stuff
    val socket = new Socket(host, port)
    val outputStream = socket.getOutputStream()
    lazy val in = new BufferedSource(socket.getInputStream()).getLines()
    val out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8")))
    out.println(request)
    out.flush()// send the request to the server

    var recv = in.next()
    var notConverted = true
    //Loop forever, while the message hasn't been converted.
    while(notConverted){
      println(recv)
      // A check to see if the input isn't Empty
      if(in.hasNext) recv = in.next()
      // Otherwise tell it to stop
      else notConverted = false
    }
    println("Received: "+recv)
    socket.close()
    println("Socket Closed!")
  }

}


