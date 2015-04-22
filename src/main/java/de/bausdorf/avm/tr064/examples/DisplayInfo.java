package de.bausdorf.avm.tr064.examples;
/***********************************************************************************************************************
 *
 * javaAVMTR064 - open source Java TR-064 API
 *===========================================
 *
 * Copyright 2015 Marin Pollmann <pollmann.m@gmail.com>
 * 
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************/

import java.io.IOException;

import javax.xml.bind.JAXBException;





import de.bausdorf.avm.tr064.FritzConnection;


public class DisplayInfo {
	static String ip = null;
	static String user = null;
	static String password = null;
	
	
	public static void main(String[] args) throws  IOException, JAXBException{
		if( args.length < 3 ) {
			System.out.println("args: <fb-ip> <user> <password>");
		} else {
			ip = args[0];
			user = args[0];
			password = args[0];
		}
		FritzConnection fcWithoutUser = new FritzConnection(ip);
		fcWithoutUser.init();
		fcWithoutUser.printInfo();
		System.out.println("##################################################################");
		FritzConnection fcWithUser = new FritzConnection(ip,user,password);
		fcWithUser.init();
		fcWithUser.printInfo();
	}
}
