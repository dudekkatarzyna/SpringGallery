package com.kdudek.gallery;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Controller

public class BlogController implements InitializingBean {

    @Autowired
    private GlobalProperties globalProperties;

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        return "redirect:/gallery";
    }

    @RequestMapping(
            value = "/gallery/pictures",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String showAllPictures() throws IOException {

        return Picture.getAllJSON();
    }

    @RequestMapping(
            value = "/gallery/pictures/{id}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    @ResponseBody
    public byte[] showPicture(@PathVariable("id") int id) throws IOException {
        return Picture.pictures.get(id).toBytes();
    }


    @RequestMapping(
            value = "/gallery/pictures/{id}",
            method = RequestMethod.DELETE
    )
    @ResponseBody
    public String deletePicture(@PathVariable("id") int id) throws JSONException {
        try {
            return Picture.pictures.get(id).delete();
        } catch (Exception e) {
        }
        JSONObject r = new JSONObject();
        r.put("result", false);
        return r.toString();
    }

    @RequestMapping(
            value = "/gallery",
            method = RequestMethod.GET
    )
    public String showGallery(Model model) {
        model.addAttribute("pictures", Picture.getAll());


        System.out.println();

        return "gallery";
    }

    @RequestMapping(
            value = "/gallery/panel",
            method = RequestMethod.GET
    )
    public String login(Model model) {
        model.addAttribute("pictures", Picture.getAll());
        model.addAttribute("panel", true);
        model.addAttribute("login", true);

        return "gallery";
    }

    @RequestMapping(
            value = "/gallery/panel",
            method = RequestMethod.POST
    )
    public String logged(Model model) {

        model.addAttribute("pictures", Picture.getAll());
        model.addAttribute("panel", true);
        model.addAttribute("login", false);

        return "gallery";
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Picture.setPath(globalProperties.getGalleryPath());
        Picture.fillPictureMap();

    }
}