import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

import java.awt.*;
import java.awt.event.*;

public class DEScbcEvent implements ActionListener
{
	DEScbcGUI gui;		
	
	public DEScbcEvent(DEScbcGUI in)
	{
		gui = in;
	}

	public void actionPerformed(ActionEvent event)
	{
		String command = event.getActionCommand();
		if(command == "Encrypt")
			encrypt();
		if(command == "Decrypt")
			decrypt();
	}

	
	private void encrypt()
	{
		String strKey = new String(gui.txtKey.getText());
		String strPText = new String(gui.txtPlainText.getText());
		String strICV = new String(gui.txtICV.getText());
		try
		{
			if(strKey.length() != 16)//check to see if its 8 bytes
			{
				throw new Exception("DEScbc Key must be 8 bytes");	
			}
			if(strICV.length() != 16)//check to see if its 8 bytes
			{
				throw new Exception("DEScbc ICV must be 8 bytes");	
			}
			
			byte[] bytKey = new byte[8];
			//put key into byte array from string
			bytKey = cvHexStrToByteArr(strKey);

			byte[]bytICV = new byte[8];
			bytICV = cvHexStrToByteArr(strICV);
			IvParameterSpec icvParam = new IvParameterSpec(bytICV);
			
			//define key as type "DES" and set value
			SecretKeySpec desKey = new SecretKeySpec(bytKey,"DES");
			//define cipher instance as type "DES/CBC/PKCS5Padding"
		        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

			//init cipher in encryption mode and set key  
        		cipher.init(Cipher.ENCRYPT_MODE,desKey,icvParam);

			byte[] plainText = new byte[strPText.length()/2];
			//put plaintext into byte array
			plainText = cvHexStrToByteArr(strPText);
			byte[] encrypted = new byte[strPText.length()/2];
			//call cipher.doFinal with plainText
			//will return byte []

        		encrypted = cipher.doFinal(plainText);
			//set encrypted text to Graphical User Interface
			String strCText = new String(cvByteArrToString(encrypted));
			
			//System.out.println(strCText);
			if (strPText.length() % 8 == 0)
			 	gui.txtCipherText.setText(cvByteArrToString(encrypted).substring(0,strPText.length()));
			else
			{
				System.out.println("Your Plain Text Was Paded");
				gui.txtCipherText.setText(cvByteArrToString(encrypted));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	private void decrypt()
	{
		String strKey = new String(gui.txtKey.getText());
		String strCText = new String(gui.txtCipherText.getText());
		String strICV = new String(gui.txtICV.getText());

		try
		{
			if(strKey.length() != 16)//check to see if its 8 bytes
				throw new Exception("DEScbc Key must be 8 bytes");	
			//by defination encrypted blocks must be in 
			//blocks of 8 bytes
			if (strCText.length() % 8 != 0)
				throw new Exception("Cipher Text Blocks Must Be In Blocks Of 8 Bytes");
			
			if(strICV.length() != 16)//check to see if its 8 bytes
				throw new Exception("DEScbc ICV must be 8 bytes");	
			
			
			byte[] bytKey = new byte[8];

			bytKey = cvHexStrToByteArr(strKey);

			byte[]bytICV = new byte[8];
			bytICV = cvHexStrToByteArr(strICV);
			IvParameterSpec icvParam = new IvParameterSpec(bytICV);

			SecretKeySpec desKey = new SecretKeySpec(bytKey, "DES");
			
 			//define cipher instance as type "DES/CBC/PKCS5Padding"
		        Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");

			//init cipher in encryption mode and set key  
        		cipher.init(Cipher.DECRYPT_MODE,desKey,icvParam);
			
			byte[] decrypted = new byte[strCText.length()];
			byte[] cipherText = new byte[strCText.length()/2];
			
			cipherText = cvHexStrToByteArr(strCText);

	       		decrypted = cipher.doFinal(cipherText);
			
			gui.txtPlainText.setText(cvByteArrToString(decrypted).substring(0,strCText.length()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
   	/*
    	 * Converts a byte array to hex string
    	 */
    	private String cvByteArrToString(byte[] block) 
	{
        	StringBuffer buf = new StringBuffer();

		int len = block.length;

        	for (int i = 0; i < len; i++) 
		{
            		byteToHex(block[i], buf);
        	} 
       	 	return buf.toString();
    	}

    	/*
     	* Converts a byte to hex digit and writes to the supplied buffer
     	*/
    	private void byteToHex(byte b, StringBuffer buf) 
	{
        	char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                	'9', 'A', 'B', 'C', 'D', 'E', 'F' };
        	int high = ((b & 0xf0) >> 4);
        	int low = (b & 0x0f);
        	buf.append(hexChars[high]);
       	 	buf.append(hexChars[low]);
    	}

	//function to covert a string of HEX values
	//to a byte array 
	private byte[] cvHexStrToByteArr(String hexString)
	{
		byte [] hexByte = new byte[hexString.length()/2];

		for(int i=0;i<hexString.length();i=i+2)
			hexByte[i/2] = strToByte(hexString.substring(i,i+2));
		
		return hexByte;
	}
	//used by cvHexStrToByteArr to convert two
	//characters to a bye value
	private byte strToByte(String strByte)
	{
		byte bytByte;		
		int nibbleCounter = 0;
		//String currentNibble;
		int value = 0;
		
		while (nibbleCounter < 2)
		{
			int temp = 0;
			switch(strByte.charAt(nibbleCounter))
			{
				case '0':
					temp = 0x00;
					break;
				case '1':
					temp = 0x01;
					break;
				case '2':
					temp = 0x02;
					break;
				case '3':
					temp = 0x03;
					break;
				case '4':
					temp = 0x04;
					break;
				case '5':
					temp = 0x05;
					break;
				case '6':
					temp = 0x06;
					break;
				case '7':
					temp = 0x07;
					break;
				case '8':
					temp = 0x08;
					break;
				case '9':
					temp = 0x09;
					break;
				case 'a':
					temp = 0x0a;
					break;
				case 'b':
					temp = 0x0b;
					break;
				case 'c':
					temp = 0x0c;
					break;
				case 'd':
					temp = 0x0d;
					break;
				case 'e': 
					temp = 0x0e;
					break;
				case 'f':
					temp = 0x0f;
					break;
				case 'A':
					temp = 0x0a;
					break;
				case 'B':
					temp = 0x0b;
					break;
				case 'C':
					temp = 0x0c;
					break;
				case 'D':
					temp = 0x0d;
					break;
				case 'E': 
					temp = 0x0e;
					break;
				case 'F':
					temp = 0x0f;
					break;
			}
			if(nibbleCounter == 0) 
				value = temp * 16;
			else
				value = value + temp;

			nibbleCounter++;
		}
	
		return (byte)value;

	}


}