package swing




class TwitterData {
	String content
	String published
	String author
		
	String toString(){
		//return "${author}: ${content}"
		
		return """<html>
			         <body>
			           <p><b><i>${author}:</i></b></p>
			           <p>${content}</p>
			         </body>
			       </html>"""
	}
}
