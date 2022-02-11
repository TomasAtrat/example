package com.example.application.readerConfig.services;

import com.example.application.models.ReaderConfiguration;
import com.example.application.readerConfig.mappers.ReaderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderConfigService {

    private final ReaderMapper readerMapper;

    @Autowired
    public ReaderConfigService(ReaderMapper readerMapper) {
        this.readerMapper = readerMapper;
    }

    public void addReader(ReaderConfiguration readerConfiguration){
        ReaderConfiguration reader = this.readerMapper.getReaderByHostname(readerConfiguration.getHostname())
                .orElse(null);
        if(reader == null) this.readerMapper.add(readerConfiguration);
        else this.readerMapper.update(readerConfiguration);
    }

    public ReaderConfiguration getLastReader(){
        return this.readerMapper.getLastReader().orElse(null);
    }
}
