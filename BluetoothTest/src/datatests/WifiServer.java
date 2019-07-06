package datatests;

import java.io.*;
import java.net.*;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.HiTechnicColorSensor;
import lejos.hardware.sensor.HiTechnicCompass;
import lejos.hardware.sensor.HiTechnicIRSeekerV2;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
public class WifiServer
{
  public static void main(String[] args) throws Exception
  {
		Port s1 = LocalEV3.get().getPort("S1");
		Port s2 = LocalEV3.get().getPort("S2");
		Port s3 = LocalEV3.get().getPort("S3");
		Port s4 = LocalEV3.get().getPort("S4");

		SensorModes compassSensor = new HiTechnicCompass(s1);
		SensorModes colorSensor = new HiTechnicColorSensor(s2);
		SensorModes irSeeker = new HiTechnicIRSeekerV2(s3);
		SampleProvider irAngProv = irSeeker.getMode("Modulated");
		float [] irfloats = new float [irAngProv.sampleSize()];
		
      ServerSocket sersock = new ServerSocket(8080);
      System.out.println("Server  ready for chatting");
      Socket sock = sersock.accept( );                          
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
      	irAngProv.fetchSample(irfloats, 0);  
        if((receiveMessage = receiveRead.readLine()) != null)  
        {

           System.out.println(irfloats[0]);         
        }         
        sendMessage = String.valueOf(irfloats[0]);
        pwrite.println(sendMessage);             
        pwrite.flush();
      }               
    }                    
}                        