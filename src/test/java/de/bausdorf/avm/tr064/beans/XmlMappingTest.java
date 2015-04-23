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
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}
	
	@Before
	public void setUp() throws Exception
	{
		this.igdDescIS = ClassLoader.getSystemResourceAsStream("igd_desc_test.xml");
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
}
