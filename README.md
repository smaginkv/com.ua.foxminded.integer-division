#Task 3 - Integer division

To package the project, you have to open a command line, go to the project folder and execute the command:
    
    mvn package
    
After the conversation, you have to execute uber jar with the command:    
    
    java -jar uberjar\integer-division.jar 1564 23

![Classic](src\main\resources\docs\classic.png "program output")

<small>Integer division in soviet notation<p><br></small>

If you want to output a result in json format, you can use the "-j" option

    java -jar uberjar\integer-division.jar 1564 23 -j
    
![Classic](src\main\resources\docs\json.png "program output")

<small>Output in JSON format<p><br></small>

If you are interested in HTML file, you can use the "-h" option with file name

    java -jar uberjar\integer-division.jar 1564 23 -h > index.html

 ![Classic](src\main\resources\docs\html.png "program output")
 
<small>Output british notation in HTML format<p><br></small>
  
For XML file use "-x"

    java -jar uberjar\integer-division.jar 1564 23 -x > example.xml

![Classic](src\main\resources\docs\xml.png "program output")

<small>Output in XML format</small>
