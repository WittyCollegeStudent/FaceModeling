package com.qianbo.service;

import com.qianbo.service.impl.IConvertService;
import com.qianbo.util.Constants;
import com.qianbo.util.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Service("ConvertService")
@Transactional
public class ConvertService implements IConvertService {

    /*
    * 通过图片建模，成功则返回true，否则返回false
    * */
    public boolean convert(ImageUtil imageUtil,String path, String name) {
        System.out.println("start convert");
        String file_path = path + File.separator + name;
        System.out.println("file_path = "+file_path);
        String command="python E:\\computer-vision\\human-face\\face-reconstruction\\process.py " + file_path;
        Runtime runtime = Runtime.getRuntime();
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            Process process = runtime.exec(command);
            BufferedReader bufferedReader = new BufferedReader
                    (new InputStreamReader(process.getInputStream()));


            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
            System.out.println(sb.toString());
            String downloadFileName = "fbx&obj&png.zip";
            File file = new File(Constants.IMAGE_DOWNLOAD_ABSO_PATH + downloadFileName);

            //如果建模的文件不存在，则说明建模失败
            if(!file.exists()){
                imageUtil.setHandle_success(Constants.STATE_HANDLE_FAILURE);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            imageUtil.setHandle_success(Constants.STATE_HANDLE_FAILURE);
            return false;
        }
        imageUtil.setHandle_success(Constants.STATE_HANDLE_SUCCESS);
        return true;
    }
}
