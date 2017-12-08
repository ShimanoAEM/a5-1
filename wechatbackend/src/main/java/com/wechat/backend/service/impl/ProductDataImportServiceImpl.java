package com.wechat.backend.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wechat.backend.dao.ProductRepository;
import com.wechat.backend.domain.Product;
import com.wechat.backend.exception.DataImportException;
import com.wechat.backend.service.ProductDataImportService;
import com.wechat.backend.utils.DataFileUtil;
import com.wechat.backend.utils.FileMoveHelper;
import com.wechat.backend.utils.JacksonHelper;

@Service
public class ProductDataImportServiceImpl implements ProductDataImportService{

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	private final String PRODUCT_PREFIX = "productData";
	
	private final String FILE_EXTENSION = "json";
	
	@Value("${spring.file.productimportpath}")
	private String productimportpath;
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Override
	public void importProductData() {
		File moveFile = handlerFile(productimportpath , PRODUCT_PREFIX);
		if (moveFile != null) {
			importData(moveFile , PRODUCT_PREFIX);
		}
	}

	private File handlerFile(String path , String filePrefix){
		File files = new File(path);
		if (files.exists()) {
			File[] fileArray = files.listFiles();
			if (fileArray.length == 0) {
	             //System.out.println("文件夹是空的!");
	             return null;
	         }else {
	        	 for(File file:fileArray){ 
					if (file.isFile() && file.getName().startsWith(filePrefix) && file.getName().endsWith(FILE_EXTENSION)) {
						File moveDir = new File(files.getPath() + "/processing");
						if (!moveDir.exists())
						{
							moveDir.mkdirs();
						}
						String toPath = moveDir.getAbsolutePath();
						try {
							FileMoveHelper.MoveFolderAndFileWithSelf(file.getAbsolutePath(), toPath);
						} catch (Exception e) {
							LOGGER.debug("move file error", e);
						}
						File moveFile = new File(toPath + "/" + file.getName());
						return moveFile;
					}
				}
			}
		}
		return null;		  
	}
	
	private void importData(File file , String prefix){
		try {
			String json = FileUtils.readFileToString(file);
			if (PRODUCT_PREFIX.equals(prefix)) {
				List<Product> proDataList = new ArrayList<>();
				proDataList = JacksonHelper.fromJSONList(json, Product.class);
				for (Product product : proDataList) {
					productRepository.save(product);
				}
			}
		} catch (IOException | DataImportException e) {
			StringBuilder errorsb = null;
			LOGGER.debug("import data error ", e);
			errorsb = new StringBuilder();
			errorsb.append(e.fillInStackTrace()).append("\r\n");
			final StackTraceElement[] stackTraceElementArr = e.getStackTrace();
			if (null != stackTraceElementArr && stackTraceElementArr.length > 0)
			{
				for (int i = 0; i < stackTraceElementArr.length; i++)
				{
					errorsb.append("\t");
					if (null != stackTraceElementArr[i])
					{
						errorsb.append(stackTraceElementArr[i].toString()).append("\r\n");
					}
				}
			}
			if (null != errorsb)
			{
				writeErrorFile(errorsb.toString(), file);
			}
		}finally {
			File moveDir = new File(file.getParentFile().getParentFile() + "/archive");
			if (!moveDir.exists())
			{
				moveDir.mkdirs();
			}
			String toPath = moveDir.getAbsolutePath();
			try {
				FileMoveHelper.MoveFolderAndFileWithSelf(file.getAbsolutePath(), toPath);
			} catch (Exception e) {
				LOGGER.debug("move file error", e);
			}
		}
	}
	
	private void writeErrorFile(final String errorsb, File file)
	{
		if (errorsb.length() > 0)
		{
		  DataFileUtil.writeFile(file.getParentFile().getParentFile().getPath() + "/error", "/error_" + file.getName() + System.currentTimeMillis(), errorsb);
		}
	}
}
