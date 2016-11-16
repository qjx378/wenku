package com.izerofx.framework.core.util;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.alibaba.simpleimage.ImageFormat;
import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteParameter;
import com.alibaba.simpleimage.render.WriteRender;

/**
 * 
 * 类名称：ImageUtil<br>
 * 类描述：图片工具类<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月8日 上午11:09:28<br>
 * @version v1.0
 *
 */
public class ImageUtil {

    /**
     * 缩放用户上传图片，减小宽带占用
     * @param inputStream
     * @param outputStream
     * @param maxWidth
     * @param maxHeight
     * @throws SimpleImageException
     */
    public static void scale(InputStream inputStream, FileOutputStream outputStream, int maxWidth, int maxHeight) throws SimpleImageException {
        ScaleParameter scaleParam = new ScaleParameter(maxWidth, maxHeight);
        WriteParameter writeParam = new WriteParameter();

        ImageRender rr = new ReadRender(inputStream);
        ImageRender sr = new ScaleRender(rr, scaleParam);
        ImageRender wr = null;
        try {
            wr = new WriteRender(sr, outputStream, ImageFormat.JPEG, writeParam);
            wr.render();
        } catch (SimpleImageException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            try {
                if (wr != null) {
                    wr.dispose();
                }
            } catch (SimpleImageException e) {
                throw e;
            }

        }
    }

}
