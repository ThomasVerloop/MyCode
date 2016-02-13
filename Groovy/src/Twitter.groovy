
if(args){
 def username = args[0]
 def addr = "http://search.twitter.com/search.atom?q=${username}"
 def feed = new XmlSlurper().parse(addr)
 feed.entry.each{
   println it.author.name
   println it.published
   println it.title
   println "-"*20
 }  
}else{
 println "USAGE: groovy searchCli <query>"
}
