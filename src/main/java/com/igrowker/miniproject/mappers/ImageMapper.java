package com.igrowker.miniproject.mappers;

import com.igrowker.miniproject.models.Image;

import java.util.Map;

public class ImageMapper {

    private ImageMapper() {
    }

    public static Image toImage(Map<?, ?> map) {
        return Image.builder()
                .publicId((String) map.get("public_id"))
                .url((String) map.get("url"))
                .weight(map.get("width").toString())
                .height(map.get("height").toString())
                .build();
    }

}
