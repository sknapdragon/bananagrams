package type1;


	import java.io.*;
	import java.net.*;
	public class NetworkClient extends Thread
	{
		private int target;
		
	  public void run()
	  {

	     try {
			Socket sock = new Socket("10.0.1.6", 8080);
			                           // reading from keyboard (keyRead object)
			 BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
			                          // sending to client (pwrite object)
			 OutputStream ostream = sock.getOutputStream(); 
			 PrintWriter pwrite = new PrintWriter(ostream, true);

			                          // receiving from server ( receiveRead  object)
			 InputStream istream = sock.getInputStream();
			 BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

			 String receiveMessage, sendMessage;               
			  
			 while(true)
			 {
			    sendMessage = keyRead.readLine();  // keyboard reading
			    pwrite.println(sendMessage);       // sending to server
			    pwrite.flush();                    // flush the data
			    if((receiveMessage = receiveRead.readLine()) != null) //receive from server
			    {
			    	try {
			    		//takes the recieved data as a target and then accesses what to do
			        target = Integer.parseInt(receiveMessage);
			    	}catch ( NumberFormatException ne) {
			    		ne.printStackTrace();
			    	}
			    }         
			  }
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}               
	    }                    
	}                        


