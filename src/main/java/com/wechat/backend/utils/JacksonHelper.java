package com.wechat.backend.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.map.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.backend.exception.DataImportException;


public class JacksonHelper {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(JacksonHelper.class);

	/** The to JSON mapper. */
	private static ObjectMapper toJSONMapper = new ObjectMapper();

	/** The from JSON mapper. */
	private static ObjectMapper fromJSONMapper = new ObjectMapper();

	static
	{
		fromJSONMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		fromJSONMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

	}

	/**
	 * * transform object to json format string.
	 *
	 * @param obj
	 *           the obj
	 * @return the string
	 * @throws JsonConverterException
	 *            the  json converter exception
	 */
	public static String toJSON(final Object obj) throws DataImportException
	{
		final ObjectMapper mapper = toJSONMapper;
		final StringWriter writer = new StringWriter();
		try
		{
			mapper.writeValue(writer, obj);
			return writer.toString();
		}
		catch (final Exception e)
		{
			LOG.error("generate jsonData error " + obj, e);
			throw new DataImportException(e);
		}
	}

	public static String toJSON(final Object obj, final JacksonAnnotationIntrospector jacksonAnnotationIntrospector) throws DataImportException
	{
		final ObjectMapper mapper = toJSONMapper;
		if (jacksonAnnotationIntrospector != null)
		{
			mapper.setAnnotationIntrospector(jacksonAnnotationIntrospector);
		}

		return toJSON(obj);
	}

	/**
	 * * transform object to json format string to appointed OutStream use appointed CharSet.
	 *
	 * @param obj
	 *           the obj
	 * @param stream
	 *           the stream
	 * @param charset
	 *           the charset
	 * @throws JsonConverterException
	 *            the  json converter exception
	 */
	public static void toJSON(final Object obj, final OutputStream stream, final String charset) throws DataImportException
	{
		final ObjectMapper mapper = toJSONMapper;
		try
		{
			final OutputStreamWriter writer = new OutputStreamWriter(stream, charset);
			mapper.writeValue(writer, obj);
		}
		catch (final Exception e)
		{
			throw new DataImportException(e);
		}
	}

	/**
	 * * transform string which json format to appointed type.
	 *
	 * @param <T>
	 *           the generic type
	 * @param json
	 *           the json
	 * @param clazz
	 *           the clazz
	 * @return the t
	 * @throws JsonConverterException
	 *            the  json converter exception
	 */
	public static <T> T fromJSON(final String json, final Class<T> clazz) throws DataImportException
	{
		final ObjectMapper mapper = fromJSONMapper;
		try
		{
			return mapper.readValue(json, clazz);
		}
		catch (final Exception e)
		{
			throw new DataImportException(e);
		}
	}

	/**
	 * * transform string which json format to appointed type use by InputStream.
	 *
	 * @param <T>
	 *           the generic type
	 * @param json
	 *           the json
	 * @param clazz
	 *           the clazz
	 * @return the t
	 * @throws JsonConverterException
	 *            the  json converter exception
	 */
	public static <T> T fromJSON(final InputStream json, final Class<T> clazz) throws DataImportException
	{
		final ObjectMapper mapper = fromJSONMapper;
		try
		{
			return mapper.readValue(json, clazz);
		}
		catch (final Exception e)
		{
			throw new DataImportException(e);
		}
	}

	/**
	 * To JSON list.
	 *
	 * @param <T>
	 *           the generic type
	 * @param list
	 *           the list
	 * @return the string
	 * @throws JsonConverterException
	 *            the  json converter exception
	 */
	public static <T> String toJSONList(final List<T> list) throws DataImportException
	{
		String jsonVal = null;
		try
		{
			jsonVal = toJSONMapper.writeValueAsString(list);
		}
		catch (final Exception e)
		{
			throw new DataImportException(e);
		}
		return jsonVal;
	}

	/**
	 * From JSON list.
	 *
	 * @param <T>
	 *           the generic type
	 * @param jsonVal
	 *           the json val
	 * @param clazz
	 *           the clazz
	 * @return the list
	 * @throws JsonConverterException
	 *            the  json converter exception
	 */
	public static <T> List<T> fromJSONList(final String jsonVal, final Class<?> clazz) throws DataImportException
	{

		List<T> list = null;
		try
		{
			list = fromJSONMapper.readValue(jsonVal, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
		}
		catch (final Exception e)
		{
			throw new DataImportException(e);
		}
		return list;
	}



	public static NameValuePair[] generatNameValuePair(final Map<String, String> properties)
	{
		final NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
		int i = 0;
		for (final Map.Entry<String, String> entry : properties.entrySet())
		{
			nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
		}

		return nameValuePair;
	}

}
