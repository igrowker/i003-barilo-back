package com.igrowker.miniproject.mappers;

import com.igrowker.miniproject.models.Image;

import java.util.Map;

public class ImageMapper {

    private ImageMapper() {
    }

    public static Image toImage(Map<?, ?> map) {
        return Image.builder()
                .publicId(map.get("public_id").toString())
                .url(map.get("url").toString())
                .weight(map.get("width").toString())
                .height(map.get("height").toString())
                .build();
    }

}
