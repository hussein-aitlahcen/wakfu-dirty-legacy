package gnu.trove;

import java.util.Iterator;

public class TLongIterator extends TPrimitiveIterator implements Iterator<Long>
{
    private final TLongHash _hash;
    
    public TLongIterator(final TLongHash hash) {
        super(hash);
        this._hash = hash;
    }
    
    public Long next() {
        this.moveToNextIndex();
        return this._hash._set[this._index];
    }
}
