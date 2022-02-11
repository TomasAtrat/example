package com.example.application.backend.readerConfig.mappers;

import com.example.application.backend.models.ReaderConfiguration;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface ReaderMapper {
    @Insert("INSERT INTO readerConfig (hostname, power, rssi, MaxPower, MaxRSSI) values" +
            " (#{reader.hostname}, #{reader.power}, #{reader.RSSI}, #{reader.maxPower}, #{reader.maxRSSI})")
    void add(@Param("reader") final ReaderConfiguration readerConfiguration);

    @Select("SELECT * FROM readerConfig ORDER BY id DESC LIMIT 1")
    Optional<ReaderConfiguration> getLastReader();

    @Select("SELECT * FROM readerConfig WHERE hostname = #{hostname}")
    Optional<ReaderConfiguration> getReaderByHostname(@Param("hostname") final String hostname);

    @Update("UPDATE readerConfig SET maxRssi = #{reader.maxRSSI}, MaxPower = #{reader.maxPower}," +
            " power = #{reader.power}, rssi = #{reader.RSSI} WHERE hostname = #{reader.hostname}")
    void update(@Param("reader") final ReaderConfiguration readerConfiguration);
}
