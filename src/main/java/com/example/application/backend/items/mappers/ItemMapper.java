package com.example.application.backend.items.mappers;

import com.example.application.backend.models.ItemImage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ItemMapper {

    @Insert("INSERT INTO images (data, filename, mimeType) VALUES (#{image.data}, #{image.filename}, #{image.mimeType})")
    void addImage(@Param("image") final ItemImage itemImage);
}
