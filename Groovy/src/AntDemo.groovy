def ant = new AntBuilder()
ant.echo(message:"mapping it via attribute!")		 
ant.echo("Hello World!")

//ant = new AntBuilder()
final dirBuildRoot = "c:/dev/projects/groovy"
ant.mkdir(dir:dirBuildRoot + "/binaries")

ant.javac(srcdir:dirBuildRoot + "/src", 
	destdir:dirBuildRoot + "/binaries" )

// (ThV) what is wrong with this? ( I guess this is Gant code )	
ant.target(compile:"compiles source code"){
 ant.mkdir(dir:"target/classes")
 ant.javac(srcdir:"src", destdir:"target/classes")
 ant.echo("Compile done!")
}


