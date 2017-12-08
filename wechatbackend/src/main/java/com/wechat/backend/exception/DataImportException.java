package com.wechat.backend.exception;

public class DataImportException extends Exception{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new  json converter exception.
	 */
	public DataImportException()
	{
	}

	/**
	 * Instantiates a new  json converter exception.
	 *
	 * @param paramString
	 *           the param string
	 */
	public DataImportException(final String paramString)
	{
		super(paramString);
	}

	/**
	 * Instantiates a new  json converter exception.
	 *
	 * @param paramString
	 *           the param string
	 * @param paramThrowable
	 *           the param throwable
	 */
	public DataImportException(final String paramString, final Throwable paramThrowable)
	{
		super(paramString, paramThrowable);
	}

	/**
	 * Instantiates a new  json converter exception.
	 *
	 * @param paramThrowable
	 *           the param throwable
	 */
	public DataImportException(final Throwable paramThrowable)
	{
		super(paramThrowable);
	}

	/**
	 * Instantiates a new  json converter exception.
	 *
	 * @param paramString
	 *           the param string
	 * @param paramThrowable
	 *           the param throwable
	 * @param paramBoolean1
	 *           the param boolean 1
	 * @param paramBoolean2
	 *           the param boolean 2
	 */
	protected DataImportException(final String paramString, final Throwable paramThrowable, final boolean paramBoolean1,
			final boolean paramBoolean2)
	{
		super(paramString, paramThrowable, paramBoolean1, paramBoolean2);
	}
}
