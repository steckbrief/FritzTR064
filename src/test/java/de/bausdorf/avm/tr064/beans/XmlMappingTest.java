package de.bausdorf.avm.tr064.beans;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlMappingTest
{
	private static final Logger LOG = LoggerFactory.getLogger(XmlMappingTest.class);
	
	InputStream igdDescIS;
	InputStream anyIS;
	InputStream igdicfgIS;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}
	
	@Before
	public void setUp() throws Exception
	{
		this.igdDescIS = ClassLoader.getSystemResourceAsStream("igd_desc_test.xml");
		this.anyIS = ClassLoader.getSystemResourceAsStream("any_test.xml");
		this.igdicfgIS = ClassLoader.getSystemResourceAsStream("igdicfgSCPD_test.xml");
	}
	
	@Test
	public void testIgdDescMapping()
	{
		ObjectMapper mapper = new XmlMapper();
		try
		{
			RootType2 root = mapper.readValue(this.igdDescIS, RootType2.class);
			LOG.debug(root.toString());
		}
		catch (IOException e)
		{
			LOG.error(e.getLocalizedMessage(), e);
			fail(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testAnyMapping() {
		ObjectMapper mapper = new XmlMapper();
		try
		{
			ScpdType2 root = mapper.readValue(this.anyIS, ScpdType2.class);
			LOG.debug(root.toString());
		}
		catch (IOException e)
		{
			LOG.error(e.getLocalizedMessage(), e);
			fail(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testIgdicfgSCPDMapping() {
		try {
			this.testMapping(this.igdicfgIS, ScpdType2.class);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			fail(e.getLocalizedMessage());
		}
	}
	
	private void testMapping(InputStream is, Class<?> mapTo) throws Exception {
		ObjectMapper mapper = new XmlMapper();
		LOG.debug(mapper.readValue(is, mapTo).toString());
	}
}
