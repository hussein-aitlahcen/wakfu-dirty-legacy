package org.apache.tools.zip;

import java.util.zip.*;
import java.io.*;
import java.util.*;

public class ZipFile
{
    private static final int HASH_SIZE = 509;
    static final int NIBLET_MASK = 15;
    static final int BYTE_SHIFT = 8;
    private static final int POS_0 = 0;
    private static final int POS_1 = 1;
    private static final int POS_2 = 2;
    private static final int POS_3 = 3;
    private final List<ZipEntry> entries;
    private final Map<String, LinkedList<ZipEntry>> nameMap;
    private final String encoding;
    private final ZipEncoding zipEncoding;
    private final String archiveName;
    private final RandomAccessFile archive;
    private final boolean useUnicodeExtraFields;
    private boolean closed;
    private final byte[] DWORD_BUF;
    private final byte[] WORD_BUF;
    private final byte[] CFH_BUF;
    private final byte[] SHORT_BUF;
    private static final int CFH_LEN = 42;
    private static final long CFH_SIG;
    private static final int MIN_EOCD_SIZE = 22;
    private static final int MAX_EOCD_SIZE = 65557;
    private static final int CFD_LOCATOR_OFFSET = 16;
    private static final int ZIP64_EOCDL_LENGTH = 20;
    private static final int ZIP64_EOCDL_LOCATOR_OFFSET = 8;
    private static final int ZIP64_EOCD_CFD_LOCATOR_OFFSET = 48;
    private static final long LFH_OFFSET_FOR_FILENAME_LENGTH = 26L;
    private final Comparator<ZipEntry> OFFSET_COMPARATOR;
    
    public ZipFile(final File f) throws IOException {
        this(f, null);
    }
    
    public ZipFile(final String name) throws IOException {
        this(new File(name), null);
    }
    
    public ZipFile(final String name, final String encoding) throws IOException {
        this(new File(name), encoding, true);
    }
    
    public ZipFile(final File f, final String encoding) throws IOException {
        this(f, encoding, true);
    }
    
    public ZipFile(final File f, final String encoding, final boolean useUnicodeExtraFields) throws IOException {
        super();
        this.entries = new LinkedList<ZipEntry>();
        this.nameMap = new HashMap<String, LinkedList<ZipEntry>>(509);
        this.DWORD_BUF = new byte[8];
        this.WORD_BUF = new byte[4];
        this.CFH_BUF = new byte[42];
        this.SHORT_BUF = new byte[2];
        this.OFFSET_COMPARATOR = new Comparator<ZipEntry>() {
            public int compare(final ZipEntry e1, final ZipEntry e2) {
                if (e1 == e2) {
                    return 0;
                }
                final Entry ent1 = (e1 instanceof Entry) ? ((Entry)e1) : null;
                final Entry ent2 = (e2 instanceof Entry) ? ((Entry)e2) : null;
                if (ent1 == null) {
                    return 1;
                }
                if (ent2 == null) {
                    return -1;
                }
                final long val = ent1.getOffsetEntry().headerOffset - ent2.getOffsetEntry().headerOffset;
                return (val == 0L) ? 0 : ((val < 0L) ? -1 : 1);
            }
        };
        this.archiveName = f.getAbsolutePath();
        this.encoding = encoding;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
        this.useUnicodeExtraFields = useUnicodeExtraFields;
        this.archive = new RandomAccessFile(f, "r");
        boolean success = false;
        try {
            final Map<ZipEntry, NameAndComment> entriesWithoutUTF8Flag = this.populateFromCentralDirectory();
            this.resolveLocalFileHeaderData(entriesWithoutUTF8Flag);
            success = true;
        }
        finally {
            if (!success) {
                try {
                    this.closed = true;
                    this.archive.close();
                }
                catch (IOException ex) {}
            }
        }
    }
    
    public String getEncoding() {
        return this.encoding;
    }
    
    public void close() throws IOException {
        this.closed = true;
        this.archive.close();
    }
    
    public static void closeQuietly(final ZipFile zipfile) {
        if (zipfile != null) {
            try {
                zipfile.close();
            }
            catch (IOException ex) {}
        }
    }
    
    public Enumeration<ZipEntry> getEntries() {
        return Collections.enumeration(this.entries);
    }
    
    public Enumeration<ZipEntry> getEntriesInPhysicalOrder() {
        final ZipEntry[] allEntries = this.entries.toArray(new ZipEntry[0]);
        Arrays.sort(allEntries, this.OFFSET_COMPARATOR);
        return Collections.enumeration(Arrays.asList(allEntries));
    }
    
    public ZipEntry getEntry(final String name) {
        final LinkedList<ZipEntry> entriesOfThatName = this.nameMap.get(name);
        return (entriesOfThatName != null) ? entriesOfThatName.getFirst() : null;
    }
    
    public Iterable<ZipEntry> getEntries(final String name) {
        final List<ZipEntry> entriesOfThatName = this.nameMap.get(name);
        return (entriesOfThatName != null) ? entriesOfThatName : Collections.emptyList();
    }
    
    public Iterable<ZipEntry> getEntriesInPhysicalOrder(final String name) {
        ZipEntry[] entriesOfThatName = new ZipEntry[0];
        if (this.nameMap.containsKey(name)) {
            entriesOfThatName = this.nameMap.get(name).toArray(entriesOfThatName);
            Arrays.sort(entriesOfThatName, this.OFFSET_COMPARATOR);
        }
        return Arrays.asList(entriesOfThatName);
    }
    
    public boolean canReadEntryData(final ZipEntry ze) {
        return ZipUtil.canHandleEntryData(ze);
    }
    
    public InputStream getInputStream(final ZipEntry ze) throws IOException, ZipException {
        if (!(ze instanceof Entry)) {
            return null;
        }
        final OffsetEntry offsetEntry = ((Entry)ze).getOffsetEntry();
        ZipUtil.checkRequestedFeatures(ze);
        final long start = offsetEntry.dataOffset;
        final BoundedInputStream bis = new BoundedInputStream(start, ze.getCompressedSize());
        switch (ze.getMethod()) {
            case 0: {
                return bis;
            }
            case 8: {
                bis.addDummy();
                final Inflater inflater = new Inflater(true);
                return new InflaterInputStream(bis, inflater) {
                    public void close() throws IOException {
                        super.close();
                        inflater.end();
                    }
                };
            }
            default: {
                throw new ZipException("Found unsupported compression method " + ze.getMethod());
            }
        }
    }
    
    protected void finalize() throws Throwable {
        try {
            if (!this.closed) {
                System.err.println("Cleaning up unclosed ZipFile for archive " + this.archiveName);
                this.close();
            }
        }
        finally {
            super.finalize();
        }
    }
    
    private Map<ZipEntry, NameAndComment> populateFromCentralDirectory() throws IOException {
        final HashMap<ZipEntry, NameAndComment> noUTF8Flag = new HashMap<ZipEntry, NameAndComment>();
        this.positionAtCentralDirectory();
        this.archive.readFully(this.WORD_BUF);
        long sig = ZipLong.getValue(this.WORD_BUF);
        if (sig != ZipFile.CFH_SIG && this.startsWithLocalFileHeader()) {
            throw new IOException("central directory is empty, can't expand corrupt archive.");
        }
        while (sig == ZipFile.CFH_SIG) {
            this.readCentralDirectoryEntry(noUTF8Flag);
            this.archive.readFully(this.WORD_BUF);
            sig = ZipLong.getValue(this.WORD_BUF);
        }
        return noUTF8Flag;
    }
    
    private void readCentralDirectoryEntry(final Map<ZipEntry, NameAndComment> noUTF8Flag) throws IOException {
        this.archive.readFully(this.CFH_BUF);
        int off = 0;
        final OffsetEntry offset = new OffsetEntry();
        final Entry ze = new Entry(offset);
        final int versionMadeBy = ZipShort.getValue(this.CFH_BUF, off);
        off += 2;
        ze.setPlatform(versionMadeBy >> 8 & 0xF);
        off += 2;
        final GeneralPurposeBit gpFlag = GeneralPurposeBit.parse(this.CFH_BUF, off);
        final boolean hasUTF8Flag = gpFlag.usesUTF8ForNames();
        final ZipEncoding entryEncoding = hasUTF8Flag ? ZipEncodingHelper.UTF8_ZIP_ENCODING : this.zipEncoding;
        ze.setGeneralPurposeBit(gpFlag);
        off += 2;
        ze.setMethod(ZipShort.getValue(this.CFH_BUF, off));
        off += 2;
        final long time = ZipUtil.dosToJavaTime(ZipLong.getValue(this.CFH_BUF, off));
        ze.setTime(time);
        off += 4;
        ze.setCrc(ZipLong.getValue(this.CFH_BUF, off));
        off += 4;
        ze.setCompressedSize(ZipLong.getValue(this.CFH_BUF, off));
        off += 4;
        ze.setSize(ZipLong.getValue(this.CFH_BUF, off));
        off += 4;
        final int fileNameLen = ZipShort.getValue(this.CFH_BUF, off);
        off += 2;
        final int extraLen = ZipShort.getValue(this.CFH_BUF, off);
        off += 2;
        final int commentLen = ZipShort.getValue(this.CFH_BUF, off);
        off += 2;
        final int diskStart = ZipShort.getValue(this.CFH_BUF, off);
        off += 2;
        ze.setInternalAttributes(ZipShort.getValue(this.CFH_BUF, off));
        off += 2;
        ze.setExternalAttributes(ZipLong.getValue(this.CFH_BUF, off));
        off += 4;
        final byte[] fileName = new byte[fileNameLen];
        this.archive.readFully(fileName);
        ze.setName(entryEncoding.decode(fileName), fileName);
        offset.headerOffset = ZipLong.getValue(this.CFH_BUF, off);
        this.entries.add(ze);
        final byte[] cdExtraData = new byte[extraLen];
        this.archive.readFully(cdExtraData);
        ze.setCentralDirectoryExtra(cdExtraData);
        this.setSizesAndOffsetFromZip64Extra(ze, offset, diskStart);
        final byte[] comment = new byte[commentLen];
        this.archive.readFully(comment);
        ze.setComment(entryEncoding.decode(comment));
        if (!hasUTF8Flag && this.useUnicodeExtraFields) {
            noUTF8Flag.put(ze, new NameAndComment(fileName, comment));
        }
    }
    
    private void setSizesAndOffsetFromZip64Extra(final ZipEntry ze, final OffsetEntry offset, final int diskStart) throws IOException {
        final Zip64ExtendedInformationExtraField z64 = (Zip64ExtendedInformationExtraField)ze.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        if (z64 != null) {
            final boolean hasUncompressedSize = ze.getSize() == 4294967295L;
            final boolean hasCompressedSize = ze.getCompressedSize() == 4294967295L;
            final boolean hasRelativeHeaderOffset = offset.headerOffset == 4294967295L;
            z64.reparseCentralDirectoryData(hasUncompressedSize, hasCompressedSize, hasRelativeHeaderOffset, diskStart == 65535);
            if (hasUncompressedSize) {
                ze.setSize(z64.getSize().getLongValue());
            }
            else if (hasCompressedSize) {
                z64.setSize(new ZipEightByteInteger(ze.getSize()));
            }
            if (hasCompressedSize) {
                ze.setCompressedSize(z64.getCompressedSize().getLongValue());
            }
            else if (hasUncompressedSize) {
                z64.setCompressedSize(new ZipEightByteInteger(ze.getCompressedSize()));
            }
            if (hasRelativeHeaderOffset) {
                offset.headerOffset = z64.getRelativeHeaderOffset().getLongValue();
            }
        }
    }
    
    private void positionAtCentralDirectory() throws IOException {
        this.positionAtEndOfCentralDirectoryRecord();
        boolean found = false;
        final boolean searchedForZip64EOCD = this.archive.getFilePointer() > 20L;
        if (searchedForZip64EOCD) {
            this.archive.seek(this.archive.getFilePointer() - 20L);
            this.archive.readFully(this.WORD_BUF);
            found = Arrays.equals(ZipOutputStream.ZIP64_EOCD_LOC_SIG, this.WORD_BUF);
        }
        if (!found) {
            if (searchedForZip64EOCD) {
                this.skipBytes(16);
            }
            this.positionAtCentralDirectory32();
        }
        else {
            this.positionAtCentralDirectory64();
        }
    }
    
    private void positionAtCentralDirectory64() throws IOException {
        this.skipBytes(4);
        this.archive.readFully(this.DWORD_BUF);
        this.archive.seek(ZipEightByteInteger.getLongValue(this.DWORD_BUF));
        this.archive.readFully(this.WORD_BUF);
        if (!Arrays.equals(this.WORD_BUF, ZipOutputStream.ZIP64_EOCD_SIG)) {
            throw new ZipException("archive's ZIP64 end of central directory locator is corrupt.");
        }
        this.skipBytes(44);
        this.archive.readFully(this.DWORD_BUF);
        this.archive.seek(ZipEightByteInteger.getLongValue(this.DWORD_BUF));
    }
    
    private void positionAtCentralDirectory32() throws IOException {
        this.skipBytes(16);
        this.archive.readFully(this.WORD_BUF);
        this.archive.seek(ZipLong.getValue(this.WORD_BUF));
    }
    
    private void positionAtEndOfCentralDirectoryRecord() throws IOException {
        final boolean found = this.tryToLocateSignature(22L, 65557L, ZipOutputStream.EOCD_SIG);
        if (!found) {
            throw new ZipException("archive is not a ZIP archive");
        }
    }
    
    private boolean tryToLocateSignature(final long minDistanceFromEnd, final long maxDistanceFromEnd, final byte[] sig) throws IOException {
        boolean found = false;
        long off = this.archive.length() - minDistanceFromEnd;
        final long stopSearching = Math.max(0L, this.archive.length() - maxDistanceFromEnd);
        if (off >= 0L) {
            while (off >= stopSearching) {
                this.archive.seek(off);
                int curr = this.archive.read();
                if (curr == -1) {
                    break;
                }
                if (curr == sig[0]) {
                    curr = this.archive.read();
                    if (curr == sig[1]) {
                        curr = this.archive.read();
                        if (curr == sig[2]) {
                            curr = this.archive.read();
                            if (curr == sig[3]) {
                                found = true;
                                break;
                            }
                        }
                    }
                }
                --off;
            }
        }
        if (found) {
            this.archive.seek(off);
        }
        return found;
    }
    
    private void skipBytes(final int count) throws IOException {
        int skippedNow;
        for (int totalSkipped = 0; totalSkipped < count; totalSkipped += skippedNow) {
            skippedNow = this.archive.skipBytes(count - totalSkipped);
            if (skippedNow <= 0) {
                throw new EOFException();
            }
        }
    }
    
    private void resolveLocalFileHeaderData(final Map<ZipEntry, NameAndComment> entriesWithoutUTF8Flag) throws IOException {
        for (final ZipEntry ze : this.entries) {
            final OffsetEntry offsetEntry = ((Entry) ze).getOffsetEntry();
            final long offset = offsetEntry.headerOffset;
            this.archive.seek(offset + 26L);
            this.archive.readFully(this.SHORT_BUF);
            final int fileNameLen = ZipShort.getValue(this.SHORT_BUF);
            this.archive.readFully(this.SHORT_BUF);
            final int extraFieldLen = ZipShort.getValue(this.SHORT_BUF);
            int skipped;
            for (int lenToSkip = fileNameLen; lenToSkip > 0; lenToSkip -= skipped) {
                skipped = this.archive.skipBytes(lenToSkip);
                if (skipped <= 0) {
                    throw new IOException("failed to skip file name in local file header");
                }
            }
            final byte[] localExtraData = new byte[extraFieldLen];
            this.archive.readFully(localExtraData);
            ze.setExtra(localExtraData);
            offsetEntry.dataOffset = offset + 26L + 2L + 2L + fileNameLen + extraFieldLen;
            if (entriesWithoutUTF8Flag.containsKey(ze)) {
                final NameAndComment nc = entriesWithoutUTF8Flag.get(ze);
                ZipUtil.setNameAndCommentFromExtraFields(ze, nc.name, nc.comment);
            }
            final String name = ze.getName();
            LinkedList<ZipEntry> entriesOfThatName = this.nameMap.get(name);
            if (entriesOfThatName == null) {
                entriesOfThatName = new LinkedList<ZipEntry>();
                this.nameMap.put(name, entriesOfThatName);
            }
            entriesOfThatName.addLast(ze);
        }
    }
    
    private boolean startsWithLocalFileHeader() throws IOException {
        this.archive.seek(0L);
        this.archive.readFully(this.WORD_BUF);
        return Arrays.equals(this.WORD_BUF, ZipOutputStream.LFH_SIG);
    }
    
    static {
        CFH_SIG = ZipLong.getValue(ZipOutputStream.CFH_SIG);
    }
    
    private static final class OffsetEntry
    {
        private long headerOffset;
        private long dataOffset;
        
        private OffsetEntry() {
            super();
            this.headerOffset = -1L;
            this.dataOffset = -1L;
        }
    }
    
    private class BoundedInputStream extends InputStream
    {
        private long remaining;
        private long loc;
        private boolean addDummyByte;
        
        BoundedInputStream(final long start, final long remaining) {
            super();
            this.addDummyByte = false;
            this.remaining = remaining;
            this.loc = start;
        }
        
        public int read() throws IOException {
            if (this.remaining-- <= 0L) {
                if (this.addDummyByte) {
                    this.addDummyByte = false;
                    return 0;
                }
                return -1;
            }
            else {
                synchronized (ZipFile.this.archive) {
                    ZipFile.this.archive.seek(this.loc++);
                    return ZipFile.this.archive.read();
                }
            }
        }
        
        public int read(final byte[] b, final int off, int len) throws IOException {
            if (this.remaining <= 0L) {
                if (this.addDummyByte) {
                    this.addDummyByte = false;
                    b[off] = 0;
                    return 1;
                }
                return -1;
            }
            else {
                if (len <= 0) {
                    return 0;
                }
                if (len > this.remaining) {
                    len = (int)this.remaining;
                }
                int ret = -1;
                synchronized (ZipFile.this.archive) {
                    ZipFile.this.archive.seek(this.loc);
                    ret = ZipFile.this.archive.read(b, off, len);
                }
                if (ret > 0) {
                    this.loc += ret;
                    this.remaining -= ret;
                }
                return ret;
            }
        }
        
        void addDummy() {
            this.addDummyByte = true;
        }
    }
    
    private static final class NameAndComment
    {
        private final byte[] name;
        private final byte[] comment;
        
        private NameAndComment(final byte[] name, final byte[] comment) {
            super();
            this.name = name;
            this.comment = comment;
        }
    }
    
    private static class Entry extends ZipEntry
    {
        private final OffsetEntry offsetEntry;
        
        Entry(final OffsetEntry offset) {
            super();
            this.offsetEntry = offset;
        }
        
        OffsetEntry getOffsetEntry() {
            return this.offsetEntry;
        }
        
        public int hashCode() {
            return 3 * super.hashCode() + (int)(this.offsetEntry.headerOffset % 2147483647L);
        }
        
        public boolean equals(final Object other) {
            if (super.equals(other)) {
                final Entry otherEntry = (Entry)other;
                return this.offsetEntry.headerOffset == otherEntry.offsetEntry.headerOffset && this.offsetEntry.dataOffset == otherEntry.offsetEntry.dataOffset;
            }
            return false;
        }
    }
}
