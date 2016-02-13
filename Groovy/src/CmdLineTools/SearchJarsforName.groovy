package CmdLineTools
import java.util.jar.*

		
		
			def searchInputFile(text, inputFile){
				def filePattern = ~/.*\.(jar|zip)$/
				if(inputFile.isDirectory()){
					inputFile.eachFileRecurse{
						if(!it.isDirectory() && it.getName() =~ filePattern)
							searchCompressedFile(text, it)
					}
				}else{
					if(inputFile.getName() =~ filePattern){
						searchCompressedFile(text, inputFile)
					}
				}
			}
		
			def searchCompressedFile(text, file){
			//	println "Trying file: " + file
				try{
					new JarFile(file).entries().each{ entry ->
						if ( entry.name =~ text){
							println "$entry.name : $file.canonicalPath"
						}
					}
				}catch(Throwable t){
					println "\nFailed to open $file.canonicalPath: ${t.toString()}"
				}
			}
		
		/**
		 * Searches entries (file name and directory, if any) in one or more named
		 * compressed (jar/zip) files, or in all compressed files in a named directory.
		 * The search string is expected to be a regular expression (ex: Thv.*\.class$)
		 * To specify a search that includes special characters, like a period, use
		 * a backslash: \.xml
		 */
			if( args.size() < 2){
				println "Required parameters: searchString filePath [filePath]\n"
				println "NOTE: filePath may be a directory, which will search all jar/zip"
				println "      files in all subdirectories"
				return
			}
			def searchstr = args[0]
			println "Searching jars and zips for " + searchstr
			args[1..-1].each{searchInputFile(searchstr, new File(it))}
