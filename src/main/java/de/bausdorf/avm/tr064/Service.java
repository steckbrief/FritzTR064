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
package de.bausdorf.avm.tr064;

import java.io.IOException;



import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import de.bausdorf.avm.tr064.beans.ActionType;
import de.bausdorf.avm.tr064.beans.ScpdType;
import de.bausdorf.avm.tr064.beans.ScpdType2;
import de.bausdorf.avm.tr064.beans.ServiceDesc;

public class Service {
	private static Logger LOG = LoggerFactory.getLogger(FritzConnection.class);

	private ServiceDesc serviceXML;
	private Map<String,Action> actions;
	
	public Service(ServiceDesc serviceXML, FritzConnection connection) throws IOException, JAXBException{
		this.serviceXML = serviceXML;
		actions = new HashMap<String,Action>();
		
		try (InputStream is = connection.getXMLIS(serviceXML.getScpdurl())){

		ObjectMapper mapper = new XmlMapper();
		ScpdType scpd = mapper.readValue(is, ScpdType.class);
		LOG.debug(scpd.toString());
		for (ActionType a : scpd.getActionList()){
			actions.put(a.getName(), new Action(a, scpd.getServiceStateTable(), connection, this.serviceXML));
		}
		} catch (Exception e){
			InputStream  is = connection.getXMLIS(serviceXML.getScpdurl());
			ObjectMapper mapper = new XmlMapper();
			ScpdType2 scpd = mapper.readValue(is, ScpdType2.class);
			LOG.debug(scpd.toString());
			for (ActionType a : scpd.getActionList()){
				
				actions.put(a.getName(), new Action(a, scpd.getServiceStateTable(), connection, this.serviceXML));
			}
		}
		
	}
	
	public Map<String,Action> getActions(){
		return actions;
	}
	public Action getAction(String name){
		return getActions().get(name);
	}
	
	
	public String toString(){
		return serviceXML.getServiceType();
	}
}
