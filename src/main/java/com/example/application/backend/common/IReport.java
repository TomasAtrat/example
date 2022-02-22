package com.example.application.backend.common;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;

public interface IReport extends TagReportListener {
    @Override
    void onTagReported(ImpinjReader impinjReader, TagReport tagReport);
}
