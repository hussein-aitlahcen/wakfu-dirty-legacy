package org.apache.tools.zip;

import java.util.*;
import java.util.zip.CRC32;
import java.io.*;

public abstract class ZipUtil
{
    private static final byte[] DOS_TIME_MIN;
    
    public static ZipLong toDosTime(final Date time) {
        return new ZipLong(toDosTime(time.getTime()));
    }
    
    public static byte[] toDosTime(final long t) {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(t);
        final int year = c.get(1);
        if (year < 1980) {
            return copy(ZipUtil.DOS_TIME_MIN);
        }
        final int month = c.get(2) + 1;
        final long value = year - 1980 << 25 | month << 21 | c.get(5) << 16 | c.get(11) << 11 | c.get(12) << 5 | c.get(13) >> 1;
        return ZipLong.getBytes(value);
    }
    
    public static long adjustToLong(final int i) {
        if (i < 0) {
            return 4294967296L + i;
        }
        return i;
    }
    
    public static Date fromDosTime(final ZipLong zipDosTime) {
        final long dosTime = zipDosTime.getValue();
        return new Date(dosToJavaTime(dosTime));
    }
    
    public static long dosToJavaTime(final long dosTime) {
        final Calendar cal = Calendar.getInstance();
        cal.set(1, (int)(dosTime >> 25 & 0x7FL) + 1980);
        cal.set(2, (int)(dosTime >> 21 & 0xFL) - 1);
        cal.set(5, (int)(dosTime >> 16) & 0x1F);
        cal.set(11, (int)(dosTime >> 11) & 0x1F);
        cal.set(12, (int)(dosTime >> 5) & 0x3F);
        cal.set(13, (int)(dosTime << 1) & 0x3E);
        return cal.getTime().getTime();
    }
    
    static void setNameAndCommentFromExtraFields(final ZipEntry ze, final byte[] originalNameBytes, final byte[] commentBytes) {
        final UnicodePathExtraField name = (UnicodePathExtraField)ze.getExtraField(UnicodePathExtraField.UPATH_ID);
        final String originalName = ze.getName();
        final String newName = getUnicodeStringIfOriginalMatches(name, originalNameBytes);
        if (newName != null && !originalName.equals(newName)) {
            ze.setName(newName);
        }
        if (commentBytes != null && commentBytes.length > 0) {
            final UnicodeCommentExtraField cmt = (UnicodeCommentExtraField)ze.getExtraField(UnicodeCommentExtraField.UCOM_ID);
            final String newComment = getUnicodeStringIfOriginalMatches(cmt, commentBytes);
            if (newComment != null) {
                ze.setComment(newComment);
            }
        }
    }
    
    private static String getUnicodeStringIfOriginalMatches(final AbstractUnicodeExtraField f, final byte[] orig) {
        if (f != null) {
            final CRC32 crc32 = new CRC32();
            crc32.update(orig);
            final long origCRC32 = crc32.getValue();
            if (origCRC32 == f.getNameCRC32()) {
                try {
                    return ZipEncodingHelper.UTF8_ZIP_ENCODING.decode(f.getUnicodeName());
                }
                catch (IOException ex) {
                    return null;
                }
            }
        }
        return null;
    }
    
    static byte[] copy(final byte[] from) {
        if (from != null) {
            final byte[] to = new byte[from.length];
            System.arraycopy(from, 0, to, 0, to.length);
            return to;
        }
        return null;
    }
    
    static boolean canHandleEntryData(final ZipEntry entry) {
        return supportsEncryptionOf(entry) && supportsMethodOf(entry);
    }
    
    private static boolean supportsEncryptionOf(final ZipEntry entry) {
        return !entry.getGeneralPurposeBit().usesEncryption();
    }
    
    private static boolean supportsMethodOf(final ZipEntry entry) {
        return entry.getMethod() == 0 || entry.getMethod() == 8;
    }
    
    static void checkRequestedFeatures(final ZipEntry ze) throws UnsupportedZipFeatureException {
        if (!supportsEncryptionOf(ze)) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.ENCRYPTION, ze);
        }
        if (!supportsMethodOf(ze)) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.METHOD, ze);
        }
    }
    
    static {
        DOS_TIME_MIN = ZipLong.getBytes(8448L);
    }
}
