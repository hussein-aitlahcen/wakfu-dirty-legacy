package com.akfu.common.configuration;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.set.hash.TIntHashSet;
import gnu.trove.set.hash.TLongHashSet;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.akfu.common.util.ByteArray;
import com.akfu.common.util.PrimitiveConverter;
import com.google.common.base.Charsets;

public class SystemConfiguration
{
    public static final EnumSet<SystemConfigurationType> DISPATCH_SERIALIZATION;
    public static final SystemConfiguration INSTANCE;
    private static final Logger m_logger;
    private static final Pattern SPLIT_PATTERN;
    private final Map<SystemConfigurationType, String> m_properties;
    private byte[] m_clientData;
    private byte[] m_dispatchData;
    
    public SystemConfiguration() {
        super();
        this.m_properties = new EnumMap<SystemConfigurationType, String>(SystemConfigurationType.class);
    }
    
    public static void configure(final String filename) {
        try {
            SystemConfiguration.INSTANCE.load(filename);
        }
        catch (IOException e) {
            SystemConfiguration.m_logger.error((Object)("Erreur lors du chargement du fichier de propri\u00e9t\u00e9s : " + filename), (Throwable)e);
        }
        catch (RuntimeException e2) {
            SystemConfiguration.m_logger.error((Object)("Erreur lors du chargement du fichier de propri\u00e9t\u00e9s : " + filename), (Throwable)e2);
        }
    }
    
    public void load(final String filename) throws IOException {
        final Properties props = new Properties();
        props.load(new FileInputStream(filename));
        this.m_properties.clear();
        for (final SystemConfigurationType type : SystemConfigurationType.values()) {
            String value = props.getProperty(type.getKey());
            if (value == null) {
                value = type.getDefaultValue();
            }
            this.m_properties.put(type, value);
        }
        this.buildSerialisation();
    }
    
    private void buildSerialisation() {
        this.buildClientSerialization();
        this.buildDispatchSerialization();
    }
    
    private void buildClientSerialization() {
        final ByteArray bb = new ByteArray();        
        bb.putInt(m_properties.size());
        for(final SystemConfigurationType property : m_properties.keySet()) {
        	final String value = m_properties.get(property);
        	bb.putShort(property.getId());
            final byte[] bytes = value.getBytes(Charsets.UTF_8);
            bb.putInt(bytes.length);
            bb.put(bytes);
        }
//        bb.putInt(SystemConfigurationType.NUM_SHARED_PROPERTIES);
//        for (final SystemConfigurationType type : SystemConfigurationType.values()) {
//            if (type.isShareWithClient()) {
//                String value = this.m_properties.get(type);
//                if(value == null)
//                	value = type.getDefaultValue();
//	            bb.putShort(type.getId());
//	            final byte[] bytes = value.getBytes(Charsets.UTF_8);
//	            bb.putInt(bytes.length);
//	            bb.put(bytes);
//            }
//        }        
        this.m_clientData = bb.toArray();
    }
    
    private void buildDispatchSerialization() {
        final ByteArray bb = new ByteArray();
        bb.putInt(SystemConfiguration.DISPATCH_SERIALIZATION.size());
        for (final SystemConfigurationType type : SystemConfiguration.DISPATCH_SERIALIZATION) {
            String value = this.m_properties.get(type);
            if (value == null) 
            	value = type.getDefaultValue();
            bb.putShort(type.getId());
            final byte[] bytes = value.getBytes(Charsets.UTF_8);
            bb.putInt(bytes.length);
            bb.put(bytes);
        }
        this.m_dispatchData = bb.toArray();
    }
    
    public byte[] serializeForClient() {
        if (this.m_clientData == null) {
            this.buildClientSerialization();
        }
        return this.m_clientData;
    }
    
    public byte[] serializeForDispatch() {
        if (this.m_dispatchData == null) {
            this.buildDispatchSerialization();
        }
        return this.m_dispatchData;
    }
    
    public void unserialize(final byte[] data) {
        this.m_properties.clear();
        final ByteBuffer bb = ByteBuffer.wrap(data);
        for (int numProperties = bb.getInt(), i = 0; i < numProperties; ++i) {
            final SystemConfigurationType type = SystemConfigurationType.getById(bb.getShort());
            final byte[] bytes = new byte[bb.getInt()];
            bb.get(bytes);
            final String value = new String(bytes);
            this.m_properties.put(type, value);
        }
    }
    
    public boolean containsKey(final SystemConfigurationType key) {
        return this.m_properties.containsKey(key);
    }
    
    public int getIntValue(final SystemConfigurationType type) {
        String value = this.m_properties.get(type);
        if (value == null) {
            value = type.getDefaultValue();
        }
        return PrimitiveConverter.getInteger(value, 0);
    }
    
    public long getLongValue(final SystemConfigurationType type) {
        String value = this.m_properties.get(type);
        if (value == null) {
            value = type.getDefaultValue();
        }
        return PrimitiveConverter.getLong(value, 0L);
    }
    
    public boolean getBooleanValue(final SystemConfigurationType type) {
        String value = this.m_properties.get(type);
        if (value == null) {
            value = type.getDefaultValue();
        }
        return PrimitiveConverter.getBoolean(value, false);
    }
    
    public String getStringValue(final SystemConfigurationType type) {
        String value = this.m_properties.get(type);
        if (value == null) {
            value = type.getDefaultValue();
        }
        return value;
    }
    
    public ArrayList<String> getStringArrayList(final SystemConfigurationType type) {
        String value = this.m_properties.get(type);
        if (value == null) {
            value = type.getDefaultValue();
        }
        final ArrayList<String> list = new ArrayList<String>();
        for (final String s : SystemConfiguration.SPLIT_PATTERN.split(value)) {
            if (!s.isEmpty()) {
                list.add(s);
            }
        }
        return list;
    }
    
    public TIntArrayList getIntArrayList(final SystemConfigurationType type) {
        String value = this.m_properties.get(type);
        if (value == null) {
            value = type.getDefaultValue();
        }
        final TIntArrayList list = new TIntArrayList();
        for (final String s : SystemConfiguration.SPLIT_PATTERN.split(value)) {
            if (!s.isEmpty()) {
                final int i = PrimitiveConverter.getInteger(s, Integer.MIN_VALUE);
                if (i != Integer.MIN_VALUE) {
                    list.add(i);
                }
            }
        }
        return list;
    }
    
    public TIntHashSet getIntHashSet(final SystemConfigurationType type) {
        String value = this.m_properties.get(type);
        if (value == null) {
            value = type.getDefaultValue();
        }
        final TIntHashSet list = new TIntHashSet();
        for (final String s : SystemConfiguration.SPLIT_PATTERN.split(value)) {
            if (!s.isEmpty()) {
                final int i = PrimitiveConverter.getInteger(s, Integer.MIN_VALUE);
                if (i != Integer.MIN_VALUE) {
                    list.add(i);
                }
            }
        }
        return list;
    }
    
    public TLongHashSet getLongHashSet(final SystemConfigurationType type) {
        String value = this.m_properties.get(type);
        if (value == null) {
            value = type.getDefaultValue();
        }
        final TLongHashSet list = new TLongHashSet();
        for (final String s : SystemConfiguration.SPLIT_PATTERN.split(value)) {
            if (!s.isEmpty()) {
                final long l = PrimitiveConverter.getLong(s, Long.MIN_VALUE);
                if (l != Long.MIN_VALUE) {
                    list.add(l);
                }
            }
        }
        return list;
    }
    
    public String changeProperty(final SystemConfigurationType key, final String value) throws IllegalArgumentException {
        final String res = this.m_properties.put(key, value);
        this.buildSerialisation();
        return res;
    }
    
    @Override
    public String toString() {
        return "SystemConfiguration{m_properties=" + this.m_properties.size() + '}';
    }
    
    public String getValueToString(final String sysConfKey) {
        return ToStringConverter.toString(sysConfKey);
    }
    
    static {
        DISPATCH_SERIALIZATION = EnumSet.of(SystemConfigurationType.SERVER_ID, SystemConfigurationType.COMMUNITY_CHECK_ENABLE, SystemConfigurationType.COMMUNITY_REQUIRED, SystemConfigurationType.COMMUNITY_FORBIDDEN, SystemConfigurationType.AUTHORIZED_PARTNERS);
        INSTANCE = new SystemConfiguration();
        m_logger = Logger.getLogger(SystemConfiguration.class);
        SPLIT_PATTERN = Pattern.compile(";");
    }
}
