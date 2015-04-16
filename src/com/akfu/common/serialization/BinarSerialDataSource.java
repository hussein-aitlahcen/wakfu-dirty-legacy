package com.akfu.common.serialization;

public interface BinarSerialDataSource
{
    void updateToSerializedPart();
    
    void onDataChanged();
}
