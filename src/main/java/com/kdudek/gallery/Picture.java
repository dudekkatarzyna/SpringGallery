package com.kdudek.gallery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.TreeMap;


public class Picture implements Serializable {

    private int index;
    private String name;
    private long width;
    private long height;
    private long size;
    private long created;

    static TreeMap<Integer, Picture> pictures;
    private static String path;


    public Picture(int index, String name, long width, long height, long size, long created) {
        this.index = index;
        this.name = name;
        this.width = width;
        this.height = height;
        this.size = size;
        this.created = created;
    }


    public static TreeMap<Integer, Picture> getAll() {
        return Picture.pictures;
    }

    public static String getAllJSON() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = "";

        jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Picture.pictures);

        return jsonResult;
    }


    public static void fillPictureMap() throws IOException {

        if (Picture.pictures != null)
            return;

        Picture.pictures = new TreeMap<>();

        File folder = new File(
                Thread.currentThread().getContextClassLoader().getResource(Picture.path).getPath()
        );
        File[] files = folder.listFiles();

        System.out.println(files.length);
        if (files != null) {
            int id = 0;
            for (File file : files) {
                if (file.isFile()) {
                    BufferedImage image = null;

                    image = ImageIO.read(file);

                    Path filepath = Paths.get(file.getPath());
                    BasicFileAttributes attr = Files.readAttributes(filepath, BasicFileAttributes.class);

                    Picture tmp = new Picture(id, file.getName(), image.getWidth(), image.getHeight(), file.length(), attr.creationTime().toMillis());
                    Picture.pictures.put(id++, tmp);
                }
            }

        }
    }


    private static String getPicturePath(int id) {
        return path + "/" + Picture.pictures.get(id).name;
    }


    public static void setPath(String path) {
        Picture.path = path;
    }

    public byte[] toBytes() throws IOException {

        InputStream in;
        in = Thread.currentThread().getContextClassLoader().getResourceAsStream(Picture.getPicturePath(this.index));
        return IOUtils.toByteArray(in);
    }

    private static String getPictureFullPath(String name) {
        String galleryPath = Thread.currentThread().getContextClassLoader().getResource(Picture.path).getPath();
        return galleryPath + "/" + name;
    }

    public String delete() throws JSONException {

        JSONObject r = new JSONObject();

        if (!Picture.pictures.containsKey(this.index)) {
            r.put("result", false);
            return r.toString();
        }

        System.out.println(getPictureFullPath(this.name));
        Path filepath = Paths.get(getPictureFullPath(this.name).substring(1));


        try {
            Files.delete(filepath);
            Picture.pictures.remove(this.index);
        } catch (IOException e) {
            e.printStackTrace();
            r.put("result", false);
            return r.toString();
        }

        r.put("result", true);
        return r.toString();
    }

    public long getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }


    public long getSize() {
        return size;
    }

    public long getCreated() {
        return created;
    }

    public long getWidth() {
        return width;
    }

    public long getHeight() {
        return height;
    }


}
