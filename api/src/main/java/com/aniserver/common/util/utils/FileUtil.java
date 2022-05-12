package com.aniserver.common.util.utils;

import com.aniserver.common.Const;
import com.aniserver.common.util.Msg;
import com.aniserver.common.util.exception.FileException;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.DemuxerTrack;
import org.jcodec.common.NIOUtils;
import org.jcodec.common.SeekableByteChannel;
import org.jcodec.common.model.Picture;
import org.jcodec.containers.mp4.demuxer.MP4Demuxer;
import org.jcodec.scale.AWTUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtil {
    public boolean isExist(String path){
        File file = null;
        try{
            file = new File(path);
        }catch(Exception e){
            return false;
        }

        return file.exists();
    }

    public File getFileInfo(String path){
        if(!this.isExist(path))
            throw new FileException(Msg.FILE_UTIL_ERROR_NOT_EXIST_PATH);

        File file = new File(path);
        return file;
    }

    public void makeDirectory(String path) {
        if(isExist(path)) return;

        File f = new File(path);
        f.mkdir();
    }

    public void removeDirectory(String path) throws FileException {
        if(!isExist(path))
            throw new FileException(Msg.FILE_UTIL_ERROR_NOT_EXIST_PATH);

        File f = new File(path);
        f.delete();
    }

    public void moveDirectory(String target, String move){
        if(!isExist(target))
            throw new FileException(Msg.FILE_UTIL_ERROR_NOT_EXIST_PATH);

        if(isExist(move))
            throw new FileException(Msg.FILE_UTIL_ERROR_EXIST_PATH);

        File t = new File(target);
        File m = new File(move);
        t.renameTo(m);
    }

    public String getExtension(String name){
        String extension = "";

        for(int i=name.length()-1;i>=0&&name.charAt(i)!='.';i--)
            extension = name.charAt(i) + extension;

        return extension;
    }

    /**
     * 썸네일 생성
     */
    public void makeThumbnail(File videoFile) {
        String fileName = videoFile.getAbsolutePath();
        String baseName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
        String savePath = Const.THUMBNAIL_PATH;

        if(!isExist(savePath)){
            makeDirectory(savePath);
        }

        double frameNumber = 0d;

        try {
            SeekableByteChannel bc = NIOUtils.readableFileChannel(videoFile);
            MP4Demuxer dm = new MP4Demuxer(bc);
            DemuxerTrack vt = dm.getVideoTrack();

            frameNumber = vt.getMeta().getTotalDuration() / 5.0;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Picture frame = FrameGrab.getNativeFrame(new File(fileName), frameNumber);
            BufferedImage img = AWTUtil.toBufferedImage(frame);
            File pngFile = new File(savePath + "/" + baseName + ".png");

            if (!pngFile.exists())
                pngFile.createNewFile();

            ImageIO.write(img, "png", pngFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JCodecException e) {
            e.printStackTrace();
        }
    }
}
