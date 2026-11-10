import java.io.BufferedReader;

interface IFile {

public BufferedReader openFile(String fileName); 
// Uses the fileName and opens a file and returns a BufferedReader object

// The class that implements this must keep track of this BufferedReader too
/* The class implementing this must use a TreeMap Value (BufferedReader object) &
Key (file name) to keep track of the files/buffered reader pairs
*/

public int closeFile(BufferedReader br);    
// The buffered reader must be closed when done with (should first check the br if it is a match)
// returns 0 if okay, returns -1 if br is not matching (immediately returns -1 if not found)
// // the K,V pair needs to be removed if 0 is going to be returned

public String getFileName(BufferedReader br);  
// gets the file name based on the buffered reader

 

/* A TreeMap of BufferedReader (Value), and file name (Key) should be maintained in the class that implements this interface */

}